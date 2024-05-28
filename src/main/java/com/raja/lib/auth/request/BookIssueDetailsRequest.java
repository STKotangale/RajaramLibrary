package com.raja.lib.auth.request;

import lombok.Data;

@Data
public class BookIssueDetailsRequest {
	private String username;
    private String startDate;
    private String endDate;

}
