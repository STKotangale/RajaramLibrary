package com.raja.lib.security.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.DbService.PurchaseDbService;
import com.raja.lib.models.Purchase;
import com.raja.lib.models.PurchaseDetail;
import com.raja.lib.payload.request.PurchaseDetailDto;
import com.raja.lib.payload.request.PurchaseRequestDto;
import com.raja.lib.payload.response.GetPurchaseReponseDto;
import com.raja.lib.payload.response.PurchaseResponseDto;
import com.raja.lib.repository.PurchaseDetailRepository;
import com.raja.lib.repository.PurchaseRepository;

@Service
public class PurchaseServiceImpl implements PurchaseDbService {

	private final PurchaseRepository purchaseRepository;
	private final PurchaseDetailRepository purchaseDetailRepository;

	@Autowired
	public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
			PurchaseDetailRepository purchaseDetailRepository) {
		this.purchaseRepository = purchaseRepository;
		this.purchaseDetailRepository = purchaseDetailRepository;
	}

	public PurchaseResponseDto createPurchase(PurchaseRequestDto requestDto) {
		Purchase purchase = new Purchase();
		purchase.setInvoiceNo(requestDto.getInvoiceNo());
		purchase.setInvoiceDate(requestDto.getInvoiceDate());
		purchase.setPartyName(requestDto.getPartyName());
		purchase.setBillTotal(requestDto.getBillTotal());
		purchase.setDiscountPercent(requestDto.getDiscountPercent());
		purchase.setDiscountAmount(requestDto.getDiscountAmount());
		purchase.setTotalAfterDiscount(requestDto.getTotalAfterDiscount());
		purchase.setGstPercent(requestDto.getGstPercent());
		purchase.setGstAmount(requestDto.getGstAmount());
		purchase.setGrandTotal(requestDto.getGrandTotal());

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
		responseDto.setPartyName(purchase.getPartyName());
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
}
