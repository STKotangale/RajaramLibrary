package com.raja.lib.invt.resposne;


import java.io.Serializable;
import java.util.List;

import com.raja.lib.acc.model.Ledger;

import lombok.Data;

@Data
public class StockResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;

    private int stockId;
    private String stockType;
    private String invoiceNo;
    private String invoiceDate;
    private double billTotal;
    private double discountPercent;
    private double discountAmount;
    private double totalAfterDiscount;
    private double gstPercent;
    private double gstAmount;
    private double grandTotal;
    private Ledger ledgerIDF;
	private double fineDays;
	private double finePerDays;
	private double fineAmount; 
    private List<StockDetailResponseDTO> stockDetails;
}
