package com.raja.lib.invt.request;

import java.util.List;
import lombok.Data;

@Data
public class BookLostRequestDTO {
	
	private String invoiceNO;
	private String invoiceDate;
	private int ledgerId;
	private double billTotal;
	private double discountPercent; // Add discount percentage
	private double discountAmount; // Add discount amount
	private double totalAfterDiscount; // Add total after discount
	private double grandTotal; // Add grand total
	private List<BookLostBookDetailDTO> bookDetails;
}
