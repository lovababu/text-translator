package com.cat.texttranslator.api.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Error {

    private int errorCode;
    private String message;
    private String info;
}
