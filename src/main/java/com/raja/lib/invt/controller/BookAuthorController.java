package com.raja.lib.invt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.raja.lib.acc.response.ApiResponseDTO;
import com.raja.lib.invt.model.BookAuthor;
import com.raja.lib.invt.request.BookAuthorRequestDTO;
import com.raja.lib.invt.service.BookAuthorService;

@RestController
@RequestMapping("/api/book-authors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookAuthorController {

    @Autowired
    private BookAuthorService bookAuthorService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<BookAuthor>>> getAllBookAuthors() {
        ApiResponseDTO<List<BookAuthor>> response = bookAuthorService.getAllBookAuthors();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<ApiResponseDTO<BookAuthor>> getBookAuthorById(@PathVariable int authorId) {
        ApiResponseDTO<BookAuthor> response = bookAuthorService.getBookAuthorById(authorId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<BookAuthor>> createBookAuthor(@RequestBody BookAuthorRequestDTO requestDTO) {
        ApiResponseDTO<BookAuthor> response = bookAuthorService.createBookAuthor(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<ApiResponseDTO<BookAuthor>> updateBookAuthor(@PathVariable int authorId, @RequestBody BookAuthorRequestDTO requestDTO) {
        ApiResponseDTO<BookAuthor> response = bookAuthorService.updateBookAuthor(authorId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBookAuthor(@PathVariable int authorId) {
        ApiResponseDTO<Void> response = bookAuthorService.deleteBookAuthor(authorId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
