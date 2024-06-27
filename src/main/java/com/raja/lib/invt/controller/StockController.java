package com.raja.lib.invt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.objects.StockModel;
import com.raja.lib.invt.request.StockRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.StockInvoiceResponseDTO;
import com.raja.lib.invt.resposne.StockResponseDTO;
import com.raja.lib.invt.service.StockService;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StockController {

    @Autowired
    private StockService stockService;
    
    
    @GetMapping("/latest-purchaseNo")
    public ResponseEntity<Map<String, String>> getNextPurchaseNo() {
        Map<String, String> response = new HashMap<>();
        response.put("nextInvoiceNo", stockService.getNextPurchaseNo());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest-issueNo")
    public ResponseEntity<Map<String, String>> getNextIssueNo() {
        Map<String, String> response = new HashMap<>();
        response.put("nextInvoiceNo", stockService.getNextIssueNo());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest-issueReturnNo")
    public ResponseEntity<Map<String, String>> getNextIssueReturnNo() {
        Map<String, String> response = new HashMap<>();
        response.put("nextInvoiceNo", stockService.getNextIssueReturnNo());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest-purchaseReturnNo")
    public ResponseEntity<Map<String, String>> getNextPurchaseReturnNo() {
        Map<String, String> response = new HashMap<>();
        response.put("nextInvoiceNo", stockService.getNextPurchaseReturnNo());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest-bookLostNo")
    public ResponseEntity<Map<String, String>> getNextBookLostNo() {
        Map<String, String> response = new HashMap<>();
        response.put("nextInvoiceNo", stockService.getNextBookLostNo());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest-bookScrapNo")
    public ResponseEntity<Map<String, String>> getNextBookScrapNo() {
        Map<String, String> response = new HashMap<>();
        response.put("nextInvoiceNo", stockService.getNextBookScrapNo());
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponseDTO<Void>> createStock(@RequestBody StockRequestDTO stockRequestDTO) {
        try {
            ApiResponseDTO<Void> response = stockService.createStock(stockRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, "Internal server error", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<StockResponseDTO>> getStockById(@PathVariable int id) {
        ApiResponseDTO<StockResponseDTO> response = stockService.getStockById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDTO<List<StockInvoiceResponseDTO>>> getAllStocks(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        ApiResponseDTO<List<StockInvoiceResponseDTO>> response = stockService.getStockDetails(startDate, endDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
   
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteStockById(@PathVariable int id) {
        ApiResponseDTO<Void> response = stockService.deleteStockById(id);
		return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @GetMapping("only")
    public ResponseEntity<List<StockModel>> getAllStocksonly() {
        List<StockModel> stocks = stockService.getStockDetials();
        return ResponseEntity.ok().body(stocks);
    }
    
   
}

