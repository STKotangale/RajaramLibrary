package com.raja.lib.invt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.model.StockCopyNo;
import com.raja.lib.invt.objects.BookDetail;
import com.raja.lib.invt.objects.BookDetailNameCopyNO;
import com.raja.lib.invt.objects.BookDetailNameWithCopyNO;
import com.raja.lib.invt.repository.BookDetailsRepository;
import com.raja.lib.invt.repository.StockCopyNoRepository;
import com.raja.lib.invt.request.UpdateBookDetailsRequest;
import com.raja.lib.invt.resposne.BookDetailResponse;

@Service
public class BookDetailsService {

    @Autowired
    private BookDetailsRepository bookDetailsRepository;

    @Autowired
    private StockCopyNoRepository stockCopyNoRepository;

    public List<BookDetail> findBooksByNullIsbn() {
        return bookDetailsRepository.findBooksByNullIsbn();
    }

    public List<BookDetailResponse> getBookDetails() {
        List<BookDetailNameCopyNO> bookDetails = bookDetailsRepository.findBooksDetail();
        List<BookDetailResponse> responseList = new ArrayList<>();

        for (BookDetailNameCopyNO bookDetail : bookDetails) {
            BookDetailResponse response = new BookDetailResponse();
            response.setBookId(bookDetail.getBookId());
            response.setBookName(bookDetail.getBookName());

            List<BookDetailResponse.CopyDetail> copyDetails = Stream.of(bookDetail.getPurchaseCopyNos().split(","))
                    .map(copyNo -> {
                        String[] parts = copyNo.split(":");
                        return new BookDetailResponse.CopyDetail(
                                Integer.parseInt(parts[0]),
                                Integer.parseInt(parts[1])
                        );
                    })
                    .collect(Collectors.toList());

            response.setCopyDetails(copyDetails);
            responseList.add(response);
        }

        return responseList;
    }
    
    
    
    public String updateBookDetails(int id, UpdateBookDetailsRequest request) {
        try {
            BookDetails existingBookDetails = bookDetailsRepository.findById(id)
                    .orElseThrow(() -> new Exception("Book details not found with id: " + id));
            existingBookDetails.setIsbn(request.getIsbn());
            existingBookDetails.setClassificationNumber(request.getClassificationNumber());
            existingBookDetails.setItemNumber(request.getItemNumber());
            existingBookDetails.setEditor(request.getEditor());
            existingBookDetails.setTitle(request.getTitle());
            existingBookDetails.setSecondTitle(request.getSecondTitle());
            existingBookDetails.setSeriesTitle(request.getSeriesTitle());
            existingBookDetails.setEdition(request.getEdition());
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
            existingBookDetails.setBookIssue("Y");
            existingBookDetails.setBookWorkingStart("Y");
            existingBookDetails.setBookLost("N");
            existingBookDetails.setBookScrap("N");
            BookDetails updatedBookDetails = bookDetailsRepository.save(existingBookDetails);
            StockCopyNo stockCopyNo = new StockCopyNo();
            stockCopyNo.setStockDetailIdF(existingBookDetails.getStockDetailIdF());
            stockCopyNo.setBookDetailIdF(existingBookDetails);
            stockCopyNo.setStockType("A1");
            stockCopyNoRepository.save(stockCopyNo);

            return "Book details updated successfully.";

        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            return "Error updating book details: " + e.getMessage();
        } catch (Exception e) {
            return "An error occurred while updating book details.";
        }
    }
    
    
    public List<Map<String, Object>> getBookDetailsWithCopyNO() {
        List<BookDetailNameWithCopyNO> bookDetails = bookDetailsRepository.findAllBookDetails();

        Map<String, List<Map<String, Object>>> groupedBooks = bookDetails.stream().collect(
            Collectors.groupingBy(
            		BookDetailNameWithCopyNO::getBookName,
                Collectors.mapping(detail -> {
                    Map<String, Object> bookMap = new HashMap<>();
                    bookMap.put("bookRate", detail.getBookRate());
                    bookMap.put("purchaseCopyNo", detail.getPurchaseCopyNo());
                    bookMap.put("bookDetailId", detail.getBookDetailId());
                    return bookMap;
                }, Collectors.toList())
            )
        );

        List<Map<String, Object>> result = groupedBooks.entrySet().stream().map(entry -> {
            Map<String, Object> bookGroup = new HashMap<>();
            bookGroup.put("bookName", entry.getKey());
            bookGroup.put("details", entry.getValue());
            return bookGroup;
        }).collect(Collectors.toList());

        return result;
    }
}

