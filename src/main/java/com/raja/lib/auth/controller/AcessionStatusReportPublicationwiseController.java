package com.raja.lib.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.auth.service.AcessionStatusReportPublicationwiseService;

import java.io.ByteArrayOutputStream;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reports")
public class AcessionStatusReportPublicationwiseController {

    @Autowired
    private AcessionStatusReportPublicationwiseService reportService;

    @GetMapping("/acession-status-publicationwise/{publicationName}")
    public ResponseEntity<byte[]> getAcessionStatusReportPublicationwise(@PathVariable String publicationName) {
        try {
            ByteArrayOutputStream outputStream = reportService.generateAcessionStatusReportPublicationwise(publicationName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=AcessionStatusReportPublicationwise.pdf");

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
