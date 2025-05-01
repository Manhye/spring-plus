package org.example.expert.domain.log.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "logs")
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;

	private String details;

	@Enumerated(EnumType.STRING)
	private LogResult result;

	@CreatedDate
	private LocalDateTime createdAt;

	public Log(){}

	public Log(Long userId, String details, LogResult result) {
		this.userId = userId;
		this.details = details;
		this.result = result;
	}
}
