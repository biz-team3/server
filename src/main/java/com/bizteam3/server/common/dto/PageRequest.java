package com.bizteam3.server.common.dto;

import com.bizteam3.server.global.exception.common.InvalidParameterException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 예외 처리를 할 때
 * ex) usernmae이 들어오는데 비면 안돼고, 3~10글자 사이여야 해
 * 처음 걸러내야할 것(비면 안돼)
 * 나중에 도메안적으로 예외처리를 해야할 것(3~10글자 도메인)
 * */
@Getter
@Setter
@NoArgsConstructor
public class PageRequest {
	@Min(value = 0)
	private int page;

	@Min(value = 1)
	@Max(value = 1000)
	private int size;

	public PageRequest(int page, int size) {
		if (page < 0) {
			throw new InvalidParameterException("페이지(page)는 0보다 커야합니다.");
		}

		if (size < 1) {
			throw new InvalidParameterException("페이지크기(size)는 1보다 커야 합니다.");
		}

		this.page = page;
		this.size = size;
	}

	public static PageRequest of(int page, int size) {
		return new PageRequest(page, size);
	}

	@JsonIgnore
	public int getOffset() {
		return  page * size;
	}


}
