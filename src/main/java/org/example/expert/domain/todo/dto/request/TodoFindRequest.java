package org.example.expert.domain.todo.dto.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoFindRequest {
	private String weather;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
