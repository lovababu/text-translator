package com.cat.texttranslator.connectors;

import com.cat.texttranslator.api.model.TimeStamp;
import com.cat.texttranslator.api.model.Translation;
import com.cat.texttranslator.entity.LabelDocument;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class ElasticSearchConnector {

    Map<String, LabelDocument> inMemoryDataStore = new HashMap<>();

    public void index(String key, LabelDocument labelDocument) {
        //TODO: ideally should connect to ES and index the document.
        if (inMemoryDataStore.containsKey(key)) {
            LabelDocument inMemDocument = inMemoryDataStore.get(key);
            updateDocument(key, inMemDocument, labelDocument);
        } else {
            inMemoryDataStore.put(key, labelDocument);
        }
    }

    private void updateDocument(String key, LabelDocument inMemDocument, LabelDocument labelDocument) {
        Map<String, Translation> translationMap = inMemDocument.getTranslations();
        translationMap.putAll(labelDocument.getTranslations());
        inMemoryDataStore.put(key, LabelDocument.builder()
                .label(key)
                .timeStamp(
                        TimeStamp.builder()
                                .created(inMemDocument.getTimeStamp().getCreated())
                                .modified(LocalDateTime.now())
                                .build()
                )
                .translations(translationMap)
                .build()
        );
    }

    public LabelDocument search(String key) {

        return MapUtils.isNotEmpty(inMemoryDataStore) ? inMemoryDataStore.get(key) : null;
    }

    public LabelDocument search(String key, String lang) {
        LabelDocument labelDocument = inMemoryDataStore.get(key);
        if (labelDocument != null) {
            Translation translation = labelDocument.getTranslations().get(lang);
            return LabelDocument.builder()
                    .translations(
                            Collections.singletonMap(lang, translation)
                    )
                    .timeStamp(labelDocument.getTimeStamp())
                    .label(key)
                    .build();
        }
        return null;
    }
}
