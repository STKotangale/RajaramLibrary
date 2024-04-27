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
import com.raja.lib.invt.objects.BookName;
import com.raja.lib.invt.objects.BookRate;
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
	        purchase.setLedger(ledger);

	        // Fetch the maximum srno from the PurchaseDetail records
	        Integer maxSrno = purchaseDetailRepository.findMaxSrno();
	        int nextSrno = (maxSrno == null ? 0 : maxSrno) + 1;

	        List<PurchaseDetail> purchaseDetails = new ArrayList<>();

	        for (PurchaseDetailDto detailDto : requestDto.getPurchaseDetails()) {
	            Book book = bookRepository.findById(detailDto.getBookId())
	                    .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + detailDto.getBookId()));

	            PurchaseDetail purchaseDetail = new PurchaseDetail();
	            purchaseDetail.setBook_idf(book);
	            purchaseDetail.setQty(detailDto.getQty());
	            purchaseDetail.setRate(detailDto.getRate());
	            purchaseDetail.setAmount(detailDto.getAmount());
	            purchaseDetail.setPurchase(purchase);
	            purchaseDetail.setSrno(nextSrno++);  // Increment srno for each detail
	            purchaseDetails.add(purchaseDetail);
	        }

	        // Save purchase and related entities
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
	        int quantity = purchaseDetail.getQty();
	        int rate = purchaseDetail.getRate();
	        Long purchaseDetailId = purchaseDetail.getPurchaseDetail();
	        purchaseCopyMap.putIfAbsent(purchaseDetailId, 1);

	        for (int i = 0; i < quantity; i++) {
	            BookDetails bookDetails = new BookDetails();
	            bookDetails.setPurchaseDetail(purchaseDetail);
	            bookDetails.setPurchaseDetailIdf(purchaseDetail);
	            bookDetails.setRate(rate);

	            int purchaseCopyNo = purchaseCopyMap.get(purchaseDetailId);
	            bookDetails.setPurchaseCopyNo(purchaseCopyNo);

	            bookDetailsList.add(bookDetails);

	            purchaseCopyMap.put(purchaseDetailId, purchaseCopyNo + 1);
	        }
	    }
	    return bookDetailsList;
	}


	
	//
	
	public PurchaseResponseDtos getPurchaseById(Long purchaseId) {
	    Purchase purchase = purchaseRepository.findById(purchaseId)
	        .orElseThrow(() -> new RuntimeException("Purchase not found with id: " + purchaseId));

	    List<PurchaseDetailResponseDto> detailResponseDtos = purchase.getPurchaseDetails().stream()
	    	    .map(detail -> new PurchaseDetailResponseDto(
	    	        detail.getPurchaseDetail(),  // Corrected to use the getter that exists
	    	        detail.getBook_idf().getBookId(),  // Ensure Book entity has getBookId()
	    	        bookRepository.findById(detail.getBook_idf().getBookId())
	    	            .map(Book::getBookName)
	    	            .orElse("Book name not available"),  // Handling potentially missing book names
	    	        detail.getQty(),
	    	        detail.getRate(),
	    	        detail.getAmount()
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
	    	    purchase.getLedger().getLedgerName(),  
	    	    purchase.getLedger().getLedgerID(),          
	    	    detailResponseDtos
	    	);

	    return responseDto;
	}



	public List<PurchaseResponseDtos> getAllPurchases() {
	    List<Purchase> purchases = purchaseRepository.findAll();
	    return purchases.stream().map(purchase -> {
	        List<PurchaseDetailResponseDto> detailResponseDtos = purchase.getPurchaseDetails().stream()
	            .map(detail -> new PurchaseDetailResponseDto(
	                detail.getPurchaseDetail(),  // Make sure this getter is correctly named
	                detail.getBook_idf().getBookId(),
	                bookRepository.findById(detail.getBook_idf().getBookId())
	                    .map(Book::getBookName)
	                    .orElse("Book name not available"),
	                detail.getQty(),
	                detail.getRate(),
	                detail.getAmount()
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
	            purchase.getLedger().getLedgerName(),
	            purchase.getLedger().getLedgerID(),
	            detailResponseDtos
	        );
	    }).collect(Collectors.toList());
	}



	

	
	

	  
	
	
	
	
	// Upadte Api
	public PurchaseResponseDto updatePurchase(Long purchaseId, PurchaseRequestDto requestDto) {
	    Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseId);
	    if (optionalPurchase.isPresent()) {
	        Purchase purchase = optionalPurchase.get();
	        purchase.setInvoiceNo(requestDto.getInvoiceNo());
	        purchase.setInvoiceDate(requestDto.getInvoiceDate());
	        purchase.setBillTotal(requestDto.getBillTotal());
	        purchase.setDiscountPercent(requestDto.getDiscountPercent());
	        purchase.setDiscountAmount(requestDto.getDiscountAmount());
	        purchase.setTotalAfterDiscount(requestDto.getTotalAfterDiscount());
	        purchase.setGstPercent(requestDto.getGstPercent());
	        purchase.setGstAmount(requestDto.getGstAmount());
	        purchase.setGrandTotal(requestDto.getGrandTotal());
	        
	        Ledger ledger = ledgerRepository.findById(requestDto.getLedgerId())
	                .orElseThrow(() -> new RuntimeException("Ledger not found with id: " + requestDto.getLedgerId()));
	        purchase.setLedger(ledger);

	        // Collect IDs of existing purchase details that are not in the updated list
	        List<Long> existingDetailIds = purchase.getPurchaseDetails().stream()
	                .map(PurchaseDetail::getPurchaseDetail)
	                .collect(Collectors.toList());

	        // Clear existing purchase details
	        purchase.getPurchaseDetails().clear();

	        // Add new purchase details
	        List<PurchaseDetailDto> requestPurchaseDetails = requestDto.getPurchaseDetails();
	        for (PurchaseDetailDto detailDto : requestPurchaseDetails) {
	            PurchaseDetail purchaseDetail = new PurchaseDetail();
	            purchaseDetail.setBook_idf(bookRepository.findById(detailDto.getBookId()).orElse(null));
	            purchaseDetail.setQty(detailDto.getQty());
	            purchaseDetail.setRate(detailDto.getRate());
	            purchaseDetail.setAmount(detailDto.getAmount());
	            purchaseDetail.setPurchase(purchase);
	            purchase.getPurchaseDetails().add(purchaseDetail);
	        }

	        // Delete old purchase details that are not in the updated list
	        for (Long detailId : existingDetailIds) {
	            purchaseDetailRepository.deleteById(detailId);
	        }

	        purchase = purchaseRepository.save(purchase);

	        PurchaseResponseDto responseDto = new PurchaseResponseDto();
	        responseDto.setPurchaseId(purchase.getPurchaseId());
	        responseDto.setInvoiceNo(purchase.getInvoiceNo());
	        responseDto.setMessage("Purchase updated successfully");
	        responseDto.setStatusCode(HttpStatus.OK.value());
	        responseDto.setSuccess(true);
	        return responseDto;
	    } else {
	        throw new RuntimeException("Purchase not found with id: " + purchaseId);
	    }
	}





	public ApiResponseDTO<Object> deletePurchase(Long purchaseId) {
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


	public BookRate getBookRate(String bookName) {
		BookRate bookRate = purchaseDetailRepository.getBookRate(bookName);
		if (bookRate == null) {
			return null;
		}
		return bookRate;
	}

	public List<BookName> getBookNames() {
		List<BookName> bookNames = purchaseDetailRepository.getBookName();
		return bookNames;
	}

}
