package com.cat.texttranslator.api.rest;

import com.cat.texttranslator.api.model.ApiResponse;
import com.cat.texttranslator.constant.AppConstants;
import com.cat.texttranslator.entity.LabelDocument;
import com.cat.texttranslator.service.LabelSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/search")
@Api(description = "APIs supports label search capability in system.",
        produces = MediaType.APPLICATION_JSON_VALUE, basePath = "/search")
public class LabelsSearchController {

    @Autowired
    private LabelSearchService labelSearchService;

    @ApiOperation(value = "Returns available translations for the requested label.",
            httpMethod = "GET", response = ApiResponse.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "label")
    public ResponseEntity<ApiResponse> search(@ApiParam(name = "label", required = true) @RequestParam(name = "label") String label) {
        LabelDocument labelDocument = labelSearchService.search(label);
        return new ResponseEntity<>(ApiResponse.builder()
                .labelDocument(labelDocument)
                .status("Success")
                .build(), HttpStatus.OK);
    }

    @ApiOperation(value = "Returns available translations for the requested label.",
            httpMethod = "GET", response = ApiResponse.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"label", "language"})
    public ResponseEntity<ApiResponse> find(@ApiParam(name = "label", required = true)
                                                  @RequestParam(name = "label") String label,
                                              @ApiParam(name = "language", required = true)
                                                @RequestParam(name = "language") String language) {
        LabelDocument labelDocument = labelSearchService.find(label, language);
        ApiResponse apiResponse = ApiResponse.builder()
                .labelDocument(labelDocument)
                .status(AppConstants.SUCCESS)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
