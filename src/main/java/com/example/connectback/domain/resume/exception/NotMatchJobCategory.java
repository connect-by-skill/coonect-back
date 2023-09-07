package com.example.connectback.domain.resume.exception;


import com.example.connectback.global.error.exception.BusinessException;
import com.example.connectback.global.error.exception.ErrorCode;

public class NotMatchJobCategory extends BusinessException {

    public NotMatchJobCategory(ErrorCode errorCode) {
        super(errorCode);
    }
}
