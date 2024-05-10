package com.raja.lib.invt.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookDetailsRequest {
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
    private String bookIssue;
    private String bookWorkingStart;
    private String bookLostScrap;



   
}
