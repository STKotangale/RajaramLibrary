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

import com.raja.lib.invt.model.BookAuthor;
import com.raja.lib.invt.request.BookAuthorRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.service.BookAuthorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/book-authors")
public class BookAuthorController {

    @Autowired
    private BookAuthorService bookAuthorService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<BookAuthor>>> getAllBookAuthors() {
        return ResponseEntity.ok(bookAuthorService.getAllBookAuthors());
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<ApiResponseDTO<BookAuthor>> getBookAuthorById(@PathVariable Long authorId) {
        return ResponseEntity.ok(bookAuthorService.getBookAuthorById(authorId));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<BookAuthor>> createBookAuthor(@RequestBody BookAuthorRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookAuthorService.createBookAuthor(requestDTO));
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<ApiResponseDTO<BookAuthor>> updateBookAuthor(@PathVariable Long authorId, @RequestBody BookAuthorRequestDTO requestDTO) {
        return ResponseEntity.ok(bookAuthorService.updateBookAuthor(authorId, requestDTO));
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBookAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(bookAuthorService.deleteBookAuthor(authorId));
    }
}
