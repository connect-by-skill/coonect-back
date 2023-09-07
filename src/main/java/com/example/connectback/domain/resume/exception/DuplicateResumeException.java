package com.example.connectback.domain.resume.exception;


import com.example.connectback.global.error.exception.BusinessException;
import com.example.connectback.global.error.exception.ErrorCode;

public class DuplicateResumeException extends BusinessException {
    public DuplicateResumeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
