package com.raja.lib.invt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.model.Ledger;
import com.raja.lib.invt.model.Purchase;
import com.raja.lib.invt.model.PurchaseDetail;
import com.raja.lib.invt.objects.BookName;
import com.raja.lib.invt.objects.BookRate;
import com.raja.lib.invt.repository.BookDetailsRepository;
import com.raja.lib.invt.repository.LedgerRepository;
import com.raja.lib.invt.repository.PurchaseDetailRepository;
import com.raja.lib.invt.repository.PurchaseRepository;
import com.raja.lib.invt.request.PurchaseDetailDto;
import com.raja.lib.invt.request.PurchaseRequestDto;
import com.raja.lib.invt.resposne.GetPurchaseReponseDto;
import com.raja.lib.invt.resposne.PurchaseResponseDto;

@Service
public class PurchaseServiceImpl  {

	private final PurchaseRepository purchaseRepository;
	private final PurchaseDetailRepository purchaseDetailRepository;
	private final LedgerRepository ledgerRepository;
	private final BookDetailsRepository bookDetailsRepository;

	public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
	                           PurchaseDetailRepository purchaseDetailRepository,
	                           LedgerRepository ledgerRepository,
	                           BookDetailsRepository bookDetailsRepository) {
	    this.purchaseRepository = purchaseRepository;
	    this.purchaseDetailRepository = purchaseDetailRepository;
	    this.ledgerRepository = ledgerRepository;
	    this.bookDetailsRepository = bookDetailsRepository;
	}


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
		purchase = purchaseRepository.save(purchase);

		List<PurchaseDetail> purchaseDetails = new ArrayList<>();

		for (PurchaseDetailDto detailDto : requestDto.getPurchaseDetails()) {
			PurchaseDetail purchaseDetail = new PurchaseDetail();
			purchaseDetail.setBookName(detailDto.getBookName());
			purchaseDetail.setQty(detailDto.getQty());
			purchaseDetail.setRate(detailDto.getRate());
			purchaseDetail.setAmount(detailDto.getAmount());
			purchaseDetail.setPurchase(purchase);
			purchaseDetails.add(purchaseDetail);
		}
		purchaseDetailRepository.saveAll(purchaseDetails);
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
		bookDetailsRepository.saveAll(bookDetailsList);

		PurchaseResponseDto responseDto = new PurchaseResponseDto();
		responseDto.setPurchaseId(purchase.getPurchaseId());
		responseDto.setInvoiceNo(purchase.getInvoiceNo());
		responseDto.setMessage("Purchase created successfully");
		responseDto.setStatusCode(HttpStatus.CREATED.value());
		responseDto.setSuccess(true);

		return responseDto;
	}

	public GetPurchaseReponseDto getPurchaseDetails(Long purchaseId) {
		Optional<Purchase> optionalPurchase = purchaseRepository.findById(purchaseId);
		if (optionalPurchase.isPresent()) {
			Purchase purchase = optionalPurchase.get();
			GetPurchaseReponseDto responseDto = mapToResponseDto(purchase);
			return responseDto;
		} else {
			return null;
		}
	}

	public List<GetPurchaseReponseDto> getAllPurchaseDetails() {
		List<Purchase> purchases = purchaseRepository.findAll();
		return purchases.stream().map(this::mapToResponseDto).collect(Collectors.toList());
	}

	private GetPurchaseReponseDto mapToResponseDto(Purchase purchase) {
		GetPurchaseReponseDto responseDto = new GetPurchaseReponseDto();
		responseDto.setPurchaseId(purchase.getPurchaseId());
		responseDto.setInvoiceNo(purchase.getInvoiceNo());
		responseDto.setInvoiceDate(purchase.getInvoiceDate());
		responseDto.setBillTotal(purchase.getBillTotal());
		responseDto.setDiscountPercent(purchase.getDiscountPercent());
		responseDto.setDiscountAmount(purchase.getDiscountAmount());
		responseDto.setTotalAfterDiscount(purchase.getTotalAfterDiscount());
		responseDto.setGstPercent(purchase.getGstPercent());
		responseDto.setGstAmount(purchase.getGstAmount());
		responseDto.setGrandTotal(purchase.getGrandTotal());

		List<PurchaseDetailDto> purchaseDetailDtos = purchase.getPurchaseDetails().stream().map(this::mapToDetailDto)
				.collect(Collectors.toList());
		responseDto.setPurchaseDetails(purchaseDetailDtos);

		return responseDto;
	}

	private PurchaseDetailDto mapToDetailDto(PurchaseDetail purchaseDetail) {
		PurchaseDetailDto detailDto = new PurchaseDetailDto();
		detailDto.setBookName(purchaseDetail.getBookName());
		detailDto.setQty(purchaseDetail.getQty());
		detailDto.setRate(purchaseDetail.getRate());
		detailDto.setAmount(purchaseDetail.getAmount());
		return detailDto;
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
