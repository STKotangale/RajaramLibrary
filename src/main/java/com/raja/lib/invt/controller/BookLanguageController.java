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

import com.raja.lib.invt.model.BookLanguage;
import com.raja.lib.invt.request.BookLanguageRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.service.BookLanguageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class BookLanguageController {

    @Autowired
    private BookLanguageService bookLanguageService;

    @PostMapping("/book-languages")
    public ResponseEntity<ApiResponseDTO<BookLanguage>> createBookLanguage(@Validated @RequestBody BookLanguageRequest request) {
        ApiResponseDTO<BookLanguage> response = bookLanguageService.createBookLanguage(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/book-languages")
    public ResponseEntity<ApiResponseDTO<List<BookLanguage>>> getAllBookLanguages() {
        ApiResponseDTO<List<BookLanguage>> response = bookLanguageService.getAllBookLanguages();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/book-languages/{id}")
    public ResponseEntity<ApiResponseDTO<BookLanguage>> getBookLanguageById(@PathVariable int id) {
        ApiResponseDTO<BookLanguage> response = bookLanguageService.getBookLanguageById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/book-languages/{id}")
    public ResponseEntity<ApiResponseDTO<BookLanguage>> updateBookLanguage(@PathVariable int id, @Validated @RequestBody BookLanguageRequest request) {
        ApiResponseDTO<BookLanguage> response = bookLanguageService.updateBookLanguage(id, request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/book-languages/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBookLanguage(@PathVariable int id) {
        ApiResponseDTO<Void> response = bookLanguageService.deleteBookLanguage(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
