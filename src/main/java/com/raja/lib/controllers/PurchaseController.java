package com.raja.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.raja.lib.DbService.PurchaseDbService;
import com.raja.lib.payload.request.PurchaseRequestDto;
import com.raja.lib.payload.response.GetPurchaseReponseDto;
import com.raja.lib.payload.response.PurchaseResponseDto;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseDbService purchaseService;

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PurchaseResponseDto> createPurchase(@RequestBody PurchaseRequestDto requestDto) {
		PurchaseResponseDto responseDto = purchaseService.createPurchase(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}

	@GetMapping("/{purchaseId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<GetPurchaseReponseDto> getPurchaseDetails(@PathVariable Long purchaseId) {
		GetPurchaseReponseDto responseDto = purchaseService.getPurchaseDetails(purchaseId);
		if (responseDto != null) {
			return ResponseEntity.ok(responseDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('USER')")
	public List<GetPurchaseReponseDto> getAllPurchaseDetails() {
		return purchaseService.getAllPurchaseDetails();
	}

}
