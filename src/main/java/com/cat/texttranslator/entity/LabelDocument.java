package com.cat.texttranslator.entity;

import com.cat.texttranslator.api.model.TimeStamp;
import com.cat.texttranslator.api.model.Translation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@ApiModel(value = "Class represents the Label document model.")
@JsonPropertyOrder({
        "label",
        "translations",
        "timeStamp"
})
public class LabelDocument {

    @ApiModelProperty(value = "Denotes Label in english language.")
    private String label;

    @ApiModelProperty(value = "Represents timestamp, when the label processed.")
    private TimeStamp timeStamp;

    @JsonIgnore
    private Map<String, Translation> translationMap;

    private List<Translation> translations;
}
