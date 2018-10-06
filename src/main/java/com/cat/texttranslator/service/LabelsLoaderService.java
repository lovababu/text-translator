package com.cat.texttranslator.service;

import com.cat.texttranslator.api.model.Translation;

import java.io.IOException;
import java.io.InputStream;

public interface LabelsLoaderService {

    int load(InputStream data) throws Exception;

    int post(String label, Translation translation);
}
