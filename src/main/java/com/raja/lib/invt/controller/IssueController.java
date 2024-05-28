package com.raja.lib.invt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.objects.GetAllIssueBookDetailsByUsername;
import com.raja.lib.invt.objects.GetIssueDetilsByUser;
import com.raja.lib.invt.request.BookIssueRequestDto;
import com.raja.lib.invt.request.BookIssueReturnRequestDTO;
import com.raja.lib.invt.request.BookLostRequestDTO;
import com.raja.lib.invt.request.PurchaseReturnRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.PurchaseReturnDTO;
import com.raja.lib.invt.service.BookDetailsService;
import com.raja.lib.invt.service.StockService;

@RestController
@RequestMapping("/api/issue")
@CrossOrigin(origins = "*", maxAge = 3600)
public class IssueController {

	@Autowired
	private StockService stockService;
	
	@Autowired
	private BookDetailsService bookDetailservice;
	
	

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

	@DeleteMapping("/{stockId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBookIssue(@PathVariable int stockId) {
        try {
        	stockService.deleteBookIssue(stockId);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Book issue deleted successfully", null, HttpStatus.OK.value()));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
	 
//    ------------------------------------------------- Issue Return---------------------------------------------------
	
	@GetMapping("/detail/{username}")
	public List<GetAllIssueBookDetailsByUsername> getStockDetailsByUsername(@PathVariable String username) {
	    return stockService.getStockDetailsByUsername(username);
	}
	
	@PostMapping("return/create")
    public ApiResponseDTO<Void> createIssueReturn(@RequestBody BookIssueReturnRequestDTO bookIssueReturnRequestDTO) {
        try {
            return stockService.createIssueReturn(bookIssueReturnRequestDTO);
        } catch (NotFoundException e) {
            return new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.NOT_FOUND.value());
        }
    }
	
	@GetMapping("/issueReturns")
    public List<GetIssueDetilsByUser> getAllIssueReturns() {
        return stockService.findAllIssueReturn();
    }
	
	
//  ------------------------------------------------- Purchase Return---------------------------------------------------


	@GetMapping("/details/{bookName}")
    public Map<String, Object> getBookDetails(@PathVariable String bookName) {
        return bookDetailservice.getBookDetailsByBookName(bookName);
    }
	
	@PostMapping("/purchase-return")
    public ResponseEntity<ApiResponseDTO<Void>> createPurchaseReturn(@RequestBody PurchaseReturnRequestDTO purchaseReturnRequestDTO) {
        try {
            ApiResponseDTO<Void> response = stockService.createPurchaseReturn(purchaseReturnRequestDTO);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.NOT_FOUND.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

	
	
	 @GetMapping("/purchase-return-all")
	    public List<PurchaseReturnDTO> getStockDetails() {
	        return stockService.getStockDetailsByType();
	    }
	 
	//  ------------------------------------------------- Book Lost---------------------------------------------------
	 
	 @PostMapping("/book-lost")
	    public ResponseEntity<ApiResponseDTO<Void>> createBookLost(@RequestBody PurchaseReturnRequestDTO purchaseReturnRequestDTO) {
	        try {
	            ApiResponseDTO<Void> response = stockService.createBookLost(purchaseReturnRequestDTO);
	            return ResponseEntity.status(response.getStatusCode()).body(response);
	        } catch (NotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.NOT_FOUND.value()));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        }
	    }
	 
	 @GetMapping("/book-lost-all")
	    public List<PurchaseReturnDTO> getLostDetials() {
	        return stockService.getLostDetials();
	    }

		//  ------------------------------------------------- Book scrap---------------------------------------------------
	 
	 @PostMapping("/book-scrap")
	    public ResponseEntity<ApiResponseDTO<Void>> createBookScrap(@RequestBody BookLostRequestDTO bookLostRequestDTO) {
	        try {
	            ApiResponseDTO<Void> response = stockService.createBookScrap(bookLostRequestDTO);
	            return ResponseEntity.status(response.getStatusCode()).body(response);
	        } catch (NotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.NOT_FOUND.value()));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        }
	    }
	 
	 @GetMapping("/book-scrap-all")
	    public List<PurchaseReturnDTO> geScraptDetials() {
	        return stockService.getScarpDetials();
	    }

	 
}
