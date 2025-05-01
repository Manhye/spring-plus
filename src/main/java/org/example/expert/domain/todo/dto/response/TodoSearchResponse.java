package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

@Getter
public class TodoSearchResponse {
	String title;
	long managerCount;
	long commentCount;
}
