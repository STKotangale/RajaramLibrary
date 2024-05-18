package com.raja.lib.invt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.request.BookIssueRequestDto;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.service.StockService;

@RestController
@RequestMapping("/api/issue")
@CrossOrigin(origins = "*", maxAge = 3600)
public class IssueController {

	@Autowired
	private StockService stockService;

	@PostMapping("/book-issue")
	public ResponseEntity<ApiResponseDTO<Stock>> bookIssue(@RequestBody BookIssueRequestDto bookIssueRequestDto) {
		try {
			ApiResponseDTO<Stock> response = stockService.bookIssue(bookIssueRequestDto);
			return ResponseEntity.status(response.getStatusCode()).body(response);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.NOT_FOUND.value()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}

	@PutMapping("/book-issue/{stockId}")
	public ResponseEntity<ApiResponseDTO<Stock>> updateBookIssue(@PathVariable int stockId,
			@RequestBody BookIssueRequestDto bookIssueRequestDto) {
		try {
			ApiResponseDTO<Stock> response = stockService.updateBookIssue(stockId, bookIssueRequestDto);
			return ResponseEntity.status(response.getStatusCode()).body(response);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.NOT_FOUND.value()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
		}
	}

	@GetMapping(value = "/all", produces = "application/json")
	public String getStockDetailsAsJson() {
		return stockService.getStockDetailsAsJson();
	}

}
