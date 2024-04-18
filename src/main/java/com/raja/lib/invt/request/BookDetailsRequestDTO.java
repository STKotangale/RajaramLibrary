package com.raja.lib.invt.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDetailsRequestDTO {
    private Long purchaseId; 
    private String ISBN;
    private String language;
    private String classificationNumber;
    private String itemNumber;
    private String author;
    private String editor;
    private String title;
    private String secondTitle;
    private String seriesTitle;
    private String edition;
    private String placeOfPublication;
    private String nameOfPublisher;
    private Integer publicationYear;
    private Double price;
    private Integer numberOfPages;
    private String subjectHeading;
    private String secondAuthorEditor;
    private String thirdAuthorEditor;
    private String itemType;
    private String permanentLocation;
    private String currentLocation;
    private String shelvingLocation;
    private Double bookPrice;
    private Integer volumeNo;
    private String fullCallNumber;
    private Integer copyNo;
    private Integer accessionNo;
}
