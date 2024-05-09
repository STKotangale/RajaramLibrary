package com.raja.lib.invt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.Book;
import com.raja.lib.invt.model.BookAuthor;
import com.raja.lib.invt.model.BookLanguage;
import com.raja.lib.invt.model.BookPublication;
import com.raja.lib.invt.model.BookType;
import com.raja.lib.invt.repository.BookAuthorRepository;
import com.raja.lib.invt.repository.BookLanguageRepository;
import com.raja.lib.invt.repository.BookPublicationRepository;
import com.raja.lib.invt.repository.BookRepository;
import com.raja.lib.invt.repository.BookTypeRepository;
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
    
    @Autowired
    private BookTypeRepository bookTypeRepository;
    
    @Autowired
    private BookLanguageRepository bookLanguageRepository;

    
    public ApiResponseDTO<Book> createBook(BookRequest request) {
        try {
            Optional<BookAuthor> optionalAuthor = authorRepository.findById(request.getAuthorId());
            Optional<BookPublication> optionalPublication = publicationRepository.findById(request.getPublicationId());
            Optional<BookType> optionalBookType = bookTypeRepository.findById(request.getBookTypeId());
            Optional<BookLanguage> optionalBookLanguage = bookLanguageRepository.findById(request.getBookLangId());

            if (optionalAuthor.isPresent() && optionalPublication.isPresent() && optionalBookType.isPresent() && optionalBookLanguage.isPresent()) {
                BookAuthor author = optionalAuthor.get();
                BookPublication publication = optionalPublication.get();
                BookType bookType = optionalBookType.get();
                BookLanguage bookLanguage = optionalBookLanguage.get();

                Book book = new Book();
                book.setBookName(request.getBookName()); // Set the book name
                book.setIsBlock(request.getIsBlock()); // Set the isBlock status
                book.setAuthorIdF(author); // Set the author
                book.setPublicationIdF(publication); // Set the publication
                book.setBookTypeIdF(bookType); // Set the book type
                book.setBookLangIdF(bookLanguage); // S

                Book savedBook = bookRepository.save(book);
                return new ApiResponseDTO<>(true, "Book created successfully", savedBook, HttpStatus.CREATED.value());
            } else {
                return new ApiResponseDTO<>(false, "Author, Publication, Book Type, or Book Language not found", null, HttpStatus.NOT_FOUND.value());
            }
        } catch (DataIntegrityViolationException e) {
            // If the book name already exists, return a response indicating the conflict
            return new ApiResponseDTO<>(false, "Book name already exists", null, HttpStatus.CONFLICT.value());
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return new ApiResponseDTO<>(false, "An error occurred while creating the book", null, HttpStatus.INTERNAL_SERVER_ERROR.value());
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
            Optional<BookType> optionalBookType = bookTypeRepository.findById(request.getBookTypeId());
            Optional<BookLanguage> optionalBookLanguage = bookLanguageRepository.findById(request.getBookLangId());

            if (optionalAuthor.isPresent() && optionalPublication.isPresent() && optionalBookType.isPresent() && optionalBookLanguage.isPresent()) {
                Book existingBook = optionalBook.get();
                existingBook.setBookName(request.getBookName());
                existingBook.setIsBlock(request.getIsBlock());

                existingBook.setAuthorIdF(optionalAuthor.get());
                existingBook.setPublicationIdF(optionalPublication.get());
                existingBook.setBookTypeIdF(optionalBookType.get());
                existingBook.setBookLangIdF(optionalBookLanguage.get());

                Book updatedBook = bookRepository.save(existingBook);
                return new ApiResponseDTO<>(true, "Book updated successfully", updatedBook, HttpStatus.OK.value());
            } else {
                return new ApiResponseDTO<>(false, "Author, Publication, Book Type or Book Language not found", null, HttpStatus.NOT_FOUND.value());
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
