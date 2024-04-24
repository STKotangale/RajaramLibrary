package com.raja.lib.acc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.request.LedgerRequestDTO;
import com.raja.lib.acc.response.ApiResponseDTO;
import com.raja.lib.acc.service.LedgerService;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Ledger>> createLedger(@RequestBody LedgerRequestDTO request) {
        Ledger createdLedger = ledgerService.createLedger(request);
        if (createdLedger != null) {
            ApiResponseDTO<Ledger> response = new ApiResponseDTO<>(true, "Ledger created successfully", createdLedger, HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Ledger>> getLedgerById(@PathVariable("id") int ledgerId) {
        Ledger ledger = ledgerService.getLedgerById(ledgerId);
        if (ledger != null) {
            ApiResponseDTO<Ledger> response = new ApiResponseDTO<>(true, "Ledger found", ledger, HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<Ledger>>> getAllLedgers() {
        List<Ledger> ledgers = ledgerService.getAllLedgers();
        ApiResponseDTO<List<Ledger>> response = new ApiResponseDTO<>(true, "Ledgers retrieved successfully", ledgers, HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Ledger>> updateLedger(@PathVariable("id") int ledgerId, @RequestBody LedgerRequestDTO request) {
        Ledger updatedLedger = ledgerService.updateLedger(ledgerId, request);
        if (updatedLedger != null) {
            ApiResponseDTO<Ledger> response = new ApiResponseDTO<>(true, "Ledger updated successfully", updatedLedger, HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLedger(@PathVariable("id") int ledgerId) {
        ledgerService.deleteLedger(ledgerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
