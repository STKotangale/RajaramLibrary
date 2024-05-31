package com.raja.lib.invt.resposne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembOnlineBookingResponseDTO {
	private int membOnlineId;
    private String invoiceNo;
    private String invoiceDate;
    private int memberIdF;
    private int bookIdF;
}
