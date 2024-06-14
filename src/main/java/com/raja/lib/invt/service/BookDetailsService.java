package com.raja.lib.invt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
                        if (parts.length >= 2) {
                            String[] copyNoParts = parts[1].split("-");
                            if (copyNoParts.length >= 2) {
                                return new BookDetailResponse.CopyDetail(
                                        Integer.parseInt(parts[0]),
                                        copyNoParts[0], // accessionNo
                                        Integer.parseInt(copyNoParts[1])
                                );
                            }
                        }
                        return null;
                    })
                    .filter(copyDetail -> copyDetail != null)
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
            existingBookDetails.setBookIssue("Y");
            existingBookDetails.setBookWorkingStart("Y");
            existingBookDetails.setBookLost("N");
            existingBookDetails.setBookScrap("N");
            existingBookDetails.setBook_return("N");
            BookDetails updatedBookDetails = bookDetailsRepository.save(existingBookDetails);

            Optional<StockCopyNo> existingStockCopyNo = stockCopyNoRepository.findByBookDetailIdF(existingBookDetails);
            if (existingStockCopyNo.isPresent()) {
                StockCopyNo stockCopyNo = existingStockCopyNo.get();
                stockCopyNo.setStockDetailIdF(existingBookDetails.getStockDetailIdF());
                stockCopyNo.setStockType("A1");
                stockCopyNoRepository.save(stockCopyNo);
            } else {
                StockCopyNo stockCopyNo = new StockCopyNo();
                stockCopyNo.setStockDetailIdF(existingBookDetails.getStockDetailIdF());
                stockCopyNo.setBookDetailIdF(existingBookDetails);
                stockCopyNo.setStockType("A1");
                stockCopyNoRepository.save(stockCopyNo);
            }

            return "Book details updated successfully.";

        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            return "Error updating book details: " + e.getMessage();
        } catch (Exception e) {
            return "An error occurred while updating book details.";
        }
    }
    
    public Map<String, Object> getBookDetailsByBookName(String bookName) {
        List<BookDetailNameWithCopyNO> bookDetails = bookDetailsRepository.findBookDetailsByBookName(bookName);

        List<Map<String, Object>> details = bookDetails.stream().map(detail -> {
            Map<String, Object> bookMap = new HashMap<>();
            bookMap.put("bookRate", detail.getBookRate());
            bookMap.put("bookName", detail.getBookName());
            bookMap.put("purchaseCopyNo", detail.getPurchaseCopyNo());
            bookMap.put("accessionNo", detail.getaccessionNo()); // Include accessionNo
            bookMap.put("bookDetailId", detail.getBookDetailId());
            return bookMap;
        }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("bookName", bookName);
        result.put("details", details);

        return result;
    }


    public String updateBookIssueStatus(List<Integer> bookDetailIds) {
        try {
            for (Integer id : bookDetailIds) {
                Optional<BookDetails> optionalBookDetails = bookDetailsRepository.findById(id);
                if (optionalBookDetails.isPresent()) {
                    BookDetails bookDetails = optionalBookDetails.get();
                    bookDetails.setBookIssue("Y");
                    bookDetailsRepository.save(bookDetails);
                } else {
                    throw new Exception("Book details not found with id: " + id);
                }
            }
            return "Book issue status updated successfully.";
        } catch (Exception e) {
            return "An error occurred while updating book issue status: " + e.getMessage();
        }
    }
    
    
    public String updateIssuereturnStatus(List<Integer> bookDetailIds) {
        try {
            for (Integer id : bookDetailIds) {
                Optional<BookDetails> optionalBookDetails = bookDetailsRepository.findById(id);
                if (optionalBookDetails.isPresent()) {
                    BookDetails bookDetails = optionalBookDetails.get();
                    bookDetails.setBookIssue("N");
                    bookDetailsRepository.save(bookDetails);
                } else {
                    throw new Exception("Book details not found with id: " + id);
                }
            }
            return "Book issue status updated successfully.";
        } catch (Exception e) {
            return "An error occurred while updating book issue status: " + e.getMessage();
        }
    }
    
    
    public String updatePurchasereturnStatus(List<Integer> bookDetailIds) {
        try {
            for (Integer id : bookDetailIds) {
                Optional<BookDetails> optionalBookDetails = bookDetailsRepository.findById(id);
                if (optionalBookDetails.isPresent()) {
                    BookDetails bookDetails = optionalBookDetails.get();
                    bookDetails.setBook_return("N");
                    bookDetailsRepository.save(bookDetails);
                } else {
                    throw new Exception("Book details not found with id: " + id);
                }
            }
            return "Book issue status updated successfully.";
        } catch (Exception e) {
            return "An error occurred while updating book issue status: " + e.getMessage();
        }
    }
    
    
    public String updateBookLostreturnStatus(List<Integer> bookDetailIds) {
        try {
            for (Integer id : bookDetailIds) {
                Optional<BookDetails> optionalBookDetails = bookDetailsRepository.findById(id);
                if (optionalBookDetails.isPresent()) {
                    BookDetails bookDetails = optionalBookDetails.get();
                    bookDetails.setBookLost("N");
                    bookDetailsRepository.save(bookDetails);
                } else {
                    throw new Exception("Book details not found with id: " + id);
                }
            }
            return "Book issue status updated successfully.";
        } catch (Exception e) {
            return "An error occurred while updating book issue status: " + e.getMessage();
        }
    }
    
    
    public String updateBookScraptreturnStatus(List<Integer> bookDetailIds) {
        try {
            for (Integer id : bookDetailIds) {
                Optional<BookDetails> optionalBookDetails = bookDetailsRepository.findById(id);
                if (optionalBookDetails.isPresent()) {
                    BookDetails bookDetails = optionalBookDetails.get();
                    bookDetails.setBookScrap("N");
                    bookDetailsRepository.save(bookDetails);
                } else {
                    throw new Exception("Book details not found with id: " + id);
                }
            }
            return "Book issue status updated successfully.";
        } catch (Exception e) {
            return "An error occurred while updating book issue status: " + e.getMessage();
        }
    }
}

