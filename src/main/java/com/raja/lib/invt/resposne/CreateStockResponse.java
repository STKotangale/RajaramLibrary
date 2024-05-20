package com.raja.lib.invt.resposne;

import lombok.Data;

@Data
public class CreateStockResponse {
	
	private Integer stockId;
    private Integer ledgerId;
    private String invoiceNo;
    private String invoiceDate;
    private Object details; 

}
