package com.raja.lib.invt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.request.LibraryFeeRequest;
import com.raja.lib.invt.resposne.LibraryFeeResponse;
import com.raja.lib.invt.service.LibraryFeeService;

@RestController
@RequestMapping("/api/fees")
public class LibraryFeeController {

    @Autowired
    private LibraryFeeService service;

    @GetMapping
    public List<LibraryFeeResponse> getAllFees() {
        return service.getAllFees();
    }

    @PutMapping("/{feesId}")
    public ResponseEntity<LibraryFeeResponse> updateFee(@PathVariable Long feesId, @RequestBody LibraryFeeRequest request) {
        LibraryFeeResponse response = service.updateFee(feesId, request);
        return ResponseEntity.ok(response);
    }
}
