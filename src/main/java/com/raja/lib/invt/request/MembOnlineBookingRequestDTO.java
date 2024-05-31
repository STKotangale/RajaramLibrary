package com.raja.lib.invt.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembOnlineBookingRequestDTO {
    private String invoiceNo;
    private String invoiceDate;
    private int memberIdF;
    private int bookIdF;
}
