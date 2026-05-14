package com.bizteam3.server.translation.service;

import com.bizteam3.server.global.exception.ErrorCode;
import com.bizteam3.server.global.exception.common.BadRequestException;
import com.bizteam3.server.global.exception.common.NotFoundException;
import com.bizteam3.server.post.dao.PostDao;
import com.bizteam3.server.translation.dto.TranslationRequest;
import com.bizteam3.server.translation.dto.TranslationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TranslationService {
	private static final String POST_TARGET_TYPE = "POST";
	private static final String DEFAULT_TARGET_LANG = "EN";
	private static final String ENGLISH_LANG = "EN";
	private static final URI DEEPL_API_URI = URI.create("https://api-free.deepl.com/v2/translate");
	private static final Pattern DETECTED_SOURCE_LANG_PATTERN = Pattern.compile("\"detected_source_language\"\\s*:\\s*\"([^\"]+)\"");
	private static final Pattern TRANSLATED_TEXT_PATTERN = Pattern.compile("\"text\"\\s*:\\s*\"((?:\\\\.|[^\"])*)\"");

	private final PostDao postDao;
	private final HttpClient httpClient = HttpClient.newHttpClient();

	@Value("${deepl.api-key:}")
	private String deeplApiKey;

	@Transactional
	public TranslationResponse translatePostCaption(TranslationRequest request) {
		if (!POST_TARGET_TYPE.equalsIgnoreCase(request.getTargetType())) {
			throw new BadRequestException(ErrorCode.BAD_REQUEST, "게시물 캡션 번역만 지원합니다.");
		}

		String cachedCaption = postDao.selectTranslatedCaptionByPostId(request.getTargetId());
		if (hasText(cachedCaption)) {
			return new TranslationResponse(cachedCaption, null, normalizeTargetLang(request.getTargetLang()), true);
		}

		String caption = postDao.selectCaptionByPostId(request.getTargetId());
		if (!hasText(caption)) {
			throw new NotFoundException(ErrorCode.NOT_FOUND, "번역할 게시물 캡션을 찾을 수 없습니다.");
		}

		if (!hasText(deeplApiKey)) {
			throw new BadRequestException(ErrorCode.BAD_REQUEST, "DeepL API 키가 설정되지 않았습니다.");
		}

		DeepLResult result = translateWithAutoDirection(caption, request.getTargetLang());
		postDao.updateTranslatedCaption(request.getTargetId(), result.text());

		return new TranslationResponse(
				result.text(),
				result.sourceLang(),
				resolveResponseTargetLang(result.sourceLang(), request.getTargetLang()),
				false
		);
	}

	private DeepLResult translateWithAutoDirection(String text, String targetLang) {
		String normalizedTargetLang = normalizeTargetLang(targetLang);
		DeepLResult firstResult = callDeepL(text, normalizedTargetLang);

		if (!hasText(targetLang) && ENGLISH_LANG.equalsIgnoreCase(firstResult.sourceLang())) {
			return callDeepL(text, "KO");
		}

		return firstResult;
	}

	private DeepLResult callDeepL(String text, String targetLang) {
		try {
			String requestBody = "text=" + encode(text) + "&target_lang=" + encode(targetLang);

			HttpRequest request = HttpRequest.newBuilder(DEEPL_API_URI)
					.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
					.header(HttpHeaders.AUTHORIZATION, "DeepL-Auth-Key " + deeplApiKey)
					.POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() < 200 || response.statusCode() >= 300) {
				throw new BadRequestException(ErrorCode.BAD_REQUEST, "DeepL 번역 요청에 실패했습니다.");
			}

			DeepLResult result = parseDeepLResult(response.body());
			if (!hasText(result.text())) {
				throw new BadRequestException(ErrorCode.BAD_REQUEST, "DeepL 번역 결과가 비어 있습니다.");
			}

			return result;
		} catch (IOException e) {
			throw new BadRequestException(ErrorCode.BAD_REQUEST, "DeepL 서버와 통신할 수 없습니다.");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new BadRequestException(ErrorCode.BAD_REQUEST, "DeepL 번역 요청이 중단되었습니다.");
		}
	}

	private DeepLResult parseDeepLResult(String responseBody) {
		Matcher sourceMatcher = DETECTED_SOURCE_LANG_PATTERN.matcher(responseBody);
		Matcher textMatcher = TRANSLATED_TEXT_PATTERN.matcher(responseBody);

		String sourceLang = sourceMatcher.find() ? sourceMatcher.group(1) : null;
		String translatedText = textMatcher.find() ? unescapeJsonString(textMatcher.group(1)) : null;

		return new DeepLResult(sourceLang, translatedText);
	}

	private String unescapeJsonString(String value) {
		return value
				.replace("\\n", "\n")
				.replace("\\\"", "\"")
				.replace("\\\\", "\\");
	}

	private String encode(String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}

	private String normalizeTargetLang(String targetLang) {
		if (!hasText(targetLang)) {
			return DEFAULT_TARGET_LANG;
		}

		return targetLang.trim().toUpperCase();
	}

	private String resolveResponseTargetLang(String sourceLang, String requestedTargetLang) {
		if (hasText(requestedTargetLang)) {
			return normalizeTargetLang(requestedTargetLang);
		}

		return ENGLISH_LANG.equalsIgnoreCase(sourceLang) ? "KO" : DEFAULT_TARGET_LANG;
	}

	private boolean hasText(String value) {
		return value != null && !value.isBlank();
	}

	private record DeepLResult(String sourceLang, String text) {
	}
}
