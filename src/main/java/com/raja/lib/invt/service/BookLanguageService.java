package com.raja.lib.invt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.BookLanguage;
import com.raja.lib.invt.repository.BookLanguageRepository;
import com.raja.lib.invt.request.BookLanguageRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@Service
public class BookLanguageService {

    @Autowired
    private BookLanguageRepository bookLanguageRepository;

    public ApiResponseDTO<BookLanguage> createBookLanguage(BookLanguageRequest request) {
        try {
            BookLanguage bookLanguage = new BookLanguage();
            bookLanguage.setBookLangName(request.getBookLangName());
            bookLanguage.setIsBlock(request.getIsBlock());
            BookLanguage savedBookLanguage = bookLanguageRepository.save(bookLanguage);
            return new ApiResponseDTO<>(true, "Book language created successfully", savedBookLanguage, HttpStatus.CREATED.value());
        } catch (DataIntegrityViolationException e) {
            return new ApiResponseDTO<>(false, "Failed to create book language. Language name already exists.", null, HttpStatus.BAD_REQUEST.value());
        }
    }

    public ApiResponseDTO<List<BookLanguage>> getAllBookLanguages() {
        List<BookLanguage> bookLanguages = bookLanguageRepository.findAll();
        return new ApiResponseDTO<>(true, "List of book languages", bookLanguages, HttpStatus.OK.value());
    }

    public ApiResponseDTO<BookLanguage> getBookLanguageById(int id) {
        Optional<BookLanguage> optionalBookLanguage = bookLanguageRepository.findById(id);
        if (optionalBookLanguage.isPresent()) {
            return new ApiResponseDTO<>(true, "Book language found", optionalBookLanguage.get(), HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Book language not found", null, HttpStatus.NOT_FOUND.value());
        }
    }

    public ApiResponseDTO<BookLanguage> updateBookLanguage(int id, BookLanguageRequest request) {
        try {
            Optional<BookLanguage> optionalBookLanguage = bookLanguageRepository.findById(id);
            if (optionalBookLanguage.isPresent()) {
                BookLanguage existingBookLanguage = optionalBookLanguage.get();
                existingBookLanguage.setBookLangName(request.getBookLangName());
                existingBookLanguage.setIsBlock(request.getIsBlock());
                BookLanguage updatedBookLanguage = bookLanguageRepository.save(existingBookLanguage);
                return new ApiResponseDTO<>(true, "Book language updated successfully", updatedBookLanguage, HttpStatus.OK.value());
            } else {
                return new ApiResponseDTO<>(false, "Book language not found", null, HttpStatus.NOT_FOUND.value());
            }
        } catch (DataIntegrityViolationException e) {
            return new ApiResponseDTO<>(false, "Failed to update book language. Language name already exists.", null, HttpStatus.BAD_REQUEST.value());
        }
    }


    public ApiResponseDTO<Void> deleteBookLanguage(int id) {
        Optional<BookLanguage> optionalBookLanguage = bookLanguageRepository.findById(id);
        if (optionalBookLanguage.isPresent()) {
            bookLanguageRepository.deleteById(id);
            return new ApiResponseDTO<>(true, "Book language deleted successfully", null, HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Book language not found", null, HttpStatus.NOT_FOUND.value());
        }
    }
}
