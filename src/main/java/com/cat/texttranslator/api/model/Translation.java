package com.cat.texttranslator.api.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Translation {

    private String code;
    private String language;
    private String value;
    private TimeStamp timeStamp;
}
