package com.im.branchlayout.dto;

// CustomException.java도 common 패키지로 이동
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
