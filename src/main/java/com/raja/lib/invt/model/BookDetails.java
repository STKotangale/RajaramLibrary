package com.raja.lib.invt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookDetailId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_idF") 
    private PurchaseDetail purchaseDetail;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_detail_idf") 
    private PurchaseDetail purchaseDetailIdf;
    
    
    private String isbn;
    private double price;
    private String language;
    private String classificationNumber;
    private String itemNumber;
    private String author;
    private String editor;
    private String title;
    private String secondTitle;
    private String seriesTitle;
    private int edition;
    private String placeOfPublication;
    private String nameOfPublisher;
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
    private int rate;

    
    
}
