package com.raja.lib.invt.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.BookAuthor;
import com.raja.lib.invt.repository.BookAuthorRepository;
import com.raja.lib.invt.request.BookAuthorRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;

import jakarta.annotation.Resource;

@Service
public class BookAuthorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookAuthorService.class);

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;

//    @Cacheable(value = "allBookAuthors")
    public ApiResponseDTO<List<BookAuthor>> getAllBookAuthors() {
        LOGGER.info("Fetching all book authors");
        List<BookAuthor> bookAuthors = bookAuthorRepository.findAll();
        LOGGER.debug("Found {} book authors", bookAuthors.size());
        return new ApiResponseDTO<>(true, "All book authors retrieved successfully.", bookAuthors, 200);
    }

//    @Cacheable(value = "authorById", key = "#authorId")
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

//    @CacheEvict(value = {"allBookAuthors", "authorById"}, allEntries = true)
    public ApiResponseDTO<BookAuthor> createBookAuthor(BookAuthorRequestDTO requestDTO) {
        LOGGER.info("Creating book author");
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setAuthorName(requestDTO.getAuthorName());
        bookAuthor.setAuthorAddress(requestDTO.getAddress());
        bookAuthor.setAuthorContactNo1(requestDTO.getContactNo1());
        bookAuthor.setAuthorContactNo2(requestDTO.getContactNo2());
        bookAuthor.setAuthorEmailId(requestDTO.getEmailId());

        BookAuthor savedBookAuthor = bookAuthorRepository.save(bookAuthor);
        LOGGER.debug("Book author created with id {}", savedBookAuthor.getAuthorId());
        return new ApiResponseDTO<>(true, "Book author created successfully.", savedBookAuthor, 201);
    }

//    @CacheEvict(value = {"allBookAuthors", "authorById"}, allEntries = true)
    public ApiResponseDTO<BookAuthor> updateBookAuthor(int authorId, BookAuthorRequestDTO requestDTO) {
        LOGGER.info("Updating book author with id {}", authorId);
        Optional<BookAuthor> optionalBookAuthor = bookAuthorRepository.findById(authorId);
        if (optionalBookAuthor.isPresent()) {
            LOGGER.debug("Book author found with id {}", authorId);
            BookAuthor existingBookAuthor = optionalBookAuthor.get();

            existingBookAuthor.setAuthorName(requestDTO.getAuthorName());
            existingBookAuthor.setAuthorAddress(requestDTO.getAddress());
            existingBookAuthor.setAuthorContactNo1(requestDTO.getContactNo1());
            existingBookAuthor.setAuthorContactNo2(requestDTO.getContactNo2());
            existingBookAuthor.setAuthorEmailId(requestDTO.getEmailId());

            BookAuthor updatedBookAuthor = bookAuthorRepository.save(existingBookAuthor);
            LOGGER.debug("Book author updated with id {}", authorId);
            return new ApiResponseDTO<>(true, "Book author updated successfully.", updatedBookAuthor, 200);
        } else {
            LOGGER.warn("Book author not found with id {}", authorId);
            return new ApiResponseDTO<>(false, "Book author not found.", null, 404);
        }
    }

//    @CacheEvict(value = {"allBookAuthors", "authorById"}, allEntries = true)
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
}
