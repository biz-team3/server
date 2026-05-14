package com.bizteam3.server.translation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslationRequest {
	private String targetType = "POST";

	@NotNull
	private Integer targetId;

	private String targetLang;
}
