package com.raja.lib.invt.request;

import java.util.List;

import com.raja.lib.invt.resposne.PurchaseReturnBookDetailDTO;

import lombok.Data;

@Data
public class PurchaseReturnRequestDTO {
	private String invoiceNO;
	private String invoiceDate;
	private int ledgerId;
	private int billTotal;
	private int grandTotal;
	private int discount;
	private int totalAfterDiscount;
	private List<PurchaseReturnBookDetailDTO> bookDetails;
}
