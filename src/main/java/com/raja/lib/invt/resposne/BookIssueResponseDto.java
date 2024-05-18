package com.raja.lib.invt.resposne;

import java.util.List;

import lombok.Data;

@Data
public class BookIssueResponseDto {
    private String invoiceNo;
    private String invoiceDate;
    private String memberName;
    private List<String> bookNames;
    private List<String> purchaseCopyNos;

}
