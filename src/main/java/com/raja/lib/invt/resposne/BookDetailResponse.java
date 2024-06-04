package com.raja.lib.invt.resposne;

import java.util.List;

public class BookDetailResponse {
    private Integer bookId;
    private String bookName;
    private List<CopyDetail> copyDetails;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<CopyDetail> getCopyDetails() {
        return copyDetails;
    }

    public void setCopyDetails(List<CopyDetail> copyDetails) {
        this.copyDetails = copyDetails;
    }

    public static class CopyDetail {
        private Integer bookDetailId;
        private String accessionNo;
        private Integer purchaseCopyNo;

        public CopyDetail(Integer bookDetailId, String accessionNo, Integer purchaseCopyNo) {
            this.bookDetailId = bookDetailId;
            this.accessionNo = accessionNo;
            this.purchaseCopyNo = purchaseCopyNo;
        }

        public Integer getBookDetailId() {
            return bookDetailId;
        }

        public void setBookDetailId(Integer bookDetailId) {
            this.bookDetailId = bookDetailId;
        }

        public String getAccessionNo() {
            return accessionNo;
        }

        public void setAccessionNo(String accessionNo) {
            this.accessionNo = accessionNo;
        }

        public Integer getPurchaseCopyNo() {
            return purchaseCopyNo;
        }

        public void setPurchaseCopyNo(Integer purchaseCopyNo) {
            this.purchaseCopyNo = purchaseCopyNo;
        }
    }
}
