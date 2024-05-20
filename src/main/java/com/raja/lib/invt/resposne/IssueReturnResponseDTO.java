package com.raja.lib.invt.resposne;

import lombok.Data;

@Data
public class IssueReturnResponseDTO {
    private String issueNo;
    private String issueReturnDate;
    private int memberId;
    private int bookDetailId;
}
