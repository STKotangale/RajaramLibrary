package com.raja.lib.auth.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "auth_permanent_members")
public class PermanentMember implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="permanentMemberId")
    private int memberId;

    @Column(name="firstName")
    private String firstName;
    
    @Column(name="middleName")
    private String middleName;
    
    @Column(name="lastName")
    private String lastName;
    
    @Column(name="registerDate")
    private String registerDate;
    
    @Column(name="adharCard")
    private String adharCard;
    
    @Column(name="memberAddress")
    private String memberAddress;
    
    @Column(name="dateOfBirth")
    private String dateOfBirth;
    
    @Column(name="memberEducation")
    private String memberEducation;
    
    @Column(name="memberOccupation")
    private String memberOccupation;
    
    @Column(name="mobileNo")
    private long mobileNo;
    
    @Column(name="confirmDate")
    private String confirmDate;
    
	@Column(name = "isBlock", columnDefinition = "char(1) default 'N'")
    private char isBlock;
}