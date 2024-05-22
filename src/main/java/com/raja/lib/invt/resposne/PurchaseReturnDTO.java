package com.raja.lib.invt.resposne;

public interface PurchaseReturnDTO {
    Long getStockDetailId();
    Long getStock_idF();
    int getSrno();
    Long getBook_idF();
    int getBook_qty();
    double getBook_rate();
    double getBook_amount();
    String getStock_type();
    String getBookName();
    String getLedgerName();
    int getPurchaseCopyNo();
    Long getBookDetailId();
    Long getStock_id();
    String getInvoiceNo();
    String getInvoiceDate();
    double getBillTotal();
    double getDiscountPercent();
    double getDiscountAmount();
    double getTotalAfterDiscount();
    double getGstPercent();
    double getGstAmount();
    double getGrandTotal();
    Long getLedgerIDF();
    String getMemberIdF();
}
