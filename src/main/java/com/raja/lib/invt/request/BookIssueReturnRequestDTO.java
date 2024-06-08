package com.raja.lib.invt.request;

import lombok.Data;
import java.util.List;

@Data
public class BookIssueReturnRequestDTO {
    private String issueNo;
    private String issueReturnDate;
    private int memberId;
    private List<BookDetailsDTO> bookDetailsList; 
}
