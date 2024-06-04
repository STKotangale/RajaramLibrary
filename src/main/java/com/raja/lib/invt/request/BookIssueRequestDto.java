package com.raja.lib.invt.request;

import java.util.List;

import lombok.Data;

@Data
public class BookIssueRequestDto {
    private String invoiceNo;
    private String invoiceDate;
    private int generalMemberId;
    private int qty;
    private List<BookDetailDto> bookDetails;
}
