package com.raja.lib.invt.model;


import java.io.Serializable;
import org.springframework.stereotype.Component;
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
public class BookDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookDetailId")
    private int bookDetailId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockDetailIdF")
    private StockDetail stockDetailIdF;
    
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
    
    
    @Column(name="bookIssue")
    private String bookIssue;
    
    @Column(name="bookWorkingStart")
    private String bookWorkingStart;
    
    @Column(name="bookLost")
    private String bookLost;
    
    @Column(name="book_return")
    private String book_return;
    
    @Column(name="bookScrap")
    private String bookScrap;

    
    
}
