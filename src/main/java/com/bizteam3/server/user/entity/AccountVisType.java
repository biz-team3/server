package com.bizteam3.server.user.entity;

public enum AccountVisType {
	PUBLIC("공개"),
	PRIVATE("비공개")

	;

	private final String name;

	AccountVisType(String name) {
		this.name = name;
	}
}
