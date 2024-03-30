package com.raja.lib.DbService;

import java.util.List;

import com.raja.lib.payload.request.PurchaseRequestDto;
import com.raja.lib.payload.response.GetPurchaseReponseDto;
import com.raja.lib.payload.response.PurchaseResponseDto;

public interface PurchaseDbService {

	public PurchaseResponseDto createPurchase(PurchaseRequestDto requestDto);

	public GetPurchaseReponseDto getPurchaseDetails(Long purchaseId);

	public List<GetPurchaseReponseDto> getAllPurchaseDetails();

}
