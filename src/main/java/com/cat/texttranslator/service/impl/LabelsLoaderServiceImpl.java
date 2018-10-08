package com.cat.texttranslator.service.impl;

import com.cat.texttranslator.api.model.Error;
import com.cat.texttranslator.api.model.TimeStamp;
import com.cat.texttranslator.api.model.Translation;
import com.cat.texttranslator.config.Languages;
import com.cat.texttranslator.connectors.ElasticSearchConnector;
import com.cat.texttranslator.constant.AppConstants;
import com.cat.texttranslator.constant.ErrorCodes;
import com.cat.texttranslator.entity.LabelDocument;
import com.cat.texttranslator.exception.MalformedExcelFileException;
import com.cat.texttranslator.exception.MalformedJsonPayloadException;
import com.cat.texttranslator.service.LabelsLoaderService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class LabelsLoaderServiceImpl implements LabelsLoaderService {

    @Autowired
    private Languages languages;

    @Autowired
    private ElasticSearchConnector esConnector;

    /**
     * Loads the excel sheet data into system, expected excel format should be.
     * <p>
     * |English|Danish|French |
     * |Domain |Domæne|Domaine|
     * <p>
     * First row considered as Languages.
     *
     * @param data
     * @return no of rows processed successfully.
     */
    @Override
    public int load(InputStream data) throws MalformedExcelFileException, IOException {
        Workbook workbook = WorkbookFactory.create(data);
        //Interested in 0th sheet.
        Sheet sheet = getSheetAtIndex0(workbook);
        //preparing header and index map.
        Map<String, Integer> langCellIndex = createHeaderMap(sheet);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                System.out.println("Header skipping.");
                continue;
            }
            processRow(row, langCellIndex);
        }
        //excluding header row.
        return sheet.getPhysicalNumberOfRows() - 1;
    }

    private Sheet getSheetAtIndex0(Workbook workbook) throws MalformedExcelFileException {
        if (workbook.getNumberOfSheets() > 0) {
            return workbook.getSheetAt(0);
        }
        throw new MalformedExcelFileException(ErrorCodes.INVALID_EXCEL_FILE.getCode(),
                ErrorCodes.INVALID_EXCEL_FILE.getMessage());
    }

    private void processRow(Row row, Map<String, Integer> langCellIndex) {
        Map<String, Translation> translations = new TreeMap<>();
        langCellIndex.forEach((s, integer) ->
                translations.put(s, buildTranslation(row.getCell(integer), s)));

        //assuming 0th index is english labels.
        LabelDocument labelDocument = LabelDocument.builder()
                .label(row.getCell(0).getStringCellValue())
                .translationMap(translations)
                .translations(new ArrayList<>(translations.values()))
                .timeStamp(
                        TimeStamp.builder()
                                .created(LocalDateTime.now())
                                .build()
                ).build();
        esConnector.index(labelDocument.getLabel(), labelDocument);
    }

    private Translation buildTranslation(Cell cell, String s) {
        return Translation.builder()
                .value(cell.getStringCellValue())
                .code(languages.getMap().get(s))
                .language(s)
                .timeStamp(TimeStamp.builder()
                        .created(LocalDateTime.now())
                        .build()
                )
                .build();
    }

    private Map<String, Integer> createHeaderMap(Sheet sheet) throws MalformedExcelFileException {
        if (sheet.getPhysicalNumberOfRows() > 0) {
            Row header = sheet.getRow(0);
            if (validateHeader(header)) {
                Map<String, Integer> langCellMap = new HashMap<>();
                for (Cell cell : header) {
                    langCellMap.put(cell.getStringCellValue(), cell.getColumnIndex());
                }
                return langCellMap;
            }
        }
        throw new MalformedExcelFileException(ErrorCodes.INVALID_EXCEL_FILE.getCode(),
                ErrorCodes.INVALID_EXCEL_FILE.getMessage());
    }

    private boolean validateHeader(Row header) throws MalformedExcelFileException {
        if (header.getPhysicalNumberOfCells() > 1 &&
                StringUtils.equalsIgnoreCase(header.getCell(0).getStringCellValue(), AppConstants.LABEL)) {
            return true;
        }
        throw new MalformedExcelFileException(ErrorCodes.INVALID_EXCEL_FILE.getCode(),
                ErrorCodes.INVALID_EXCEL_FILE.getMessage());
    }


    /**
     * Takes json input and store labels in system.
     *
     * @param label
     *      label in english.
     * @param translations
     *      list of translations for requested label.
     * @return 1 if successful, else 0.
     */
    @Override
    public int post(String label, List<Translation> translations) throws MalformedJsonPayloadException {
        Map<String, Translation> translationMap = buildTranslation(translations);
        LabelDocument labelDocument = LabelDocument.builder().label(label)
                .timeStamp(
                        TimeStamp.builder()
                                .created(LocalDateTime.now())
                                .build()
                ).translationMap(translationMap)
                .translations(new ArrayList<>(translationMap.values()))
                .build();
        esConnector.index(label, labelDocument);
        return 1;
    }

    private Map<String, Translation> buildTranslation(List<Translation> translations) throws MalformedJsonPayloadException {
        if (CollectionUtils.isNotEmpty(translations)) {
            final Map<String, Translation> translationMap = new TreeMap<>();
            translations.forEach(translation -> {
                Translation t = Translation.builder()
                        .code(languages.getMap().get(translation.getLanguage()))
                        .language(translation.getLanguage())
                        .value(translation.getValue())
                        .timeStamp(
                                TimeStamp.builder()
                                        .created(LocalDateTime.now())
                                        .build()
                        ).build();
                translationMap.put(translation.getLanguage(), t);
            });
            return translationMap;
        }
        throw new MalformedJsonPayloadException(ErrorCodes.INVALID_JSON_PAYLOAD.getCode(),
                ErrorCodes.INVALID_JSON_PAYLOAD.getMessage());
    }
}
