package com.raja.lib.invt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.request.StockRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.StockResponseDTO;
import com.raja.lib.invt.service.StockService;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDTO<Stock>> createStock(@RequestBody StockRequestDTO stockRequestDTO) {
        try {
            ApiResponseDTO<Stock> response = stockService.createStock(stockRequestDTO);
            return ResponseEntity.ok(response);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.NOT_FOUND.value()));
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
    public ResponseEntity<ApiResponseDTO<List<StockResponseDTO>>> getAllStocks() {
        ApiResponseDTO<List<StockResponseDTO>> response = stockService.getAllStocks();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
   
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteStockById(@PathVariable int id) {
        try {
            ApiResponseDTO<Void> response = stockService.deleteStockById(id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.NOT_FOUND.value()));
        }
    }
    
   
}

