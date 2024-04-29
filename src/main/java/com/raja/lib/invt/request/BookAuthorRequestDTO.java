package com.raja.lib.invt.request;

import lombok.Data;

@Data
public class BookAuthorRequestDTO {

    private String authorName;
    private String address;
    private String contactNo1;
    private String contactNo2;
    private String emailId;
    private char isblock;

}
