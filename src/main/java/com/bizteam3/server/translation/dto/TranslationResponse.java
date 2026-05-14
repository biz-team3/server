package com.bizteam3.server.translation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TranslationResponse {
	private String translatedContent;
	private String sourceLang;
	private String targetLang;
	private boolean cached;
}
