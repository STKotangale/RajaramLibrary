package com.raja.lib.invt.resposne;


import java.util.List;

public class BookDetailResponse {
    private String bookName;
    private List<Integer> purchaseCopyNos;

    // Getters and setters
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<Integer> getPurchaseCopyNos() {
        return purchaseCopyNos;
    }

    public void setPurchaseCopyNos(List<Integer> purchaseCopyNos) {
        this.purchaseCopyNos = purchaseCopyNos;
    }
}
