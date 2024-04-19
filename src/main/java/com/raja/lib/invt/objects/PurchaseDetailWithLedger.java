package com.raja.lib.invt.objects;

import java.util.Date;

public interface PurchaseDetailWithLedger {
    Long getPurchase_id();
    double getBill_total();
    double getDiscount_amount();
    double getDiscount_percent();
    double getGrand_total();
    double getGst_amount();
    double getGst_percent();
    Date getInvoice_date();
    String getInvoice_no();
    double getTotal_after_discount();
    Long getPurchase_detail_id();
    double getAmount();
    String getBook_name();
    int getQty();
    double getRate();
    Long getPurchase_idf();
    Long getLedger_id();
    String getLedger_code();
    String getLedger_name();
}
