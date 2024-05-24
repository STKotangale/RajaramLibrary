package com.raja.lib.invt.resposne;

import lombok.Data;

@Data
public class MemberMonthlyFeesResponse {
	private int memberMonthlyId;
	private String memMonInvoiceNo;
	private String memMonInvoiceDate;
	private int memberIdF;
	private Double feesAmount;
	private String fromDate;
	private String toDate;
	private Integer totalMonths;
	private String feesType;
	private int ledgerIDF;
	private String bankName;
	private String chequeNo;
	private String chequeDate;
	private String monthlyDescription;

}
