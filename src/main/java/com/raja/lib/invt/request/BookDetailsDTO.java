package com.raja.lib.invt.request;
import lombok.Data;

@Data
public class BookDetailsDTO {
    private int bookDetailIds;
    private int bookId;
	private int stockDetailId;
	private String issuedate;
	private double fineDays;
	private double fineAmount;		
	private double finePerDays; 
}