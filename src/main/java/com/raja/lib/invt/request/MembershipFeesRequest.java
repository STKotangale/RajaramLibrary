package com.raja.lib.invt.request;

import java.util.List;
import lombok.Data;

@Data
public class MembershipFeesRequest {
	private String memInvoiceNo;
	private String memInvoiceDate;
	private int memberIdF;
	private String feesType;
	private Double fess_total;
	private String bankName;
	private String chequeNo;
	private String chequeDate;
	private String membershipDescription;
	private List<MembershipFeesDetailRequest> membershipFeesDetails;
}
