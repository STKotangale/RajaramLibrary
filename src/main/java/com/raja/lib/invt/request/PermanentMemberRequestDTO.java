package com.raja.lib.invt.request;

import lombok.Data;

@Data
public class PermanentMemberRequestDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String registerDate;
    private String adharCard;
    private String memberAddress;
    private String dateOfBirth;
    private String memberEducation;
    private String memberOccupation;
    private long mobileNo;
    private String memberEmailId;
    private String confirmDate;
    private char isBlock;
}

