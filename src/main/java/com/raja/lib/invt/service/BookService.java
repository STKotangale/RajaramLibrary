package com.raja.lib.invt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.Book;
import com.raja.lib.invt.model.BookAuthor;
import com.raja.lib.invt.model.BookPublication;
import com.raja.lib.invt.repository.BookAuthorRepository;
import com.raja.lib.invt.repository.BookPublicationRepository;
import com.raja.lib.invt.repository.BookRepository;
import com.raja.lib.invt.request.BookRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookAuthorRepository authorRepository;
    
    @Autowired
    private BookPublicationRepository publicationRepository;

    
    public ApiResponseDTO<Book> createBook(BookRequest request, Long authorId, Long publicationId) {
        Optional<BookAuthor> optionalAuthor = authorRepository.findById(authorId);
        Optional<BookPublication> optionalPublication = publicationRepository.findById(publicationId);

        if (optionalAuthor.isPresent() && optionalPublication.isPresent()) {
            BookAuthor author = optionalAuthor.get();
            BookPublication publication = optionalPublication.get();

            Book book = new Book(request.getBookName(), request.getIsBlock(), author, publication);
            Book savedBook = bookRepository.save(book);
            return new ApiResponseDTO<>(true, "Book created successfully", savedBook, HttpStatus.CREATED.value());
        } else {
            return new ApiResponseDTO<>(false, "Author or Publication not found", null, HttpStatus.NOT_FOUND.value());
        }
    }

    public ApiResponseDTO<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return new ApiResponseDTO<>(true, "List of books", books, HttpStatus.OK.value());
    }

    

    public ApiResponseDTO<Book> getBookById(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            return new ApiResponseDTO<>(true, "Book found", optionalBook.get(), HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Book not found", null, HttpStatus.NOT_FOUND.value());
        }
    }

    

    public ApiResponseDTO<Book> updateBook(int id, BookRequest request) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Optional<BookAuthor> optionalAuthor = authorRepository.findById(request.getAuthorId());
            Optional<BookPublication> optionalPublication = publicationRepository.findById(request.getPublicationId());
            
            if (optionalAuthor.isPresent() && optionalPublication.isPresent()) {
                Book existingBook = optionalBook.get();
                existingBook.setBookName(request.getBookName());
                existingBook.setIsBlock(request.getIsBlock());
                
                existingBook.setAuthor(optionalAuthor.get());
                existingBook.setPublication(optionalPublication.get());
                
                Book updatedBook = bookRepository.save(existingBook);
                return new ApiResponseDTO<>(true, "Book updated successfully", updatedBook, HttpStatus.OK.value());
            } else {
                return new ApiResponseDTO<>(false, "Author or Publication not found", null, HttpStatus.NOT_FOUND.value());
            }
        } else {
            return new ApiResponseDTO<>(false, "Book not found", null, HttpStatus.NOT_FOUND.value());
        }
    }

    
    public ApiResponseDTO<Void> deleteBook(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            return new ApiResponseDTO<>(true, "Book deleted successfully", null, HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Book not found", null, HttpStatus.NOT_FOUND.value());
        }
    }
}
