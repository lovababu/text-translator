package com.cat.texttranslator.service.impl;

import com.cat.texttranslator.connectors.ElasticSearchConnector;
import com.cat.texttranslator.entity.LabelDocument;
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
    public LabelDocument search(String label) {
        return esConnector.search(label);
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
    public LabelDocument find(String label, String language) {
        return esConnector.search(label, language);
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
