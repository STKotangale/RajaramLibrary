package com.raja.lib.invt.request;

import lombok.Data;

@Data
public class BookIssueRequestDto {
    private String invoiceNo;
    private String invoiceDate;
    private int generalMemberId;
    private int bookIdF;
    private int bookdetail;
}
