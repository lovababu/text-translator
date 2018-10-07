package com.cat.texttranslator.service;

import com.cat.texttranslator.api.model.Translation;
import com.cat.texttranslator.exception.MalformedJsonPayloadException;

import java.io.InputStream;
import java.util.List;

public interface LabelsLoaderService {

    int load(InputStream data) throws Exception;

    int post(String label, List<Translation> translation) throws MalformedJsonPayloadException;
}
