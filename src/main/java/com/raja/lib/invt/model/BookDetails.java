package com.raja.lib.invt.model;

import org.hibernate.annotations.JoinColumnOrFormula;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "invt_book_details")
public class BookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookDetailId")
    private Long bookDetailId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseDetailIdF")
    private PurchaseDetail purchaseDetail;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookIdF") 
    private Book bookIdF;
    
    @Column(name="isbn")
    private String isbn;
    
    @Column(name="classificationNumber")
    private String classificationNumber;

    @Column(name="itemNumber")
    private String itemNumber;

    @Column(name="editor")
    private String editor;

    @Column(name="title")
    private String title;

    @Column(name="secondTitle")
    private String secondTitle;
    
    @Column(name="seriesTitle")
    private String seriesTitle;
    
    @Column(name="edition")
    private int edition;
    
    @Column(name="publicationYear")
    private int publicationYear;
    
    @Column(name="numberOfPages")
    private int numberOfPages;
    
    @Column(name="subjectHeading")
    private String subjectHeading;
    
    @Column(name="secondAuthorEditor")
    private String secondAuthorEditor;
    
    @Column(name="thirdAuthorEditor")
    private String thirdAuthorEditor;
    
    @Column(name="itemType")
    private String itemType;
    
    
    @Column(name="permanentLocation")
    private String permanentLocation;
    
    @Column(name="currentLocation")
    private String currentLocation;
    
    @Column(name="shelvingLocation")
    private String shelvingLocation;
    
    @Column(name="volumeNo")
    private int volumeNo;
    
    @Column(name="fullCallNumber")
    private String fullCallNumber;
    
    @Column(name="copyNo")
    private String copyNo;
    
    @Column(name="accessionNo")
    private String accessionNo;
    
    @Column(name="typeofbook")
    private String typeofbook;
    
    @Column(name="purchaseCopyNo")
    private int purchaseCopyNo;
    
    
//    private int rate;
//    private double price;
//    private String language;
//    private String author;
//    private String placeOfPublication;
//    private String nameOfPublisher;

    
    
}
