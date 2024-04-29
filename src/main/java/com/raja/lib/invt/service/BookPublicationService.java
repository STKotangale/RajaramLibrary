package com.raja.lib.invt.service;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.BookPublication;
import com.raja.lib.invt.repository.BookPublicationRepository;
import com.raja.lib.invt.repository.BookPublicationRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@Service
public class BookPublicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookPublicationService.class);

    @Autowired
    private BookPublicationRepository bookPublicationRepository;

    public ApiResponseDTO<List<BookPublication>> getAllBookPublications() {
        LOGGER.info("Fetching all book publications");
        List<BookPublication> bookPublications = bookPublicationRepository.findAll();
        LOGGER.debug("Found {} book publications", bookPublications.size());
        return new ApiResponseDTO<>(true, "All book publications retrieved successfully.", bookPublications, 200);
    }

    public ApiResponseDTO<BookPublication> getBookPublicationById(Long publicationId) {
        LOGGER.info("Fetching book publication with id {}", publicationId);
        Optional<BookPublication> optionalBookPublication = bookPublicationRepository.findById(publicationId);
        if (optionalBookPublication.isPresent()) {
            LOGGER.debug("Book publication found with id {}", publicationId);
            return new ApiResponseDTO<>(true, "Book publication found.", optionalBookPublication.get(), 200);
        } else {
            LOGGER.warn("Book publication not found with id {}", publicationId);
            return new ApiResponseDTO<>(false, "Book publication not found.", null, 404);
        }
    }

    public ApiResponseDTO<BookPublication> createBookPublication(BookPublicationRequestDTO requestDTO) {
        LOGGER.info("Creating book publication");
        BookPublication bookPublication = new BookPublication();
        bookPublication.setPublicationName(requestDTO.getPublicationName());
        bookPublication.setContactPerson(requestDTO.getContactPerson());
        bookPublication.setAddress(requestDTO.getAddress());
        bookPublication.setContactNo1(requestDTO.getContactNo1());
        bookPublication.setContactNo2(requestDTO.getContactNo2());
        bookPublication.setEmailId(requestDTO.getEmailId());

        BookPublication savedBookPublication = bookPublicationRepository.save(bookPublication);
        LOGGER.debug("Book publication created with id {}", savedBookPublication.getPublicationId());
        return new ApiResponseDTO<>(true, "Book publication created successfully.", savedBookPublication, 201);
    }

    public ApiResponseDTO<BookPublication> updateBookPublication(Long publicationId, BookPublicationRequestDTO requestDTO) {
        LOGGER.info("Updating book publication with id {}", publicationId);
        Optional<BookPublication> optionalBookPublication = bookPublicationRepository.findById(publicationId);
        if (optionalBookPublication.isPresent()) {
            LOGGER.debug("Book publication found with id {}", publicationId);
            BookPublication existingBookPublication = optionalBookPublication.get();
            existingBookPublication.setPublicationName(requestDTO.getPublicationName());
            existingBookPublication.setContactPerson(requestDTO.getContactPerson());
            existingBookPublication.setAddress(requestDTO.getAddress());
            existingBookPublication.setContactNo1(requestDTO.getContactNo1());
            existingBookPublication.setContactNo2(requestDTO.getContactNo2());
            existingBookPublication.setEmailId(requestDTO.getEmailId());

            BookPublication updatedBookPublication = bookPublicationRepository.save(existingBookPublication);
            LOGGER.debug("Book publication updated with id {}", publicationId);
            return new ApiResponseDTO<>(true, "Book publication updated successfully.", updatedBookPublication, 200);
        } else {
            LOGGER.warn("Book publication not found with id {}", publicationId);
            return new ApiResponseDTO<>(false, "Book publication not found.", null, 404);
        }
    }

    public ApiResponseDTO<Void> deleteBookPublication(Long publicationId) {
        LOGGER.info("Deleting book publication with id {}", publicationId);
        if (bookPublicationRepository.existsById(publicationId)) {
            bookPublicationRepository.deleteById(publicationId);
            LOGGER.debug("Book publication deleted with id {}", publicationId);
            return new ApiResponseDTO<>(true, "Book publication deleted successfully.", null, 200);
        } else {
            LOGGER.warn("Book publication not found with id {}", publicationId);
            return new ApiResponseDTO<>(false, "Book publication not found.", null, 404);
        }
    }
}
