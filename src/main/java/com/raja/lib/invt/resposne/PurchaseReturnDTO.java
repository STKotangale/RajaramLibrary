package com.raja.lib.invt.resposne;



public interface PurchaseReturnDTO {
	
	 Long getstockDetailId();
     Long getstock_idF();
     int getsrno();
     Long getbook_idF();
     int getbook_qty();
     double getbook_rate();
     double getbook_amount();
     String getstock_type();
     String getbookName();
     String getledgerName();

}
