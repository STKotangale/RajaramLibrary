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

import com.raja.lib.invt.report.service.AcessionStatusReportBookTypewiseService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class AcessionStatusReportBookTypewiseController {
    
    @Autowired
    private AcessionStatusReportBookTypewiseService reportService;

    @PostMapping("/acession-status-booktype-wise")
    public ResponseEntity<byte[]> getAcessionStatusReportBookTypewise(@RequestBody Map<String, String> dateRange) {
        try {
            String bookTypeId = dateRange.get("bookTypeId");
        	String bookTypeName = dateRange.get("bookTypeName");
        	
            ByteArrayOutputStream outputStream = reportService.generateAcessionStatusReportPublicationwise(bookTypeId, bookTypeName);

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
