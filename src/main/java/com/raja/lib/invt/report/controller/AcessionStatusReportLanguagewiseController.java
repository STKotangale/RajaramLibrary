package com.raja.lib.invt.report.controller;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.report.service.AcessionStatusReportLanguagewiseService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class AcessionStatusReportLanguagewiseController {
    
    @Autowired
    private AcessionStatusReportLanguagewiseService acessionStatusReportLanguagewiseService;

    @PostMapping("/acession-status-languagewise")
    public ResponseEntity<byte[]> getAcessionStatusReportBookTypewise(@RequestBody Map<String, String> dateRange) {
        try {
        	
        	String bookLangId = dateRange.get("bookLangId");
        	String bookLangName = dateRange.get("bookLangName");

        	ByteArrayOutputStream outputStream = acessionStatusReportLanguagewiseService.generateAcessionStatusReportPublicationwise(bookLangId, bookLangName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=AcessionStatusReportBookTypewise.pdf");
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
