package com.raja.lib.invt.request;

import java.util.List;

import com.raja.lib.invt.resposne.PurchaseReturnBookDetailDTO;

import lombok.Data;

@Data
public class PurchaseReturnRequestDTO {
	private String invoiceNO;
	private String invoiceDate;
	private int memberId; 
	private int ledgerId;
	private double billTotal;
	private double grandTotal;
	private double discountPercent;
	private double discountAmount;
	private double gstPercent;
	private double gstAmount;
	private double totalAfterDiscount;
	private List<PurchaseReturnBookDetailDTO> bookDetails;
}
