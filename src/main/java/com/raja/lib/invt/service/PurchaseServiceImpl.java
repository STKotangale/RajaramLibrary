package com.raja.lib.invt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.repository.LedgerRepository;
import com.raja.lib.invt.model.Book;
import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.model.Purchase;
import com.raja.lib.invt.model.PurchaseDetail;
import com.raja.lib.invt.repository.BookDetailsRepository;
import com.raja.lib.invt.repository.BookRepository;
import com.raja.lib.invt.repository.PurchaseDetailRepository;
import com.raja.lib.invt.repository.PurchaseRepository;
import com.raja.lib.invt.request.PurchaseDetailDto;
import com.raja.lib.invt.request.PurchaseRequestDto;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.PurchaseDetailResponseDto;
import com.raja.lib.invt.resposne.PurchaseResponseDto;
import com.raja.lib.invt.resposne.PurchaseResponseDtos;

import jakarta.transaction.Transactional;

@Service
public class PurchaseServiceImpl {

	private final PurchaseRepository purchaseRepository;
	private final PurchaseDetailRepository purchaseDetailRepository;
	private final LedgerRepository ledgerRepository;
	private final BookDetailsRepository bookDetailsRepository;
	private final BookRepository bookRepository;

	public PurchaseServiceImpl(PurchaseRepository purchaseRepository, 
	                           PurchaseDetailRepository purchaseDetailRepository,
	                           LedgerRepository ledgerRepository, 
	                           BookDetailsRepository bookDetailsRepository,
	                           BookRepository bookRepository) {
	    this.purchaseRepository = purchaseRepository;
	    this.purchaseDetailRepository = purchaseDetailRepository;
	    this.ledgerRepository = ledgerRepository;
	    this.bookDetailsRepository = bookDetailsRepository;
	    this.bookRepository = bookRepository;
	}

	
	 @Transactional
	    public PurchaseResponseDto createPurchase(PurchaseRequestDto requestDto) {
	        Ledger ledger = ledgerRepository.findById(requestDto.getLedgerId())
	                .orElseThrow(() -> new RuntimeException("Ledger not found with id: " + requestDto.getLedgerId()));

	        Purchase purchase = new Purchase();
	        purchase.setInvoiceNo(requestDto.getInvoiceNo());
	        purchase.setInvoiceDate(requestDto.getInvoiceDate());
	        purchase.setBillTotal(requestDto.getBillTotal());
	        purchase.setDiscountPercent(requestDto.getDiscountPercent());
	        purchase.setDiscountAmount(requestDto.getDiscountAmount());
	        purchase.setTotalAfterDiscount(requestDto.getTotalAfterDiscount());
	        purchase.setGstPercent(requestDto.getGstPercent());
	        purchase.setGstAmount(requestDto.getGstAmount());
	        purchase.setGrandTotal(requestDto.getGrandTotal());
	        purchase.setLedgerIDF(ledger);

	        Integer maxSrno = purchaseDetailRepository.findMaxSrno();
	        int nextSrno = (maxSrno == null ? 0 : maxSrno) + 1;

	        List<PurchaseDetail> purchaseDetails = new ArrayList<>();

	        for (PurchaseDetailDto detailDto : requestDto.getPurchaseDetails()) {
	            Book book = bookRepository.findById(detailDto.getBookId())
	                    .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + detailDto.getBookId()));

	            PurchaseDetail purchaseDetail = new PurchaseDetail();
	            purchaseDetail.setBook(book);
	            purchaseDetail.setBook_qty(detailDto.getQty());
	            purchaseDetail.setBook_rate(detailDto.getRate());
	            purchaseDetail.setBook_amount(detailDto.getAmount());
	            purchaseDetail.setPurchaseIdF(purchase);
	            purchaseDetail.setSrno(nextSrno++);  
	            purchaseDetails.add(purchaseDetail);
	        }

	        purchase = purchaseRepository.save(purchase);
	        purchaseDetailRepository.saveAll(purchaseDetails);

	        List<BookDetails> bookDetailsList = generateBookDetails(purchaseDetails);
	        bookDetailsRepository.saveAll(bookDetailsList);

	        PurchaseResponseDto responseDto = new PurchaseResponseDto();
	        responseDto.setPurchaseId(purchase.getPurchaseId());
	        responseDto.setInvoiceNo(purchase.getInvoiceNo());
	        responseDto.setMessage("Purchase created successfully");
	        responseDto.setStatusCode(HttpStatus.CREATED.value());
	        responseDto.setSuccess(true);

	        return responseDto;
	    }

	 private List<BookDetails> generateBookDetails(List<PurchaseDetail> purchaseDetails) {
		    List<BookDetails> bookDetailsList = new ArrayList<>();
		    Map<Long, Integer> purchaseCopyMap = new HashMap<>();

		    for (PurchaseDetail purchaseDetail : purchaseDetails) {
		        int quantity = purchaseDetail.getBook_qty();
		        Integer purchaseDetailId = purchaseDetail.getPurchaseDetailId();
		        Long purchaseDetailIdLong = purchaseDetailId != null ? purchaseDetailId.longValue() : null;

		        // Check if purchaseDetailId is not null before adding to map
		        if (purchaseDetailId != null) {
		            purchaseCopyMap.putIfAbsent(purchaseDetailId.longValue(), 1);
		        }

		        Book book = purchaseDetail.getBook_idF(); // Corrected field reference
		        for (int i = 0; i < quantity; i++) {
		            BookDetails bookDetails = new BookDetails();
		            bookDetails.setBookIdF(book);
		            bookDetails.setPurchaseDetail(purchaseDetail);

		            Integer purchaseCopyNo = purchaseCopyMap.get(purchaseDetailId); // Changed type to Integer
		            if (purchaseCopyNo == null) {
		                purchaseCopyNo = 1; // Set default value if purchaseCopyNo is null
		            }
		            bookDetails.setPurchaseCopyNo(purchaseCopyNo);

		            bookDetailsList.add(bookDetails);

		            // Increment purchaseCopyNo in the map
		            purchaseCopyMap.put(purchaseDetailId.longValue(), Integer.valueOf(purchaseCopyNo + 1));
		        }
		    }
		    return bookDetailsList;
		}




		
	 public PurchaseResponseDtos getPurchaseById(int purchaseId) {
		    Purchase purchase = purchaseRepository.findById(purchaseId)
		        .orElseThrow(() -> new RuntimeException("Purchase not found with id: " + purchaseId));

		    List<PurchaseDetailResponseDto> detailResponseDtos = purchase.getPurchaseDetails().stream()
		        .map(detail -> new PurchaseDetailResponseDto(
		            detail.getPurchaseDetailId(),
		            detail.getBook_idF().getBookId(),
		            bookRepository.findById(detail.getBook_idF().getBookId())
		                .map(Book::getBookName)
		                .orElse("Book name not available"),
		            detail.getBook_qty(),
		            (int) detail.getBook_rate(), // Adjusted to cast to int
		            (int) detail.getBook_amount() // Adjusted to cast to int
		        ))
		        .collect(Collectors.toList());

		    PurchaseResponseDtos responseDto = new PurchaseResponseDtos(
		        purchase.getPurchaseId(),
		        purchase.getInvoiceNo(),
		        purchase.getInvoiceDate(),
		        purchase.getBillTotal(),
		        purchase.getDiscountPercent(),
		        purchase.getDiscountAmount(),
		        purchase.getTotalAfterDiscount(),
		        purchase.getGstPercent(),
		        purchase.getGstAmount(),
		        purchase.getGrandTotal(),
		        purchase.getLedgerIDF().getLedgerName(),
		        purchase.getLedgerIDF().getLedgerID(),
		        detailResponseDtos
		    );

		    return responseDto;
		}




	 public List<PurchaseResponseDtos> getAllPurchases() {
		    List<Purchase> purchases = purchaseRepository.findAll();
		    return purchases.stream().map(purchase -> {
		        List<PurchaseDetailResponseDto> detailResponseDtos = purchase.getPurchaseDetails().stream()
		        		.map(detail -> new PurchaseDetailResponseDto(
		        			    detail.getPurchaseDetailId(),
		        			    detail.getBook_idF().getBookId(),
		        			    bookRepository.findById(detail.getBook_idF().getBookId())
		        			        .map(Book::getBookName)
		        			        .orElse("Book name not available"),
		        			    detail.getBook_qty(),
		        			    (int) detail.getBook_rate(), // Assuming detail.getRate() returns a double, cast it to int if it's supposed to be an int
		        			    (int) detail.getBook_amount() // Assuming detail.getAmount() returns a double, cast it to int if it's supposed to be an int
		        			))
		        			.collect(Collectors.toList());


		        return new PurchaseResponseDtos(
		            purchase.getPurchaseId(),
		            purchase.getInvoiceNo(),
		            purchase.getInvoiceDate(),
		            purchase.getBillTotal(),
		            purchase.getDiscountPercent(),
		            purchase.getDiscountAmount(),
		            purchase.getTotalAfterDiscount(),
		            purchase.getGstPercent(),
		            purchase.getGstAmount(),
		            purchase.getGrandTotal(),
		            purchase.getLedgerIDF().getLedgerName(),
		            purchase.getLedgerIDF().getLedgerID(),
		            detailResponseDtos
		        );
		    }).collect(Collectors.toList());
		}



	 @Transactional
	 public PurchaseResponseDto updatePurchase(int purchaseId, PurchaseRequestDto requestDto) {
	     Purchase purchase = purchaseRepository.findById(purchaseId)
	             .orElseThrow(() -> new RuntimeException("Purchase not found with id: " + purchaseId));

	     // Update purchase attributes
	     purchase.setInvoiceNo(requestDto.getInvoiceNo());
	     purchase.setInvoiceDate(requestDto.getInvoiceDate());
	     purchase.setBillTotal(requestDto.getBillTotal());
	     purchase.setDiscountPercent(requestDto.getDiscountPercent());
	     purchase.setDiscountAmount(requestDto.getDiscountAmount());
	     purchase.setTotalAfterDiscount(requestDto.getTotalAfterDiscount());
	     purchase.setGstPercent(requestDto.getGstPercent());
	     purchase.setGstAmount(requestDto.getGstAmount());
	     purchase.setGrandTotal(requestDto.getGrandTotal());

	     List<PurchaseDetail> newDetails = new ArrayList<>();
	     List<BookDetails> newBookDetailsList = new ArrayList<>();
	     Integer maxSrno = purchaseDetailRepository.findMaxSrno();
	     int nextSrno = (maxSrno == null ? 0 : maxSrno) + 1;

	     for (PurchaseDetailDto detailDto : requestDto.getPurchaseDetails()) {
	         PurchaseDetail newDetail = new PurchaseDetail();
	         newDetail.setPurchaseIdF(purchase);
	         Book book = bookRepository.findById(detailDto.getBookId())
	                                   .orElseThrow(() -> new RuntimeException("Book not found with id: " + detailDto.getBookId()));
	         newDetail.setBook(book);
	         newDetail.setBook_qty(detailDto.getQty());
	         newDetail.setBook_rate(detailDto.getRate());
	         newDetail.setBook_amount(detailDto.getAmount());
	         newDetail.setSrno(nextSrno++); 

	         newDetails.add(newDetail);

	         for (int i = 0; i < detailDto.getQty(); i++) {
	             BookDetails bookDetail = new BookDetails();
	             bookDetail.setPurchaseDetail(newDetail);
	             bookDetail.setPurchaseCopyNo(i + 1);
	             bookDetail.setBookIdF(book);
	             newBookDetailsList.add(bookDetail);
	         }
	     }

	     // Clear existing purchase details and associated book details
	     purchase.getPurchaseDetails().clear();
	     purchaseDetailRepository.deleteByPurchase(purchase);

	     // Add new purchase details and associated book details
	     purchase.getPurchaseDetails().addAll(newDetails);
	     purchaseDetailRepository.saveAll(newDetails);
	     bookDetailsRepository.saveAll(newBookDetailsList);

	     // Save updated purchase
	     purchase = purchaseRepository.save(purchase);

	     PurchaseResponseDto responseDto = new PurchaseResponseDto();
	     responseDto.setPurchaseId(purchase.getPurchaseId());
	     responseDto.setInvoiceNo(purchase.getInvoiceNo());
	     responseDto.setMessage("Purchase updated successfully");
	     responseDto.setStatusCode(HttpStatus.OK.value());
	     responseDto.setSuccess(true);

	     return responseDto;
	 }

		 
	public ApiResponseDTO<Object> deletePurchase(int purchaseId) {
	    Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseId);
	    if (optionalPurchase.isPresent()) {
	        Purchase purchase = optionalPurchase.get();
	        purchaseDetailRepository.deleteAll(purchase.getPurchaseDetails());
	        purchaseRepository.delete(purchase);
	        return new ApiResponseDTO<>(true, "Purchase deleted successfully", null, HttpStatus.OK.value());
	    } else {
	        throw new RuntimeException("Purchase not found with id: " + purchaseId);
	    }
	}
	



}
