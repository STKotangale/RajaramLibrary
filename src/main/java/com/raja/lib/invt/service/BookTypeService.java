package com.raja.lib.invt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.BookType;
import com.raja.lib.invt.repository.BookTypeRepository;
import com.raja.lib.invt.request.BookTypeRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@Service
public class BookTypeService {

    @Autowired
    private BookTypeRepository bookTypeRepository;

    @Caching(evict = {
        @CacheEvict(value = "bookTypes", allEntries = true),
        @CacheEvict(value = "bookTypeById", key = "#result.data.bookTypeId")
    })
    public ApiResponseDTO<BookType> createBookType(BookTypeRequest request) {
        try {
            BookType bookType = new BookType();
            bookType.setBookTypeName(request.getBookTypeName());
            bookType.setIsBlock(request.getIsBlock());
            BookType savedBookType = bookTypeRepository.save(bookType);
            return new ApiResponseDTO<>(true, "Book type created successfully", savedBookType, HttpStatus.CREATED.value());
        } catch (DataIntegrityViolationException e) {
            return new ApiResponseDTO<>(false, "Failed to create book type. Type name already exists.", null, HttpStatus.BAD_REQUEST.value());
        }
    }

    @Cacheable(value = "bookTypes", unless = "#result == null || #result.data == null || #result.data.isEmpty()")
    public ApiResponseDTO<List<BookType>> getAllBookTypes() {
        List<BookType> bookTypes = bookTypeRepository.findAll();
        return new ApiResponseDTO<>(true, "List of book types", bookTypes, HttpStatus.OK.value());
    }

    @Cacheable(value = "bookTypeById", key = "#id", unless = "#result == null || #result.data == null")
    public ApiResponseDTO<BookType> getBookTypeById(int id) {
        Optional<BookType> optionalBookType = bookTypeRepository.findById(id);
        if (optionalBookType.isPresent()) {
            return new ApiResponseDTO<>(true, "Book type found", optionalBookType.get(), HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Book type not found", null, HttpStatus.NOT_FOUND.value());
        }
    }

    @Caching(put = {
        @CachePut(value = "bookTypeById", key = "#id")
    }, evict = {
        @CacheEvict(value = "bookTypes", allEntries = true)
    })
    public ApiResponseDTO<BookType> updateBookType(int id, BookTypeRequest request) {
        Optional<BookType> optionalBookType = bookTypeRepository.findById(id);
        if (optionalBookType.isPresent()) {
            BookType existingBookType = optionalBookType.get();
            existingBookType.setBookTypeName(request.getBookTypeName());
            existingBookType.setIsBlock(request.getIsBlock());
            BookType updatedBookType = bookTypeRepository.save(existingBookType);
            return new ApiResponseDTO<>(true, "Book type updated successfully", updatedBookType, HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Book type not found", null, HttpStatus.NOT_FOUND.value());
        }
    }

    @Caching(evict = {
        @CacheEvict(value = "bookTypes", allEntries = true),
        @CacheEvict(value = "bookTypeById", key = "#id")
    })
    public ApiResponseDTO<Void> deleteBookType(int id) {
        Optional<BookType> optionalBookType = bookTypeRepository.findById(id);
        if (optionalBookType.isPresent()) {
            bookTypeRepository.deleteById(id);
            return new ApiResponseDTO<>(true, "Book type deleted successfully", null, HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Book type not found", null, HttpStatus.NOT_FOUND.value());
        }
    }
}
