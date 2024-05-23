package com.raja.lib.tools;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelDataConversionController  {

    private static final Logger logger = LoggerFactory.getLogger(ExcelDataConversionController.class);

    @Autowired
    private ExcelDataConversionService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        logger.debug("Received file: {}", file.getOriginalFilename());
        try {
            List<ExcelModel> entities = parseExcelFile(file);
            logger.debug("Parsed {} entries from Excel file.", entities.size());
            service.saveAll(entities);
            logger.debug("Data saved successfully.");
            return ResponseEntity.ok("File uploaded and data saved successfully.");
        } catch (Exception e) {
            logger.error("Error uploading and saving data: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload and save data.");
        }
    }

    private List<ExcelModel> parseExcelFile(MultipartFile file) throws IOException {
        List<ExcelModel> entities = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Skip header row
                    continue;
                }
                ExcelModel entity = new ExcelModel();
                entity.setAccessionNo(getCellValue(row, 0));
                entity.setDateOfAccessioned(getCellValue(row, 1));
                entity.setBooksName(getCellValue(row, 2));
                entity.setAutherName(getCellValue(row, 3));
                entity.setPublisheName(getCellValue(row, 4));
                entity.setYearOfPublication(getCellValue(row, 5));
                entity.setEdition(getCellValue(row, 6));
                entity.setPages(getCellValue(row, 7));
                entity.setBooksprice(getCellValue(row, 8));
                entity.setNoOfCopies(getCellValue(row, 9));
                entity.setLocation(getCellValue(row, 10));
                entity.setKadambarino(getCellValue(row, 11));
                entities.add(entity);
            }
        }
        return entities;
    }

    private String getCellValue(Row row, int cellIndex) {
        if (row.getCell(cellIndex) == null) {
            return null;
        }
        switch (row.getCell(cellIndex).getCellType()) {
            case STRING:
                return row.getCell(cellIndex).getStringCellValue();
            case NUMERIC:
                return String.valueOf(row.getCell(cellIndex).getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(row.getCell(cellIndex).getBooleanCellValue());
            case FORMULA:
                return row.getCell(cellIndex).getCellFormula();
            default:
                return null;
        }
    }
}