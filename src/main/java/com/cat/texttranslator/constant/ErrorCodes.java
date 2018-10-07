package com.cat.texttranslator.constant;

import lombok.Getter;

@Getter
public enum  ErrorCodes {

    INVALID_EXCEL_FILE(400, "Invalid Excel File uploaded."),
    INVALID_JSON_PAYLOAD(400, "Invalid Request body passed."),
    LABEL_NOT_FOUND(404, "Requested Label not found."),
    INVALID_LANGUAGE(400, "Language not supported."),
    INVALID_REQUEST(400, "Malformed Request.");

    private int code;
    private String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
