package com.cat.texttranslator.entity;

import com.cat.texttranslator.api.model.TimeStamp;
import com.cat.texttranslator.api.model.Translation;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class LabelDocument {

    private String label;
    private TimeStamp timeStamp;
    private Map<String, Translation> translations;
}
