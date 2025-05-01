package org.example.expert.domain.todo.dto.request;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class TodoSearchRequest {
	String titleKeyword;
	String managerNickname;
	LocalDate startDate;
	LocalDate endDate;
}
