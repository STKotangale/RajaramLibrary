package com.raja.lib.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralMemberResponseDTO {

    private int memberId;
    private String libGenMembNo; 
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
    private String confirmDate;
    private char isBlock;
    private String username;
    private String email;

}
