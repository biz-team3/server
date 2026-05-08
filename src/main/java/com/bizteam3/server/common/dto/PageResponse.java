package com.bizteam3.server.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageResponse<T> {
	private List<T> content = new ArrayList<>();
	private PageRequest pageRequest;
	private long total;

	public PageResponse(List<T> content, PageRequest pageRequest, int total) {
		this.content.addAll(content);
		this.pageRequest = pageRequest;
		this.total = Optional.of(pageRequest)
			.filter(it -> !content.isEmpty())
			.filter(it -> pageRequest.getOffset() + it.getSize() > total)
			.map(it -> pageRequest.getOffset() + content.size())
			.orElse(total);
	}

	public int getTotalPages() {
		return this.pageRequest.getSize() == 0 ? 1 : (int) Math.ceil(getTotal() / (double) this.pageRequest.getSize());
	}

	@JsonProperty("hasNext")
	public boolean hasNext() {
		return this.pageRequest.getPage() + 1 < getTotalPages();
	}


}
