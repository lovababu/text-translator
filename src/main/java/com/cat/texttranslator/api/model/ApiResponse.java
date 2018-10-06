package com.cat.texttranslator.api.model;

import com.cat.texttranslator.entity.LabelDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@JsonPropertyOrder({
        "status",
        "recordsProcessed",
        "label",
        "translations",
        "timeStamp"
})
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private String status;
    private Integer recordsProcessed;
    private TimeStamp timeStamp;
    private LabelDocument labelDocument;
    private Error error;
}
