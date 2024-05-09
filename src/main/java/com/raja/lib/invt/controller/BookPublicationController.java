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

import com.raja.lib.invt.model.BookPublication;
import com.raja.lib.invt.repository.BookPublicationRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.service.BookPublicationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/book-publications")
public class BookPublicationController {

    @Autowired
    private BookPublicationService bookPublicationService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<BookPublication>>> getAllBookPublications() {
        return ResponseEntity.ok(bookPublicationService.getAllBookPublications());
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<ApiResponseDTO<BookPublication>> getBookPublicationById(@PathVariable int publicationId) {
        return ResponseEntity.ok(bookPublicationService.getBookPublicationById(publicationId));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<BookPublication>> createBookPublication(@RequestBody BookPublicationRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookPublicationService.createBookPublication(requestDTO));
    }

    @PutMapping("/{publicationId}")
    public ResponseEntity<ApiResponseDTO<BookPublication>> updateBookPublication(@PathVariable int publicationId, @RequestBody BookPublicationRequestDTO requestDTO) {
        return ResponseEntity.ok(bookPublicationService.updateBookPublication(publicationId, requestDTO));
    }

    @DeleteMapping("/{publicationId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBookPublication(@PathVariable int publicationId) {
        return ResponseEntity.ok(bookPublicationService.deleteBookPublication(publicationId));
    }
}
