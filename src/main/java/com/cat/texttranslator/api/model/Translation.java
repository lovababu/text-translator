package com.cat.texttranslator.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
@Getter
@ApiModel(value = "Class represents translation model for a label.")
public class Translation {

    @ApiModelProperty(value = "2 letters Language Code.")
    private String code;

    @NotNull
    @ApiModelProperty(value = "Language Name.")
    private String language;

    @NotNull
    @ApiModelProperty(value = "Label in specific language.")
    private String value;

    @ApiModelProperty(value = "Timestamp when the label posted.")
    private TimeStamp timeStamp;
}
