package com.cat.texttranslator.service;

import com.cat.texttranslator.entity.LabelDocument;
import com.cat.texttranslator.exception.LabelNotFoundException;

public interface LabelSearchService {

    LabelDocument search(String label) throws LabelNotFoundException;

    LabelDocument find(String label, String langCode) throws LabelNotFoundException;

    String recognize(String text);
}
