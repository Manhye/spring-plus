package org.example.expert.domain.log.service

import org.example.expert.domain.log.entity.Log
import org.example.expert.domain.log.entity.LogResult
import org.example.expert.domain.log.repository.LogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class LogService(
    private val logRepository: LogRepository
){

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun saveLog(userId: Long?, details: String?, result: LogResult){
        val log = Log(userId, details, result)
        logRepository.save(log)
    }
}