package com.cat.texttranslator.service.impl;

import com.cat.texttranslator.connectors.ElasticSearchConnector;
import com.cat.texttranslator.constant.ErrorCodes;
import com.cat.texttranslator.entity.LabelDocument;
import com.cat.texttranslator.exception.LabelNotFoundException;
import com.cat.texttranslator.service.LabelSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelSearchServiceImpl implements LabelSearchService {

    @Autowired
    private ElasticSearchConnector esConnector;

    /**
     * Find requested label and returns in available languages.
     *
     * @param label
     *     should be passed in english.
     * @return available translations.
     */
    @Override
    public LabelDocument search(String label) throws LabelNotFoundException {
        LabelDocument labelDocument = esConnector.search(label);
        if (labelDocument != null) {
            return labelDocument;
        }
        throw new LabelNotFoundException(ErrorCodes.LABEL_NOT_FOUND.getCode(),
                ErrorCodes.LABEL_NOT_FOUND.getMessage());
    }

    /**
     * Find the label in requested language.
     *
     * @param label
     *    should be in english language.
     * @param language
     *    should be language.
     * @return
     *    translation in requested language.
     */
    @Override
    public LabelDocument find(String label, String language) throws LabelNotFoundException {
        LabelDocument labelDocument = esConnector.search(label, language);
        if (labelDocument != null) {
            return labelDocument;
        }
        throw new LabelNotFoundException(ErrorCodes.LABEL_NOT_FOUND.getCode(),
                ErrorCodes.LABEL_NOT_FOUND.getMessage());
    }

    /**
     * Identifies the supplied text language.
     *
     * @param text
     *      text in any language.
     * @return
     *      language.
     */
    @Override
    public String recognize(String text) {
        return null;
    }
}
