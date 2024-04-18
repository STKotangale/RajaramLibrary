package com.raja.lib.invt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.model.Ledger;
import com.raja.lib.invt.request.LedgerRequest;
import com.raja.lib.invt.service.LedgerServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ledger")
public class LedgerControlller {

	private final LedgerServiceImpl ledgerService;
	private static final Logger logger = LoggerFactory.getLogger(LedgerControlller.class);

	public LedgerControlller(LedgerServiceImpl ledgerService) {
		this.ledgerService = ledgerService;
	}

	@PostMapping("")
	public ResponseEntity<Ledger> saveLedger(@RequestBody LedgerRequest ledgerRequest) {
		try {
			Ledger savedLedger = ledgerService.saveLedger(ledgerRequest);
			return new ResponseEntity<>(savedLedger, HttpStatus.CREATED);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<Ledger>> getAllLedger() {
		try {
			List<Ledger> ledgers = ledgerService.getAllLedger();
			return ResponseEntity.ok(ledgers);
		} catch (Exception e) {
			logger.error("Failed to retrieve all ledgers", e);
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getLedgerById(@PathVariable int id) {
		Optional<Ledger> ledgerOptional = ledgerService.getLedgerById(id);
		if (!ledgerOptional.isPresent()) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("message", "No entry found for this ID.");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ledgerOptional.get(), HttpStatus.OK);
	}
	

}
