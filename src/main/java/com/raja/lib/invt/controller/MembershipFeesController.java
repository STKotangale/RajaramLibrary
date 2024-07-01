package com.raja.lib.invt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.raja.lib.invt.request.MemberCheckRequestDTO;
import com.raja.lib.invt.request.MembershipFeesRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.MembershipFeesResponse;
import com.raja.lib.invt.service.MembershipFeesService;

@RestController
@RequestMapping("/api/membership-fees")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MembershipFeesController {

    @Autowired
    private MembershipFeesService service;

    @GetMapping
    public ResponseEntity<List<MembershipFeesResponse>> getAllFees() {
        return ResponseEntity.ok(service.getAllFees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipFeesResponse> getFeeById(@PathVariable int id) {
        return ResponseEntity.ok(service.getFeeById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<String>> createFee(@RequestBody MembershipFeesRequest request) {
        ApiResponseDTO<String> response = service.createFee(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> updateFee(@PathVariable int id, @RequestBody MembershipFeesRequest request) {
        ApiResponseDTO<String> response = service.updateFee(id, request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteFee(@PathVariable int id) {
        ApiResponseDTO<String> response = service.deleteFee(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/check-member-fees")
    public ResponseEntity<ApiResponseDTO<String>> checkMemberFees(@RequestBody MemberCheckRequestDTO request) {
        ApiResponseDTO<String> response = service.checkMemberAndDate(request);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/nextInvoiceNumber")
    public ApiResponse getNextInvoiceNumber() {
        int nextInvoiceNumber = service.getNextInvoiceNumber();
        return new ApiResponse(nextInvoiceNumber);
    }

    static class ApiResponse {
        private final int NextMonthlyMemberInvoiceNo;

        public ApiResponse(int NextMonthlyMemberInvoiceNo) {
            this.NextMonthlyMemberInvoiceNo = NextMonthlyMemberInvoiceNo;
        }

        public int getNextMonthlyMemberInvoiceNo() {
            return NextMonthlyMemberInvoiceNo;
        }
    }
    
    @GetMapping("/nextInvoiceMembershipNo")
    public ApiResponse getNextInvoiceNumbers() {
        int nextInvoiceNumber = service.getNextInvoiceMembershipNo();
        return new ApiResponse(nextInvoiceNumber);
    }

    
    
}

