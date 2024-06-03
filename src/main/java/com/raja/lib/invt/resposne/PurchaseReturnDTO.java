package com.raja.lib.invt.resposne;

public interface PurchaseReturnDTO {
    String getLedgerName();
    int getPurchaseCopyNo();
    Long getBookDetailId();
    Long getStockDetailId();
    Long getStock_idF();
    int getSrno();
    Long getBook_idF();
    int getBook_qty();
    Double getBook_rate();
    Double getBook_amount();
    String getStock_type();
    String getBookName();
    Long getStock_id();
    String getInvoiceNo();
    String getInvoiceDate();
    Double getBillTotal();
    Double getDiscountPercent();
    Double getDiscountAmount();
    Double getTotalAfterDiscount();
    Double getGstPercent();
    Double getGstAmount();
    Double getGrandTotal();
    Long getLedgerIDF();
    String getMemberIdF();
}
