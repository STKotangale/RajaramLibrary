package com.raja.lib.invt.objects;

import java.util.Date;

public interface PurchaseDetaill {
	
	int getsrno();
	int setsrno();
	
	String getledger_name();
	String setledger_name();

	String getinvoice_no();
	String setinvoice_no();

	Date getinvoice_date();
	Date setinvoice_date();

	int getgrand_total();
	int setgrand_total();


}
