package com.cat.texttranslator.exception;

import lombok.Getter;

@Getter
public class LabelNotFoundException extends Exception {

    private int errorCode;
    private String message;

    public LabelNotFoundException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
