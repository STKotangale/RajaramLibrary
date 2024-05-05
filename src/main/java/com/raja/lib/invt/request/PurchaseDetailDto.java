package com.raja.lib.invt.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PurchaseDetailDto {
	
	 private Long bookId;
	    private int qty;
	    private int rate;
	    private int amount;
}