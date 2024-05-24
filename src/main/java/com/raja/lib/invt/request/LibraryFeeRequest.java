package com.raja.lib.invt.request;

import lombok.Data;

@Data
public class LibraryFeeRequest {

	private String feesName;
	private int feesAmount;
	private String isBlock;
}
