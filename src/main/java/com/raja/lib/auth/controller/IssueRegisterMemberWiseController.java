package com.raja.lib.auth.controller;

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

import com.raja.lib.auth.service.IssueRegisterMemberWiseService;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class IssueRegisterMemberWiseController {

    @Autowired
    private IssueRegisterMemberWiseService issueRegisterMemberWiseService;

    @PostMapping("/issue-member-wise")
    public ResponseEntity<byte[]> getIssueReportMemberWise(@RequestBody Map<String, String> dateRange) {
        try {
            String startDateStr = dateRange.get("startDate");
            String endDateStr = dateRange.get("endDate");
            String fullName = dateRange.get("fullName");

            // Define the date format
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

            // Parse the date strings to java.util.Date
            Date startDate = formatter.parse(startDateStr);
            Date endDate = formatter.parse(endDateStr);

            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

            // Generate the report
            ByteArrayOutputStream outputStream = issueRegisterMemberWiseService.generateReport(sqlStartDate, sqlEndDate, fullName);

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=IssueRegisterMemberWise.pdf");

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
