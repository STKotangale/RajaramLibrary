package com.raja.lib.auth.objects;

public interface BookIssueDetails {
    Long getMemberId();
    String getBookName();
    Integer getPurchaseCopyNo();
    String getaccessionNo();
    String getIssueDate();
    String getReturnDate();
    String getConfirmReturnDate();
    Double getMaxFineAmount();
}
