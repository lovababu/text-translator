package com.cat.texttranslator.entity;

import com.cat.texttranslator.api.model.TimeStamp;
import com.cat.texttranslator.api.model.Translation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
@ApiModel(value = "Class represents the Label document model.")
public class LabelDocument {

    @ApiModelProperty(value = "Denotes Label in english language.")
    private String label;

    @ApiModelProperty(value = "Represents timestamp, when the label processed.")
    private TimeStamp timeStamp;

    @ApiModelProperty(value = "Represents translation model, key will be language and value will be translation object.")
    private Map<String, Translation> translations;
}
