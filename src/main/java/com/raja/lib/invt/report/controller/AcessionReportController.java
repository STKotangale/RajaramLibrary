package com.raja.lib.invt.report.controller;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
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

import com.raja.lib.invt.report.service.ReportService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class AcessionReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/acession")
    public ResponseEntity<byte[]> getIssueReport(@RequestBody Map<String, String> dateRange) {
        try {
            System.out.println("SK1 ");
               
            // Generate the report
            ByteArrayOutputStream outputStream = reportService.generateReport();
            System.out.println("SK2 ");

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            System.out.println("SK3 ");
            headers.setContentType(MediaType.APPLICATION_PDF);
            System.out.println("SK4 ");
            headers.add("Content-Disposition", "inline; filename=IssueReport.pdf");
            System.out.println("SK5 ");

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
