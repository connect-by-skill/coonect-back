package com.example.connectback.domain.resume.exception;


import com.example.connectback.global.error.exception.BusinessException;
import com.example.connectback.global.error.exception.ErrorCode;

public class NotExistResumeException extends BusinessException {
    public NotExistResumeException(ErrorCode errorCode) { super(errorCode);}
}
