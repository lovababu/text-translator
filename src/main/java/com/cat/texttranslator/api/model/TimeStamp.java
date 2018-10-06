package com.cat.texttranslator.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonPropertyOrder({
        "created",
        "modified"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeStamp {

    private LocalDateTime created;

    private LocalDateTime modified;
}
