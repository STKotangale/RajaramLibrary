package com.raja.lib.invt.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BookPublication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publicationId;

    private String publicationName;
    private String contactPerson;
    private String address;
    private String contactNo1;
    private String contactNo2;
    private String emailId;
    private char isblock;

}
