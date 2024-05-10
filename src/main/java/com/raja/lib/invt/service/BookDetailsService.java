package com.raja.lib.invt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.objects.BookDetail;
import com.raja.lib.invt.repository.BookDetailsRepository;
import com.raja.lib.invt.request.UpdateBookDetailsRequest;



@Service
public class BookDetailsService {

    @Autowired
    private BookDetailsRepository bookDetailsRepository;
    
    
    public List<BookDetail> findBooksByNullIsbn(){
    	return bookDetailsRepository.findBooksByNullIsbn();
    }
    
  
    
    public String updateBookDetails(Long id, UpdateBookDetailsRequest request) {
        try {
            BookDetails existingBookDetails = bookDetailsRepository.findById(id)
                    .orElseThrow(() -> new Exception("Book details not found with id: " + id));

            if (request.getIsbn() == null || request.getIsbn().trim().isEmpty()) {
                return "ISBN is compulsory.";
            }

            existingBookDetails.setIsbn(request.getIsbn());
//            existingBookDetails.setLanguage(request.getLanguage());
            existingBookDetails.setClassificationNumber(request.getClassificationNumber());
            existingBookDetails.setItemNumber(request.getItemNumber());
//            existingBookDetails.setAuthor(request.getAuthor());
            existingBookDetails.setEditor(request.getEditor());
            existingBookDetails.setTitle(request.getTitle());
            existingBookDetails.setSecondTitle(request.getSecondTitle());
            existingBookDetails.setSeriesTitle(request.getSeriesTitle());
            existingBookDetails.setEdition(request.getEdition());
//            existingBookDetails.setPlaceOfPublication(request.getPlaceOfPublication());
//            existingBookDetails.setNameOfPublisher(request.getNameOfPublisher());
            existingBookDetails.setPublicationYear(request.getPublicationYear());
            existingBookDetails.setNumberOfPages(request.getNumberOfPages());
            existingBookDetails.setSubjectHeading(request.getSubjectHeading());
            existingBookDetails.setSecondAuthorEditor(request.getSecondAuthorEditor());
            existingBookDetails.setThirdAuthorEditor(request.getThirdAuthorEditor());
            existingBookDetails.setItemType(request.getItemType());
            existingBookDetails.setPermanentLocation(request.getPermanentLocation());
            existingBookDetails.setCurrentLocation(request.getCurrentLocation());
            existingBookDetails.setShelvingLocation(request.getShelvingLocation());
            existingBookDetails.setVolumeNo(request.getVolumeNo());
            existingBookDetails.setFullCallNumber(request.getFullCallNumber());
            existingBookDetails.setCopyNo(request.getCopyNo());
            existingBookDetails.setAccessionNo(request.getAccessionNo());
            existingBookDetails.setTypeofbook(request.getTypeofbook());

            BookDetails updatedBookDetails = bookDetailsRepository.save(existingBookDetails);

            return "Book details updated successfully.";

        } catch (Exception e) {
            return "Error updating book details: " + e.getMessage();
        }
    }

}

