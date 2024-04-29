package com.raja.lib.invt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class BookAuthor {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    private String authorName;
    private String address;
    private String contactNo1;
    private String contactNo2;
    private String emailId;
    private char isblock;

}
