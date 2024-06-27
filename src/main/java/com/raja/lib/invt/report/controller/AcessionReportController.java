package com.raja.lib.invt.report.controller;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.raja.lib.invt.report.service.ReportService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class AcessionReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/acession")
    public ResponseEntity<byte[]> getAcessionReport() {
        try {
            System.out.println("SK1 ");
            ByteArrayOutputStream outputStream = reportService.generateReport();
            System.out.println("SK2 ");

            HttpHeaders headers = new HttpHeaders();
            System.out.println("SK3 ");
            headers.setContentType(MediaType.APPLICATION_PDF);
            System.out.println("SK4 ");
            headers.add("Content-Disposition", "inline; filename=AcessionReport.pdf");
            System.out.println("SK5 ");

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
