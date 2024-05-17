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
		private Integer purchaseCopyNo;

		public CopyDetail(Integer bookDetailId, Integer purchaseCopyNo) {
			this.bookDetailId = bookDetailId;
			this.purchaseCopyNo = purchaseCopyNo;
		}

		public Integer getBookDetailId() {
			return bookDetailId;
		}

		public void setBookDetailId(Integer bookDetailId) {
			this.bookDetailId = bookDetailId;
		}

		public Integer getPurchaseCopyNo() {
			return purchaseCopyNo;
		}

		public void setPurchaseCopyNo(Integer purchaseCopyNo) {
			this.purchaseCopyNo = purchaseCopyNo;
		}
	}
}
