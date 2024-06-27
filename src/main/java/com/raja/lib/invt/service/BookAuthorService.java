package com.raja.lib.invt.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raja.lib.acc.response.ApiResponseDTO;
import com.raja.lib.auth.service.SessionService;
import com.raja.lib.invt.model.BookAuthor;
import com.raja.lib.invt.repository.BookAuthorRepository;
import com.raja.lib.invt.request.BookAuthorRequestDTO;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class BookAuthorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookAuthorService.class);

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private SessionService sessionService;
    
    public ApiResponseDTO<List<BookAuthor>> getAllBookAuthors() {
        LOGGER.info("Fetching all book authors");

        try {
        } catch (Exception e) {
            LOGGER.error("Session check failed: {}", e.getMessage());
            return new ApiResponseDTO<>(false, e.getMessage(), null, 500); 
        }

        List<BookAuthor> bookAuthors = bookAuthorRepository.findAll();
        LOGGER.debug("Found {} book authors", bookAuthors.size());
        return new ApiResponseDTO<>(true, "All book authors retrieved successfully.", bookAuthors, 200);
    }

    public ApiResponseDTO<BookAuthor> getBookAuthorById(int authorId) {
        LOGGER.info("Fetching book author with id {}", authorId);
        Optional<BookAuthor> optionalBookAuthor = bookAuthorRepository.findById(authorId);
        if (optionalBookAuthor.isPresent()) {
            LOGGER.debug("Book author found with id {}", authorId);
            return new ApiResponseDTO<>(true, "Book author found.", optionalBookAuthor.get(), 200);
        } else {
            LOGGER.warn("Book author not found with id {}", authorId);
            return new ApiResponseDTO<>(false, "Book author not found.", null, 404);
        }
    }

    public ApiResponseDTO<BookAuthor> createBookAuthor(BookAuthorRequestDTO requestDTO) {
        LOGGER.info("Creating book author");
        
        try {
            validateBookAuthorRequestDTO(requestDTO);
        } catch (ConstraintViolationException e) {
            LOGGER.error("Validation failed while creating book author: {}", e.getMessage());
            return new ApiResponseDTO<>(false, "Validation failed: " + e.getMessage(), null, 400);
        }

        if (bookAuthorRepository.existsByAuthorName(requestDTO.getAuthorName())) {
            LOGGER.warn("Book author with name '{}' already exists", requestDTO.getAuthorName());
            return new ApiResponseDTO<>(false, "Book author with name '" + requestDTO.getAuthorName() + "' already exists.", null, 409);
        }

        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setAuthorName(requestDTO.getAuthorName());
        bookAuthor.setAuthorAddress(requestDTO.getAddress());
        bookAuthor.setAuthorContactNo1(requestDTO.getContactNo1());
        bookAuthor.setAuthorContactNo2(requestDTO.getContactNo2());
        bookAuthor.setAuthorEmailId(requestDTO.getEmailId());
        bookAuthor.setIsblock('N');

        BookAuthor savedBookAuthor = bookAuthorRepository.save(bookAuthor);
        LOGGER.debug("Book author created with id {}", savedBookAuthor.getAuthorId());
        return new ApiResponseDTO<>(true, "Book author created successfully.", savedBookAuthor, 201);
    }

    public ApiResponseDTO<BookAuthor> updateBookAuthor(int authorId, BookAuthorRequestDTO requestDTO) {
        LOGGER.info("Updating book author with id {}", authorId);
        Optional<BookAuthor> optionalBookAuthor = bookAuthorRepository.findById(authorId);
        if (optionalBookAuthor.isPresent()) {
            LOGGER.debug("Book author found with id {}", authorId);

            if (bookAuthorRepository.existsByAuthorNameAndAuthorIdNot(requestDTO.getAuthorName(), authorId)) {
                LOGGER.warn("Another book author with name '{}' already exists", requestDTO.getAuthorName());
                return new ApiResponseDTO<>(false, "Another book author with name '" + requestDTO.getAuthorName() + "' already exists.", null, 409);
            }

            try {
                validateBookAuthorRequestDTO(requestDTO);
            } catch (ConstraintViolationException e) {
                LOGGER.error("Validation failed while updating book author: {}", e.getMessage());
                return new ApiResponseDTO<>(false, "Validation failed: " + e.getMessage(), null, 400);
            }

            BookAuthor existingBookAuthor = optionalBookAuthor.get();

            existingBookAuthor.setAuthorName(requestDTO.getAuthorName());
            existingBookAuthor.setAuthorAddress(requestDTO.getAddress());
            existingBookAuthor.setAuthorContactNo1(requestDTO.getContactNo1());
            existingBookAuthor.setAuthorContactNo2(requestDTO.getContactNo2());
            existingBookAuthor.setAuthorEmailId(requestDTO.getEmailId());
            existingBookAuthor.setIsblock('N');

            BookAuthor updatedBookAuthor = bookAuthorRepository.save(existingBookAuthor);
            LOGGER.debug("Book author updated with id {}", authorId);
            return new ApiResponseDTO<>(true, "Book author updated successfully.", updatedBookAuthor, 200);
        } else {
            LOGGER.warn("Book author not found with id {}", authorId);
            return new ApiResponseDTO<>(false, "Book author not found.", null, 404);
        }
    }

    public ApiResponseDTO<Void> deleteBookAuthor(int authorId) {
        LOGGER.info("Deleting book author with id {}", authorId);
        if (bookAuthorRepository.existsById(authorId)) {
            bookAuthorRepository.deleteById(authorId);
            LOGGER.debug("Book author deleted with id {}", authorId);
            return new ApiResponseDTO<>(true, "Book author deleted successfully.", null, 200);
        } else {
            LOGGER.warn("Book author not found with id {}", authorId);
            return new ApiResponseDTO<>(false, "Book author not found.", null, 404);
        }
    }

    private void validateBookAuthorRequestDTO(BookAuthorRequestDTO requestDTO) {
        var violations = validator.validate(requestDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
