package com.cat.texttranslator.exception;

import lombok.Getter;

@Getter
public class MalformedJsonPayloadException extends Exception {

    private int errorCode;
    private String message;

    public MalformedJsonPayloadException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
