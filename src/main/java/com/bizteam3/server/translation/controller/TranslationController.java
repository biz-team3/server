package com.bizteam3.server.translation.controller;

import com.bizteam3.server.translation.dto.TranslationRequest;
import com.bizteam3.server.translation.dto.TranslationResponse;
import com.bizteam3.server.translation.service.TranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/translations")
public class TranslationController {
	private final TranslationService translationService;

	@PostMapping
	public TranslationResponse translatePostCaption(@Valid @RequestBody TranslationRequest request) {
		return translationService.translatePostCaption(request);
	}
}
