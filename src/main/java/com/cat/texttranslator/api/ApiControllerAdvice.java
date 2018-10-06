package com.cat.texttranslator.api;

import com.cat.texttranslator.api.model.ApiResponse;
import com.cat.texttranslator.api.model.Error;
import com.cat.texttranslator.constant.AppConstants;
import com.cat.texttranslator.exception.LabelNotFoundException;
import com.cat.texttranslator.exception.MalformedExcelFileException;
import com.cat.texttranslator.exception.MalformedJsonPayloadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(value = MalformedExcelFileException.class)
    public ResponseEntity<ApiResponse> handleMalformedExcelFileException(MalformedExcelFileException ex) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(ApiResponse.builder()
                .status(AppConstants.FAILED)
                .error(
                        buildError(ex.getErrorCode(), ex.getMessage())
                )
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MalformedJsonPayloadException.class)
    public ResponseEntity<ApiResponse> handleMalformedJsonPayloadException(MalformedJsonPayloadException ex) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(ApiResponse.builder()
                .status(AppConstants.FAILED)
                .error(
                        buildError(ex.getErrorCode(), ex.getMessage())
                )
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = LabelNotFoundException.class)
    public ResponseEntity<ApiResponse> handleLabelNotFound(LabelNotFoundException ex) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(ApiResponse.builder()
                .status(AppConstants.SUCCESS)
                .error(
                        buildError(ex.getErrorCode(), ex.getMessage())
                )
                .build(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handleUnknownException(Exception ex) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(ApiResponse.builder()
                .status(AppConstants.FAILED)
                .error(
                        buildError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                ).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Error buildError(int errorCode, String message) {
        return Error.builder().errorCode(errorCode).message(message).build();
    }
}
