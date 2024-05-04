package com.raja.lib.invt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "general_members")
public class GeneralMember{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private int memberId;

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
    @Column(columnDefinition = "char(1) default 'N'")
    private char isBlock;
}
