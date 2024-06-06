package com.raja.lib.auth.objects;

public interface MemberBookInfo {
    Long getmemberId();
    String getbookName();
    Integer getpurchaseCopyNo();
    String getissueDate();
    String getreturnDate();
    String getaccessionNo();
}
