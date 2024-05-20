package com.raja.lib.invt.resposne;

import java.util.List;

import lombok.Data;

@Data
public class StockDTO {
	 private Long stockId;
	    private Long ledgerIDF;
	    private String invoiceNo;
	    private String invoiceDate;
	    private List<StockDetailDTO> details;

}