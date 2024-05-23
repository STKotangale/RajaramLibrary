package com.raja.lib.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelDataConversionService  {

    private static final Logger logger = LoggerFactory.getLogger(ExcelDataConversionService.class);

    @Autowired
    private ExcelDataConversionRepository repository;

    public void saveAll(List<ExcelModel> entities) {
        logger.debug("Saving {} entries to the database.", entities.size());
        repository.saveAll(entities);
        logger.debug("Data saved successfully.");
    }
}
