package com.cat.texttranslator.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel(value = "Class represents Error model.")
public class Error {

    @ApiModelProperty(value = "Denotes error code.")
    private int errorCode;

    @ApiModelProperty(value = "Denotes error message.")
    private String message;

    @ApiModelProperty(value = "Denotes in detail error info.")
    private String info;
}
