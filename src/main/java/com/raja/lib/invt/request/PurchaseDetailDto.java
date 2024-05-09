package com.raja.lib.invt.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PurchaseDetailDto {
	
	 private int bookId;
	    private int qty;
	    private int rate;
	    private int amount;
}