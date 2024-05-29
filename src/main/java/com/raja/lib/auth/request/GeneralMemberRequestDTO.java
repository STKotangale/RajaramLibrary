package com.raja.lib.auth.request;

import lombok.Data;

@Data
public class GeneralMemberRequestDTO {
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
    private String password;
    private String username;

}
