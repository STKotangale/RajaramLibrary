package com.raja.lib.auth.request;

import lombok.Data;

@Data
public class BookIssueDetailsRequest {
	private int userId;
    private String startDate;
    private String endDate;

}
