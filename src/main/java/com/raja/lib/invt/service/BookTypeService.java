package com.raja.lib.invt.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

    
    public ApiResponseDTO<BookType> createBookType(BookTypeRequest request) {
        BookType bookType = new BookType(request.getBookTypeName(), request.getIsBlock());
        BookType savedBookType = bookTypeRepository.save(bookType);
        return new ApiResponseDTO<>(true, "Book type created successfully", savedBookType, HttpStatus.CREATED.value());
    }

    
    public ApiResponseDTO<List<BookType>> getAllBookTypes() {
        List<BookType> bookTypes = bookTypeRepository.findAll();
        return new ApiResponseDTO<>(true, "List of book types", bookTypes, HttpStatus.OK.value());
    }

    
    public ApiResponseDTO<BookType> getBookTypeById(int id) {
        Optional<BookType> optionalBookType = bookTypeRepository.findById(id);
        if (optionalBookType.isPresent()) {
            return new ApiResponseDTO<>(true, "Book type found", optionalBookType.get(), HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Book type not found", null, HttpStatus.NOT_FOUND.value());
        }
    }

    
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
