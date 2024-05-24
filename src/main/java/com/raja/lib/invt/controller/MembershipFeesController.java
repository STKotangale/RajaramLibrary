package com.raja.lib.invt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.request.MembershipFeesRequest;
import com.raja.lib.invt.resposne.MembershipFeesResponse;
import com.raja.lib.invt.service.MembershipFeesService;

@RestController
@RequestMapping("/api/membership-fees")
public class MembershipFeesController {

    @Autowired
    private MembershipFeesService service;

    @GetMapping
    public ResponseEntity<List<MembershipFeesResponse>> getAllFees() {
        return ResponseEntity.ok(service.getAllFees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipFeesResponse> getFeeById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getFeeById(id));
    }

    @PostMapping
    public ResponseEntity<MembershipFeesResponse> createFee(@RequestBody MembershipFeesRequest request) {
        return ResponseEntity.ok(service.createFee(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembershipFeesResponse> updateFee(@PathVariable Long id, @RequestBody MembershipFeesRequest request) {
        return ResponseEntity.ok(service.updateFee(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFee(@PathVariable Long id) {
        service.deleteFee(id);
        return ResponseEntity.noContent().build();
    }
}
