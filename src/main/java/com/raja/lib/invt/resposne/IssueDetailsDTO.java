package com.raja.lib.invt.resposne;

import java.util.List;
import lombok.Data;

@Data
public class IssueDetailsDTO {
    private Integer id;
    private String invoiceNo;
    private String invoiceDate;
    private String user;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<BookDetailss> books;
}
	