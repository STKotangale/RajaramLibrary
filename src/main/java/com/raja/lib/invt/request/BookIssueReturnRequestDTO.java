package com.raja.lib.invt.request;

import lombok.Data;

@Data
public class BookIssueReturnRequestDTO {
	private String issueNo;
	private String issueReturnDate;
	private int memberId;
	private int bookDetailId;
}
