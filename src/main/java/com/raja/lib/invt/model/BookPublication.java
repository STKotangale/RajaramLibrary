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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int publicationId;

    @NotBlank
    @Size(max = 100)
    private String publicationName;


    @NotBlank
    @Size(max = 100)
    private String publicationContactPerson;
    

    @NotBlank
    @Size(max = 200)
    private String publicationAddress;
    
    private String publicationContactNo1;
    private String publicationContactNo2;

    @NotBlank
    @Size(max = 100)
    private String publicationEmailId;
    
    @Column(columnDefinition = "char(1) default 'N'")
    private char isblock;

}
