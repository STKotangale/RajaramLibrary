package com.raja.lib.invt.report.controller;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.raja.lib.invt.report.service.AcessionReportService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class AcessionReportController {

    @Autowired
    private AcessionReportService acessionReportService;

    @PostMapping("/acession")
    public ResponseEntity<byte[]> getAcessionReport() {
        try {
            ByteArrayOutputStream outputStream = acessionReportService.generateReport();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=AcessionReport.pdf");

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}