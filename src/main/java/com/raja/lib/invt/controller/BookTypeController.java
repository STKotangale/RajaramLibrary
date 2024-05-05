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

import com.raja.lib.invt.model.BookType;
import com.raja.lib.invt.request.BookTypeRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.service.BookTypeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class BookTypeController {

    @Autowired
    private BookTypeService bookTypeService;

    @PostMapping("/book-types")
    public ResponseEntity<ApiResponseDTO<BookType>> createBookType(@Validated @RequestBody BookTypeRequest request) {
        ApiResponseDTO<BookType> response = bookTypeService.createBookType(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/book-types")
    public ResponseEntity<ApiResponseDTO<List<BookType>>> getAllBookTypes() {
        ApiResponseDTO<List<BookType>> response = bookTypeService.getAllBookTypes();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/book-types/{id}")
    public ResponseEntity<ApiResponseDTO<BookType>> getBookTypeById(@PathVariable Long id) {
        ApiResponseDTO<BookType> response = bookTypeService.getBookTypeById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/book-types/{id}")
    public ResponseEntity<ApiResponseDTO<BookType>> updateBookType(@PathVariable Long id, @Validated @RequestBody BookTypeRequest request) {
        ApiResponseDTO<BookType> response = bookTypeService.updateBookType(id, request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/book-types/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBookType(@PathVariable Long id) {
        ApiResponseDTO<Void> response = bookTypeService.deleteBookType(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
