package org.example.expert.domain.log.service;

import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.entity.LogResult;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {

	private final LogRepository logRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveLog(Long userId, String details, LogResult result){
		Log log = new Log(userId, details, result);

		logRepository.save(log);
	}

}
