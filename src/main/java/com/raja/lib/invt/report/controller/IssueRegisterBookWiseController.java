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

import com.raja.lib.invt.report.service.IssueRegisterBookWiseService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class IssueRegisterBookWiseController {

    @Autowired
    private IssueRegisterBookWiseService issueRegisterBookWiseService;

    @PostMapping("/issue-book-wise")
    public ResponseEntity<byte[]> getIssueReportBookWise(@RequestBody Map<String, String> dateRange) {
        try {
            String startDate = dateRange.get("startDate");
            String endDate = dateRange.get("endDate");
            String bookId = dateRange.get("bookId");
            System.out.println("S date "+startDate);
            System.out.println("e date "+endDate);
            System.out.println("bookId "+bookId);
            // Generate the report
            ByteArrayOutputStream outputStream = issueRegisterBookWiseService.generateReport(startDate, endDate, bookId);

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=IssueRegisterBookWise.pdf");

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
