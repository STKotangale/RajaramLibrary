package com.raja.lib.invt.model;


import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "invt_book_publication")
public class BookPublication implements Serializable {
	
	private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="publicationId")
    private int publicationId;

    @NotBlank
    @Size(max = 100)
    @Column(name="publicationName")
    private String publicationName;
    
    @NotBlank
    @Size(max = 100)
    @Column(name="publicationContactPerson")
    private String publicationContactPerson;
    
    @NotBlank
    @Size(max = 200)
    @Column(name="publicationAddress")
    private String publicationAddress;
    
    @Column(name="publicationContactNo1")
    private String publicationContactNo1;
    
    @Column(name="publicationContactNo2")
    private String publicationContactNo2;
    
    @NotBlank
    @Size(max = 100)
    @Column(name="publicationEmailId")
    private String publicationEmailId;
    
    @Column(name = "isblock", columnDefinition = "char(1) default 'N'")
    private char isblock;

}
