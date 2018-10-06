package com.cat.texttranslator.service;

import com.cat.texttranslator.entity.LabelDocument;

public interface LabelSearchService {

    LabelDocument search(String label);

    LabelDocument find(String label, String langCode);

    String recognize(String text);
}
