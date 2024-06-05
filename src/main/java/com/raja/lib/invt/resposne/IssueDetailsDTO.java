package com.raja.lib.invt.resposne;

import java.util.List;

import lombok.Data;

@Data
public class IssueDetailsDTO {
    private Integer id;
    private String InvoiceNo;
    private String InvoiceDate;
    private String user;
    private List<BookDetailss> books;
}
