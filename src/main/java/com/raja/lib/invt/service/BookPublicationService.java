package com.raja.lib.invt.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Cacheable(value = "publications")
    public ApiResponseDTO<List<BookPublication>> getAllBookPublications() {
        LOGGER.info("Fetching all book publications");
        List<BookPublication> bookPublications = bookPublicationRepository.findAll();
        LOGGER.debug("Found {} book publications", bookPublications.size());
        return new ApiResponseDTO<>(true, "All book publications retrieved successfully.", bookPublications, 200);
    }

    @CacheEvict(value = "publicationById", key = "#publicationId")
    public ApiResponseDTO<BookPublication> getBookPublicationById(Integer publicationId) {
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

        try {
            BookPublication bookPublication = new BookPublication();
            bookPublication.setPublicationName(requestDTO.getPublicationName());
            bookPublication.setPublicationContactPerson(requestDTO.getContactPerson());
            bookPublication.setPublicationAddress(requestDTO.getAddress());
            bookPublication.setPublicationContactNo1(requestDTO.getContactNo1());
            bookPublication.setPublicationContactNo2(requestDTO.getContactNo2());
            bookPublication.setPublicationEmailId(requestDTO.getEmailId());


            BookPublication savedBookPublication = bookPublicationRepository.save(bookPublication);
            LOGGER.debug("Book publication created with id {}", savedBookPublication.getPublicationId());
            return new ApiResponseDTO<>(true, "Book publication created successfully.", savedBookPublication, 201);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Failed to create book publication due to duplicate publication name");
            return new ApiResponseDTO<>(false, "Failed to create book publication. Publication name already exists.", null, 400);
        }
    }

    @CacheEvict(value = "publications", allEntries = true)
    public ApiResponseDTO<BookPublication> updateBookPublication(int publicationId, BookPublicationRequestDTO requestDTO) {
        LOGGER.info("Updating book publication with id {}", publicationId);
        Optional<BookPublication> optionalBookPublication = bookPublicationRepository.findById(publicationId);
        if (optionalBookPublication.isPresent()) {
            LOGGER.debug("Book publication found with id {}", publicationId);
            BookPublication existingBookPublication = optionalBookPublication.get();

            if (!existingBookPublication.getPublicationName().equals(requestDTO.getPublicationName()) &&
                    bookPublicationRepository.existsByPublicationName(requestDTO.getPublicationName())) {
                return new ApiResponseDTO<>(false, "Error: Publication name already exists.", null, 400);
            }

            existingBookPublication.setPublicationName(requestDTO.getPublicationName());
            existingBookPublication.setPublicationContactPerson(requestDTO.getAddress());
            existingBookPublication.setPublicationAddress(requestDTO.getAddress());
            existingBookPublication.setPublicationContactNo1(requestDTO.getContactNo1());
            existingBookPublication.setPublicationContactNo2(requestDTO.getContactNo2());
            existingBookPublication.setPublicationEmailId(requestDTO.getEmailId());

            BookPublication updatedBookPublication = bookPublicationRepository.save(existingBookPublication);
            LOGGER.debug("Book publication updated with id {}", publicationId);
            return new ApiResponseDTO<>(true, "Book publication updated successfully.", updatedBookPublication, 200);
        } else {
            LOGGER.warn("Book publication not found with id {}", publicationId);
            return new ApiResponseDTO<>(false, "Book publication not found.", null, 404);
        }
    }

    @CacheEvict(value = {"publications", "publicationById"}, allEntries = true)
    public ApiResponseDTO<Void> deleteBookPublication(int publicationId) {
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
	