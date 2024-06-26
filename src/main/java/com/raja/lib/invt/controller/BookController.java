package com.raja.lib.invt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.raja.lib.invt.model.Book;
import com.raja.lib.invt.request.BookRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/book")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<ApiResponseDTO<Book>> createBook(@Valid @RequestBody BookRequest request) {
        ApiResponseDTO<Book> response = bookService.createBook(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @GetMapping("/book") 
    public ResponseEntity<ApiResponseDTO<List<Book>>> getAllBooks() {
        ApiResponseDTO<List<Book>> response = bookService.getAllBooks();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/book/{id}") 
    public ResponseEntity<ApiResponseDTO<Book>> getBookById(@PathVariable int id) {
        ApiResponseDTO<Book> response = bookService.getBookById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/book/{id}") 
    public ResponseEntity<ApiResponseDTO<Book>> updateBook(@PathVariable int id, @Valid @RequestBody BookRequest request) {
        ApiResponseDTO<Book> response = bookService.updateBook(id, request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/book/{id}") 
    public ResponseEntity<ApiResponseDTO<Void>> deleteBook(@PathVariable int id) {
        ApiResponseDTO<Void> response = bookService.deleteBook(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
