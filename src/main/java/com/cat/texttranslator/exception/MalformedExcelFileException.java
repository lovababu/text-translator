package com.cat.texttranslator.exception;

import lombok.Getter;

@Getter
public class MalformedExcelFileException extends Exception {

    private int errorCode;
    private String message;

    public MalformedExcelFileException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
