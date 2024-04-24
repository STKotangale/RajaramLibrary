package com.raja.lib.invt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.objects.BookDetail;
import com.raja.lib.invt.objects.BookName;
import com.raja.lib.invt.objects.BookRate;
import com.raja.lib.invt.request.PurchaseRequestDto;
import com.raja.lib.invt.request.UpdateBookDetailsRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.PurchaseResponseDto;
import com.raja.lib.invt.resposne.PurchaseResponseDtos;
import com.raja.lib.invt.service.BookDetailsService;
import com.raja.lib.invt.service.PurchaseServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseServiceImpl purchaseService;

	@Autowired
	BookDetailsService bookDetailsService;

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<PurchaseResponseDto> createPurchase(@RequestBody PurchaseRequestDto requestDto) {
		PurchaseResponseDto responseDto = purchaseService.createPurchase(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}

	@GetMapping("/{purchaseId}")
	public ResponseEntity<PurchaseResponseDtos> getPurchaseById(@PathVariable Long purchaseId) {
		try {
			PurchaseResponseDtos purchaseResponse = purchaseService.getPurchaseById(purchaseId);
			return ResponseEntity.ok(purchaseResponse); // Return 200 OK with the PurchaseResponseDtos object
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build(); // Return 404 Not Found if Purchase is not found
		}
	}

	@GetMapping("")
	public ResponseEntity<List<PurchaseResponseDtos>> getAllPurchases() {
		List<PurchaseResponseDtos> purchases = purchaseService.getAllPurchases();
		return ResponseEntity.ok(purchases); // Return 200 OK with the list of purchases
	}

	@PutMapping("/{purchaseId}")
	public ResponseEntity<PurchaseResponseDto> updatePurchase(@PathVariable Long purchaseId,
			@RequestBody PurchaseRequestDto requestDto) {
		PurchaseResponseDto responseDto = purchaseService.updatePurchase(purchaseId, requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{purchaseId}")
	public ResponseEntity<ApiResponseDTO<Object>> deletePurchase(@PathVariable Long purchaseId) {
		ApiResponseDTO<Object> response = purchaseService.deletePurchase(purchaseId);
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}

	@GetMapping("/book/{bookname}")
	public ResponseEntity<BookRate> getBookRate(@PathVariable("bookname") String bookName) {
		BookRate bookRate = purchaseService.getBookRate(bookName);
		if (bookRate == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bookRate);
	}

	@GetMapping("/books")
	public ResponseEntity<List<BookName>> getBookNames() {
		List<BookName> bookNames = purchaseService.getBookNames();
		if (bookNames.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bookNames);
	}

	@GetMapping("/book-details")
	List<BookDetail> findBooksByNullIsbn() {
		return bookDetailsService.findBooksByNullIsbn();
	}

	@PostMapping("/update/book-details/{id}")
	public Map<String, Object> updateBookDetails(@PathVariable Long id, @RequestBody UpdateBookDetailsRequest request) {
		Map<String, Object> response = new HashMap<>();
		try {
			String result = bookDetailsService.updateBookDetails(id, request);
			response.put("success", true);
			response.put("message", result);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", e.getMessage());
		}
		return response;
	}

}
