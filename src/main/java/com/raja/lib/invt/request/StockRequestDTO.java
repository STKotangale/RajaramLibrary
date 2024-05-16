package com.raja.lib.invt.request;


import lombok.Data;

import java.util.List;

@Data
public class StockRequestDTO {
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
    private int ledgerIDF;
    private int generalMemberId; // New field to store General Member ID
    private List<StockDetailRequestDTO> stockDetails;
}
