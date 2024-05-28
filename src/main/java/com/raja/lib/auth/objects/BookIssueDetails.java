package com.raja.lib.auth.objects;

public interface BookIssueDetails {
    Long getMemberId();
    String getBookName();
    Integer getPurchaseCopyNo();
    String getIssueDate();
    String getConfirmReturnDate();
    Double getMaxFineAmount();
    String getReturnDate();
}
