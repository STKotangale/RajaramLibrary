package com.raja.lib.invt.report.controller;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
<<<<<<< HEAD
import org.springframework.web.bind.annotation.CrossOrigin;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
>>>>>>> phase1
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.web.bind.annotation.*;
>>>>>>> reports
=======
import org.springframework.web.bind.annotation.*;
>>>>>>> sandesh-spring-new

import com.raja.lib.invt.report.service.AcessionReportService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class AcessionReportController {

    @Autowired
    private AcessionReportService acessionReportService;

    @PostMapping("/acession")
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public ResponseEntity<byte[]> getIssueReport(@RequestBody Map<String, String> dateRange) {
=======
    public ResponseEntity<byte[]> getAcessionReport() {
>>>>>>> phase1
=======
    public ResponseEntity<byte[]> getAcessionReport() {
>>>>>>> reports
=======
    public ResponseEntity<byte[]> getAcessionReport() {
>>>>>>> sandesh-spring-new
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