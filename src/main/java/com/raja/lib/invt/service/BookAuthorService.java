package com.raja.lib.invt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.raja.lib.invt.model.BookAuthor;
import com.raja.lib.invt.repository.BookAuthorRepository;
import com.raja.lib.invt.request.BookAuthorRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@Service
public class BookAuthorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookAuthorService.class);

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    public ApiResponseDTO<List<BookAuthor>> getAllBookAuthors() {
        LOGGER.info("Fetching all book authors");
        List<BookAuthor> bookAuthors = bookAuthorRepository.findAll();
        LOGGER.debug("Found {} book authors", bookAuthors.size());
        return new ApiResponseDTO<>(true, "All book authors retrieved successfully.", bookAuthors, 200);
    }

    public ApiResponseDTO<BookAuthor> getBookAuthorById(Long authorId) {
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
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setAuthorName(requestDTO.getAuthorName());
        bookAuthor.setAddress(requestDTO.getAddress());
        bookAuthor.setContactNo1(requestDTO.getContactNo1());
        bookAuthor.setContactNo2(requestDTO.getContactNo2());
        bookAuthor.setEmailId(requestDTO.getEmailId());

        BookAuthor savedBookAuthor = bookAuthorRepository.save(bookAuthor);
        LOGGER.debug("Book author created with id {}", savedBookAuthor.getAuthorId());
        return new ApiResponseDTO<>(true, "Book author created successfully.", savedBookAuthor, 201);
    }

    public ApiResponseDTO<BookAuthor> updateBookAuthor(Long authorId, BookAuthorRequestDTO requestDTO) {
        LOGGER.info("Updating book author with id {}", authorId);
        Optional<BookAuthor> optionalBookAuthor = bookAuthorRepository.findById(authorId);
        if (optionalBookAuthor.isPresent()) {
            LOGGER.debug("Book author found with id {}", authorId);
            BookAuthor existingBookAuthor = optionalBookAuthor.get();
            existingBookAuthor.setAuthorName(requestDTO.getAuthorName());
            existingBookAuthor.setAddress(requestDTO.getAddress());
            existingBookAuthor.setContactNo1(requestDTO.getContactNo1());
            existingBookAuthor.setContactNo2(requestDTO.getContactNo2());
            existingBookAuthor.setEmailId(requestDTO.getEmailId());

            BookAuthor updatedBookAuthor = bookAuthorRepository.save(existingBookAuthor);
            LOGGER.debug("Book author updated with id {}", authorId);
            return new ApiResponseDTO<>(true, "Book author updated successfully.", updatedBookAuthor, 200);
        } else {
            LOGGER.warn("Book author not found with id {}", authorId);
            return new ApiResponseDTO<>(false, "Book author not found.", null, 404);
        }
    }

    public ApiResponseDTO<Void> deleteBookAuthor(Long authorId) {
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
}

