package com.raja.lib.invt.resposne;


import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDtos {
    private Long purchaseId;
    private String invoiceNo;
    private String invoiceDate;
    private int billTotal;
    private int discountPercent;
    private int discountAmount;
    private int totalAfterDiscount;
    private int gstPercent;
    private int gstAmount;
    private int grandTotal;
    private String ledgerName; 
    private int ledgerId;  
    private List<PurchaseDetailResponseDto> purchaseDetails;
    
   
}
