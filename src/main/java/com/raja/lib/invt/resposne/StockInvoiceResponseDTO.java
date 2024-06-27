package com.raja.lib.invt.resposne;

import java.util.List;

import lombok.Data;

@Data
public class StockInvoiceResponseDTO {
	
	 private int stockId;
	    private double billTotal;
	    private double gstAmount;
	    private int invoiceNo;
	    private double grandTotal;
	    private double gstPercent;
	    private String ledgerName;
	    private String invoiceDate;
	    private double discountAmount;
	    private double discountPercent;
	    private double totalAfterDiscount;
	    private List<StockDetailDTOs> stockDetails;


}
