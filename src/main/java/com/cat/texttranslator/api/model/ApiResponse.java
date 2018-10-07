package com.cat.texttranslator.api.model;

import com.cat.texttranslator.entity.LabelDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Class represents the Api Response model.")
public class ApiResponse {

    @ApiModelProperty(value = "Denotes Request status, either success/failed.")
    private String status;

    @ApiModelProperty(value = "Denotes number of translations loaded into the system.")
    private Integer recordsProcessed;

    @ApiModelProperty(value = "Denotes timestamp when the label processed.")
    private TimeStamp timeStamp;

    @ApiModelProperty(value = "Represents Label document model.")
    private LabelDocument labelDocument;

    @ApiModelProperty(value = "Represents Error model.")
    private Error error;
}
