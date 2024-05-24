package com.raja.lib.invt.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.request.MemberMonthlyFeesRequest;
import com.raja.lib.invt.resposne.MemberMonthlyFeesResponse;
import com.raja.lib.invt.service.MemberMonthlyFeesService;

@RestController
@RequestMapping("/api/monthly-member-fees")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MemberMonthlyFeesController {

    @Autowired
    private MemberMonthlyFeesService service;

    @GetMapping
    public ResponseEntity<List<MemberMonthlyFeesResponse>> getAllFees() {
        return ResponseEntity.ok(service.getAllFees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberMonthlyFeesResponse> getFeeById(@PathVariable int id) {
        return ResponseEntity.ok(service.getFeeById(id));
    }

    @PostMapping
    public ResponseEntity<MemberMonthlyFeesResponse> createFee(@RequestBody MemberMonthlyFeesRequest request) {
        return ResponseEntity.ok(service.createFee(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberMonthlyFeesResponse> updateFee(@PathVariable int id, @RequestBody MemberMonthlyFeesRequest request) {
        return ResponseEntity.ok(service.updateFee(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFee(@PathVariable int id) {
        service.deleteFee(id);
        return ResponseEntity.noContent().build();
    }
}
