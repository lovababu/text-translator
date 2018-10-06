package com.cat.texttranslator.api.rest;

import com.cat.texttranslator.api.model.ApiResponse;
import com.cat.texttranslator.api.model.Translation;
import com.cat.texttranslator.constant.AppConstants;
import com.cat.texttranslator.entity.LabelDocument;
import com.cat.texttranslator.service.LabelSearchService;
import com.cat.texttranslator.service.LabelsLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/translate")
public class TranslatorController {

    @Autowired
    private LabelsLoaderService labelsLoaderService;

    @Autowired
    private LabelSearchService labelSearchService;

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> search(@RequestParam(name = "label") String label) {
        LabelDocument labelDocument = labelSearchService.search(label);
        return new ResponseEntity<>(ApiResponse.builder()
                .labelDocument(labelDocument)
                .status("Success")
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> find(@RequestParam(name = "label") String label,
                                            @RequestParam(name = "language") String language) {
        LabelDocument labelDocument = labelSearchService.find(label, language);
        ApiResponse apiResponse = ApiResponse.builder()
                .labelDocument(labelDocument)
                .status(AppConstants.SUCCESS)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/load", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> load(@RequestParam(name = "file") MultipartFile file) throws Exception {
        int rowsProcessed = labelsLoaderService.load(file.getInputStream());
        log.info("{} Records processed successfully.", rowsProcessed);
        ApiResponse apiResponse = ApiResponse.builder()
                .recordsProcessed(rowsProcessed)
                .status("Success")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> post(@RequestParam(name = "label") String label, @RequestBody Translation translation) {
        int rowsProcessed = labelsLoaderService.post(label, translation);
        ApiResponse apiResponse = ApiResponse.builder()
                .recordsProcessed(rowsProcessed)
                .status(AppConstants.SUCCESS)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
