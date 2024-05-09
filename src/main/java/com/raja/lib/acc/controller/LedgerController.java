package com.raja.lib.acc.controller;

import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.request.LedgerRequestDTO;
import com.raja.lib.acc.response.ApiResponseDTO;
import com.raja.lib.acc.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Ledger>> createLedger(@RequestBody LedgerRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ledgerService.createLedger(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Ledger>> getLedgerById(@PathVariable("id") int ledgerId) {
        return ResponseEntity.ok(ledgerService.getLedgerById(ledgerId));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<Ledger>>> getAllLedgers() {
        return ResponseEntity.ok(ledgerService.getAllLedgers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Ledger>> updateLedger(@PathVariable("id") int ledgerId, @RequestBody LedgerRequestDTO request) {
        return ResponseEntity.ok(ledgerService.updateLedger(ledgerId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteLedger(@PathVariable("id") int ledgerId) {
        return ResponseEntity.ok(ledgerService.deleteLedger(ledgerId));
    }
}

