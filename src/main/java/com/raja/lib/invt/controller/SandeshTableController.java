package com.raja.lib.invt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.model.SandeshTable;
import com.raja.lib.invt.request.SandeshTableRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.service.SandeshTableService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class SandeshTableController {

    @Autowired
    private SandeshTableService sandeshTableService;

    @PostMapping("/sandesh-table")
    public ResponseEntity<ApiResponseDTO<SandeshTable>> createBookLanguage(@Validated @RequestBody SandeshTableRequest request) {
        ApiResponseDTO<SandeshTable> response = sandeshTableService.createSandeshTable(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/sandesh-table")
    public ResponseEntity<ApiResponseDTO<List<SandeshTable>>> getAllSandeshTables() {
        ApiResponseDTO<List<SandeshTable>> response = sandeshTableService.getAllSandeshTable();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/sandesh-tables/{id}")
    public ResponseEntity<ApiResponseDTO<SandeshTable>> getSandeshTableById(@PathVariable int id) {
        ApiResponseDTO<SandeshTable> response = sandeshTableService.getSandeshTableById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/sandesh-tables/{id}")
    public ResponseEntity<ApiResponseDTO<SandeshTable>> updateSandeshTable(@PathVariable int id, @Validated @RequestBody SandeshTableRequest request) {
        ApiResponseDTO<SandeshTable> response = sandeshTableService.updateSandeshTable(id, request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/sandesh-tables/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteSandeshTable(@PathVariable int id) {
        ApiResponseDTO<Void> response = sandeshTableService.deleteSandeshTable(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    
}