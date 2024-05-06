package com.raja.lib.invt.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="invt_book_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookDetailId;
	private int purchaseDetailIdF;
    private int bookIdF;
    
    @NotBlank
    @Size(max = 25)
    private String isbn;
    private String classificationNumber;
    private String itemNumber;
    private String editor;
    private String title;
    private String secondTitle;
    private String seriesTitle;
    private int edition;
    private int publicationYear;
    private int numberOfPages;
    private String subjectHeading;
    private String secondAuthorEditor;
    private String thirdAuthorEditor;
    private String itemType;
    private String permanentLocation;
    private String currentLocation;
    private String shelvingLocation;
    private int volumeNo;
    private String fullCallNumber;
    private String copyNo;
    private String accessionNo;
    private String typeofbook;
    private int purchaseCopyNo;

    private char bookIssue;
    private char bookWorkingStart;
    private char bookLostScrap;
    
}