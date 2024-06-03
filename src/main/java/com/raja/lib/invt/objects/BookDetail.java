package com.raja.lib.invt.objects;

public interface BookDetail {

    Long getBookDetailId();
    String getPurchaseCopyNo(); 
    String getBookName(); 
    Long getBook_rate(); 
    int getStatus();
    
    // Additional fields
    Long getStockDetailIdF();
    Long getBookIdF();
    String getIsbn();
    String getClassificationNumber();
    String getItemNumber();
    String getEditor();
    String getTitle();
    String getSecondTitle();
    String getSeriesTitle();
    int getEdition();
    int getPublicationYear();
    int getNumberOfPages();
    String getSubjectHeading();
    String getSecondAuthorEditor();
    String getThirdAuthorEditor();
    String getItemType();
    String getPermanentLocation();
    String getCurrentLocation();
    String getShelvingLocation();
    int getVolumeNo();
    String getFullCallNumber();
    String getCopyNo();
    String getAccessionNo();
    String getBookIssue();
    String getBookWorkingStart();
    String getBookLost();
    String getBook_return();
    String getBookScrap();
}
