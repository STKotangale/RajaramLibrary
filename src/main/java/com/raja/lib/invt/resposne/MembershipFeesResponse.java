package com.raja.lib.invt.resposne;

import lombok.Data;

@Data
public class MembershipFeesResponse {
    private Long membershipId;
    private String memInvoiceNo;
    private String memInvoiceDate;
    private int memberIdF;
    private Double bookDepositFees;
    private Double entryFees;
    private Double securityDepositFees;
    private String feesType;
    private String bankName;
    private String chequeNo;
    private String chequeDate;
    private String membershipDescription;

}
