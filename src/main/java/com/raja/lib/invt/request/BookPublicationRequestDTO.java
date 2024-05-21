package com.raja.lib.invt.request;
import lombok.Data;

@Data
public class BookPublicationRequestDTO {
    
    private String publicationName;
    private String contactPerson;
    private String address;
    private String contactNo1;
    private String contactNo2;
    private String emailId;

}
