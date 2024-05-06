package com.raja.lib.auth.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
@Entity
@Table(name = "auth_permanent_members")
public class PermanentMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int permanentMemberId;
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

    @Column(columnDefinition = "char(1) default 'N'")
    private char isBlock;
}
