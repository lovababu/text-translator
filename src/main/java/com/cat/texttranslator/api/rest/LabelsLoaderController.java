package com.cat.texttranslator.api.rest;

import com.cat.texttranslator.api.model.ApiResponse;
import com.cat.texttranslator.api.model.Translation;
import com.cat.texttranslator.constant.AppConstants;
import com.cat.texttranslator.exception.MalformedJsonPayloadException;
import com.cat.texttranslator.service.LabelsLoaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@CrossOrigin(methods = {RequestMethod.POST}, origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/load")
@Api(description = "APIs supports to load label translations in multiple languages. Payload can be either Excel file or Json.",
        basePath = "/load", produces = MediaType.APPLICATION_JSON_VALUE)
public class LabelsLoaderController {

    @Autowired
    private LabelsLoaderService labelsLoaderService;

    @ApiOperation(value = "Method to load label translations through excel file.",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, headers = "Content-Type=multipart/form-data")
    public ResponseEntity<ApiResponse> load(@ApiParam(value = "Excel file to be uploaded.", name = "file", type = MediaType.MULTIPART_FORM_DATA_VALUE)
                                                @RequestParam(name = "file") MultipartFile file)
            throws Exception {
        int rowsProcessed = labelsLoaderService.load(file.getInputStream());
        log.info("{} Records processed successfully.", rowsProcessed);
        ApiResponse apiResponse = ApiResponse.builder()
                .recordsProcessed(rowsProcessed)
                .status("Success")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Method to load label translations through JSON payload.",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Content-Type=application/json")
    public ResponseEntity<ApiResponse> post(@ApiParam(value = "Label to be post.", name = "label", required = true)
                                                @RequestParam(name = "label") String label,
                                            @ApiParam("List of translation objects.")
                                            @RequestBody List<Translation> translation)
            throws MalformedJsonPayloadException {
        int rowsProcessed = labelsLoaderService.post(label, translation);
        ApiResponse apiResponse = ApiResponse.builder()
                .recordsProcessed(rowsProcessed)
                .status(AppConstants.SUCCESS)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
