package com.raja.lib.invt.resposne;

import lombok.Data;

@Data
public class StockDetailDTO {
	 private Long stockDetailId;
	    private Long bookIdF;
	    private Double bookRate;
	    private Double bookAmount;
	    private Long bookId;
	    private String bookName;
	    private Integer purchaseCopyNo;
}
