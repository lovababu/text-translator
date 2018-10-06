package com.cat.texttranslator.exception;

import lombok.Getter;

@Getter
public class LanguageNotSupportedException extends Exception{

    private int errorCode;
    private String message;

    public LanguageNotSupportedException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
