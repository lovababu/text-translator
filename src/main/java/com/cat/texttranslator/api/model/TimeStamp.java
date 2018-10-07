package com.cat.texttranslator.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Class represents the TimeStamp, when the label created/updated.")
public class TimeStamp {

    @ApiModelProperty(value = "Created timestamp.")
    private LocalDateTime created;

    @ApiModelProperty(value = "Last modified timestamp.")
    private LocalDateTime modified;
}
