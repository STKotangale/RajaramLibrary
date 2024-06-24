package com.raja.lib.tools;

public class DataValidator 
{
	// Written By Sandesh Kotangale 
	// This Method Check Data Is Double Or Not
	// Return Value is true if double is false
	public static double checkDouble(String value)
	{
		double status = 0.00 ;
		try
		{
			status =  Double.parseDouble(value) ;
		}
		catch(Exception e)
		{
			status = 0.00 ;
		}
		return status;
	}

	// Written By Sandesh Kotangale 
	// This Method Check Data Is Double Or Not
	// Return Value is true if double is false
	public static int checkInt(String value)
	{
		int status = 0 ;
		try
		{
			try
			{
				status =  Integer.parseInt(value) ;
			}
			catch(Exception e)
			{
				status = 0 ;
			}
		}
		catch(Exception e)
		{
			status = 0 ;
		}
		return status;
	}	

	
	// Written By Sandesh Kotangale 
	// This Method Check Data Is Double Or Not
	// Return Value is true if double is false
	public static String checkAlphaNumeric(String value)
	{
		String status = "" ;
		try
		{
			if(value == null)
			{
				status = "" ;
			}
			else
			{
				status = value.trim() ;
			}
		}
		catch(Exception e)
		{
			status = "" ;
		}
		return status;
	}		
	
	// Written By Sandesh Kotangale 
	// This Method Check Data Is Double Or Not
	// Return Value is true if double is false
	public static String NullcheckAlphaNumeric(String value)
	{
		String status = "" ;
		try
		{
			if(value == null || value.equals(""))
			{
				status = null ;
			}
			else
			{
				status = value.trim() ;
			}
		}
		catch(Exception e)
		{
			status = null ;
		}
		return status;
	}
	
	
	// Written By Sandesh Kotangale 
	// This Method Check Data Is String Or Not
	// Return Value have Data Yes Else No
	public static String checkYesNo(String value)
	{
		String status = "" ;
		try
		{
			if(value == null)
			{
				status = "N" ;
			}
			else
			{
				status = value.trim() ;
				status = "Y" ;
			}
		}
		catch(Exception e)
		{
			status = "N" ;
		}
		return status;
	}		
	
}
