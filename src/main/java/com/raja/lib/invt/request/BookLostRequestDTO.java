package com.raja.lib.invt.request;

import java.util.List;
import com.raja.lib.invt.resposne.PurchaseReturnBookDetailDTO;
import lombok.Data;

@Data
public class BookLostRequestDTO {
	
	private String invoiceNO;
	private String invoiceDate;
	private int ledgerId;
	private int billTotal;

	private List<BookLostBookDetailDTO> bookDetails;


}
