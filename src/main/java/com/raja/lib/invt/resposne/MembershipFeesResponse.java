package com.raja.lib.invt.resposne;

import java.util.List;
import lombok.Data;

@Data
public class MembershipFeesResponse {
    private int membershipId;
    private String memInvoiceNo;
    private String memInvoiceDate;
    private int memberIdF;
    private String firstName;
    private String middleName;
    private String lastName;
    private String feesType;
    private Double fess_total;
    private String bankName;
    private String chequeNo;
    private String chequeDate;
    private String membershipDescription;
    private List<MembershipFeesDetailResponse> membershipFeesDetails;
}
