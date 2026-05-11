package com.bizteam3.server.global.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bizteam3.server.common.file.FileUploadProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final FileUploadProperties properties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String uploadPath = Paths.get(properties.getRootDir())
			.toAbsolutePath()
			.normalize()
			.toString();

		registry.addResourceHandler(properties.getUrlPrefix() + "/**")
			.addResourceLocations("file:" + uploadPath + "/");
	}
}
