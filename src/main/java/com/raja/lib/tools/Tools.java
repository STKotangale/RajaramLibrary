package com.raja.lib.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Tools 
{
	public Connection conn;
	public Statement stmt;
	public ResultSet rset;
	protected PreparedStatement pstmt;
	protected String myName = "";
	
	public synchronized static String FirstLetterUpper(String name) 
	{
		name  = DataValidator.checkAlphaNumeric(name);
		if(!name.equals(""))
		{
			name = name.trim().toLowerCase() ;
			String name1 = name.substring(0, 1);
			name1 = name1.toUpperCase();
			String name2 = name.substring(1);
			name = name1 + name2;
		}
		return name ;
	}	

	public synchronized static String RelationName(String id) 
	{
		String relationName = "" ;
		if(  id.equals("01") )
		{
			relationName = "Family Head";
		}
		else if(  id.equals("02") )
		{
			relationName = "Wife";
		}
		else if(  id.equals("03") )
		{
			relationName = "Son";
		}
		else if(  id.equals("04") )
		{
			relationName = "Daughter";
		}
		else if(  id.equals("05") )
		{
			relationName = "Sister";
		}
		else if(  id.equals("06") )
		{
			relationName = "Brother";
		}
		else if(  id.equals("07") )
		{
			relationName = "Father";
		}
		else if(  id.equals("08") )
		{
			relationName = "Mother";
		}
		else if(  id.equals("09") )
		{
			relationName = "Grand Son";
		}
		else if(  id.equals("10") )
		{
			relationName = "Grand Daughter";
		}
		else if(  id.equals("11") )
		{
			relationName = "Nephew";
		}
		else if(  id.equals("12") )
		{
			relationName = "Niece";
		}
		else if(  id.equals("13") )
		{
			relationName = "Uncle";
		}
		else if(  id.equals("14") )
		{
			relationName = "Unty";
		}
		return relationName ;
	}	
	
	public synchronized static String RelationList(String id, String key) 
	{
		id = DataValidator.checkAlphaNumeric(id);
		String list = "<select name=\""+key+"\"     >";
		list = list + "<option value=\"00\"> Select </option>" ;
		if(  id.equals("01") )
		{
			list = list + "<option SELECTED value=\"01\">Family Head</option>" ;
		}
		else
		{
			list = list + "<option value=\"01\">Family Head</option>" ;
		}
		if(  id.equals("02") )
		{
			list = list + "<option SELECTED value=\"02\">Wife</option>" ;
		}
		else
		{
			list = list + "<option value=\"02\">Wife</option>" ;
		}
		if(  id.equals("03") )
		{
			list = list + "<option SELECTED value=\"03\">Son</option>" ;
		}
		else
		{
			list = list + "<option value=\"03\">Son</option>" ;
		}
		if(  id.equals("04") )
		{
			list = list + "<option SELECTED value=\"04\">Daughter</option>" ;
		}
		else
		{
			list = list + "<option value=\"04\">Daughter</option>" ;
		}

		if(  id.equals("05") )
		{
			list = list + "<option SELECTED value=\"05\">Sister</option>" ;
		}
		else
		{
			list = list + "<option value=\"05\">Sister</option>" ;
		}
		if(  id.equals("06") )
		{
			list = list + "<option SELECTED value=\"06\">Brother</option>" ;
		}
		else
		{
			list = list + "<option value=\"06\">Brother</option>" ;
		}
		if(  id.equals("07") )
		{
			list = list + "<option SELECTED value=\"07\">Father</option>" ;
		}
		else
		{
			list = list + "<option value=\"07\">Father</option>" ;
		}
		if(  id.equals("08") )
		{
			list = list + "<option SELECTED value=\"08\">Mother</option>" ;
		}
		else
		{
			list = list + "<option value=\"08\">Mother</option>" ;
		}
		if(  id.equals("09") )
		{
			list = list + "<option SELECTED value=\"09\">Grand Son</option>" ;
		}
		else
		{
			list = list + "<option value=\"09\">Grand Son</option>" ;
		}
		if(  id.equals("10") )
		{
			list = list + "<option SELECTED value=\"10\">Grand Daughter</option>" ;
		}
		else
		{
			list = list + "<option value=\"10\">Grand Daughter</option>" ;
		}
		if(  id.equals("11") )
		{
			list = list + "<option SELECTED value=\"11\">Nephew</option>" ;
		}
		else
		{
			list = list + "<option value=\"11\">Nephew</option>" ;
		}
		if(  id.equals("12") )
		{
			list = list + "<option SELECTED value=\"12\">Niece</option>" ;
		}
		else
		{
			list = list + "<option value=\"12\">Niece</option>" ;
		}
		if(  id.equals("13") )
		{
			list = list + "<option SELECTED value=\"13\">Uncle</option>" ;
		}
		else
		{
			list = list + "<option value=\"13\">Uncle</option>" ;
		}
		if(  id.equals("14") )
		{
			list = list + "<option SELECTED value=\"14\">Unty</option>" ;
		}
		else
		{
			list = list + "<option value=\"14\">Unty</option>" ;
		}
		if(  id.equals("15") )
		{
			list = list + "<option SELECTED value=\"15\">Daughter In Law</option>" ;
		}
		else
		{
			list = list + "<option value=\"15\">Daughter In Law</option>" ;
		}
		if(  id.equals("16") )
		{
			list = list + "<option SELECTED value=\"16\">Son In Law</option>" ;
		}
		else
		{
			list = list + "<option value=\"16\">Son In Law</option>" ;
		}
		if(  id.equals("17") )
		{
			list = list + "<option SELECTED value=\"17\">Husband</option>" ;
		}
		else
		{
			list = list + "<option value=\"17\">Husband</option>" ;
		}
		list = list + "</select>";
		return list ;
	}	
	
	public synchronized static String yesNo(String id, String key) 
	{
		id = DataValidator.checkAlphaNumeric(id);
		String list = "<select name=\""+key+"\"     >";
		list = list + "<option value=\"0\"> Select </option>" ;
		if(  id.equals("Y") )
		{
			list = list + "<option SELECTED value=\"Y\">Yes</option>" ;
		}
		else
		{
			list = list + "<option value=\"Y\">Yes</option>" ;
		}

		if(  id.equals("N") )
		{
			list = list + "<option SELECTED value=\"N\">No</option>" ;
		}
		else
		{
			list = list + "<option value=\"N\">No</option>" ;
		}
		list = list + "</select>";
		return list ;
	}

	public synchronized static int getSessionFinanceYear(String dt) 
	{
		int startmonth = Integer.parseInt(dt.substring(3, 5));
		int sessionyear = 0 ;
		if(startmonth >= 3)
		{
			sessionyear = Integer.parseInt(dt.substring(6,10));
		}
		else
		{
			sessionyear = Integer.parseInt(dt.substring(6,10)) - 1 ;
			
		}
		return sessionyear ;
	}	

	public synchronized static String genderList(String key, String id) 
	{
		String list = "<select name=\""+key+"\"   >";
		list = list + "<option value=\"0\"> Select </option>" ;
		try{
			if(  id.equals("M") )
			{
				list = list + "<option SELECTED value=\"M\">Male</option>" ;
			}
			else
			{
				list = list + "<option value=\"M\">Male</option>" ;
			}

			if(  id.equals("F") )
			{
				list = list + "<option SELECTED value=\"F\">Female</option>" ;
			}
			else
			{
				list = list + "<option value=\"F\">Female</option>" ;
			}
		}
		catch(Exception e){

			list = list + "<option value=\"M\">Male</option>" ;	
			list = list + "<option value=\"F\">Female</option>" ;
		}
		list = list + "</select>";
		return list ;
	}
	
	public synchronized static String genderListS(String id) 
	{
		String list = "<select name=\"gender\"   >";
		try{
			if(  id.equals("G") )
			{
				list = list + "<option SELECTED value=\"G\">General</option>" ;
			}
			else
			{
				list = list + "<option value=\"G\">General</option>" ;
			}
			if(  id.equals("M") )
			{
				list = list + "<option SELECTED value=\"M\">Male</option>" ;
			}
			else
			{
				list = list + "<option value=\"M\">Male</option>" ;
			}

			if(  id.equals("F") )
			{
				list = list + "<option SELECTED value=\"F\">Female</option>" ;
			}
			else
			{
				list = list + "<option value=\"F\">Female</option>" ;
			}
		}
		catch(Exception e){

			list = list + "<option value=\"M\">Male</option>" ;	
			list = list + "<option value=\"F\">Female</option>" ;
			list = list + "<option value=\"G\">General</option>" ;
		}
		list = list + "</select>";
		return list ;
	}
	
	public synchronized static String genderListSM(String id) 
	{
		String list = "<select name=\"memgender\"   >";
		try{
			if(  id.equals("M") )
			{
				list = list + "<option SELECTED value=\"M\">Male</option>" ;
			}
			else
			{
				list = list + "<option value=\"M\">Male</option>" ;
			}

			if(  id.equals("F") )
			{
				list = list + "<option SELECTED value=\"F\">Female</option>" ;
			}
			else
			{
				list = list + "<option value=\"F\">Female</option>" ;
			}
		}
		catch(Exception e){

			list = list + "<option value=\"M\">Male</option>" ;	
			list = list + "<option value=\"F\">Female</option>" ;
		}
		list = list + "</select>";
		return list ;
	}

	public synchronized static String stringIncrement(String key) 
	{
		int srno  = Integer.parseInt( key ) ;
		srno++;
		return PaddingLeft(10, ""+srno) ;
	}	

	public synchronized static String PaddingLeft(int padding, String key) 
	{
		key = "00000000000000000000" + key ;
		String list = key.substring(key.length() - padding ) ;	
		return list ;
	}	

	public synchronized static String PaddingRight(int padding, String key) 
	{
		key =  key + "00000000000000000000";
		String list = key.substring(0 , padding ) ;	
		return list ;
	}	

	public synchronized static String LengthFixing(int len, String key) 
	{
		try
		{
			key = key.trim() ;
			key = key.substring(0, len) ;
		}
		catch(Exception e)
		{
		}
		return key ;
	}	

	public synchronized static String RemoveSpace(String key) 
	{
		char[] keychar = key.toCharArray() ; 
		String ok = "" ;
		try
		{
			int len = 0 ;
			while(keychar.length >= len )
			{
				if(keychar[len] != ' ')
				{
					ok = ok + keychar[len] ;
				}
				len++;
			}
		}
		catch(Exception e)
		{
		}
		return ok ;
	}	

	public synchronized static String elecCurrentList(String id) 
	{
		String list = "<select name=\"ele_current\"   >";
		list = list + "<option value=\"N\"> No </option>" ;
		try{
			if(  id.equals("Y") )
			{
				list = list + "<option SELECTED value=\"Y\">Yes</option>" ;
			}
			else
			{
				list = list + "<option value=\"Y\">Yes</option>" ;
			}

					}
		catch(Exception e){

			list = list + "<option value=\"N\">No</option>" ;	
			list = list + "<option value=\"Y\">Yes</option>" ;
			
		}
		list = list + "</select>";
		return list ;
	}		

	public synchronized static String amountbenefitList(String id) 
	{
		String list = "<select name=\"amt_benefit\"   >";
		list = list + "<option value=\"A\"> Amount </option>" ;
		try{
			if(  id.equals("B") )
			{
				list = list + "<option SELECTED value=\"B\">Benefit</option>" ;
			}
			else
			{
				list = list + "<option value=\"B\">Benefit</option>" ;
			}

					}
		catch(Exception e){

			list = list + "<option value=\"A\">Amount</option>" ;	
			list = list + "<option value=\"B\">Benefit</option>" ;
			
		}
		list = list + "</select>";
		return list ;
	}

	public synchronized static String urbanRuralList(String id) 
	{
		String list = "<select name=\"urban_rural\"   >";
		list = list + "<option value=\"U\"> Urban </option>" ;
		try{
			if(  id.equals("R") )
			{
				list = list + "<option SELECTED value=\"R\">Rural</option>" ;
			}
			else
			{
				list = list + "<option value=\"R\">Rural</option>" ;
			}

					}
		catch(Exception e){

			list = list + "<option value=\"U\">Urban</option>" ;	
			list = list + "<option value=\"R\">Rural</option>" ;
			
		}
		list = list + "</select>";
		return list ;
	}		

	public synchronized static String recTypeList(String id) 
	{
		String list = "<select name=\"rec_type\"   >";
		list = list + "<option value=\"M\"> Monthly </option>" ;
		try{
			if(  id.equals("Q") )
			{
				list = list + "<option SELECTED value=\"Q\">Quarterly</option>" ;
			}
			else
			{
				list = list + "<option value=\"Q\">Quarterly</option>" ;
			}
			if(  id.equals("H") )
			{
				list = list + "<option SELECTED value=\"H\">Half yearly</option>" ;
			}
			else
			{
				list = list + "<option value=\"H\">Half yearly</option>" ;
			}
			if(  id.equals("Y") )
			{
				list = list + "<option SELECTED value=\"Y\">Yearly</option>" ;
			}
			else
			{
				list = list + "<option value=\"Y\">yearly</option>" ;
			}

					}
		catch(Exception e){

			list = list + "<option value=\"M\">Monthly</option>" ;	
			list = list + "<option value=\"Q\">Quarterly</option>" ;
			list = list + "<option value=\"H\">Half Yearly</option>" ;	
			list = list + "<option value=\"Y\">Yearly</option>" ;
			
		}
		list = list + "</select>";
		return list ;
	}	

	public synchronized static String accTypeList(String id) 
	{
		String list = "<select name=\"acc_type\"   >";
		list = list + "<option value=\"B\"> Bank </option>" ;
		try{
			if(  id.equals("P") )
			{
				list = list + "<option SELECTED value=\"P\">Post Office</option>" ;
			}
			else
			{
				list = list + "<option value=\"P\">Post Office</option>" ;
			}

					}
		catch(Exception e){

			list = list + "<option value=\"B\">Bank</option>" ;	
			list = list + "<option value=\"P\">Post Office</option>" ;
			
		}
		list = list + "</select>";
		return list ;
	}		

	public synchronized static String eduTypeList(String id, String fieldname, String onchange)
	{
		String list = "" ;
		if(onchange.equals(""))
		{
			list = "<select name=\""+fieldname+"\" >";
		}
		else
		{
			list = "<select name=\""+fieldname+"\"   onchange=\""+onchange+"\"   >";
		} 

		list = list + "<option value=\"0\"> Select </option>" ;
		if(  id.equals("1") )
		{
			list = list + "<option SELECTED value=\"1\">10th Under</option>" ;
		}
		else
		{
			list = list + "<option value=\"1\">10th Under</option>" ;
		}

		if(  id.equals("2") )
		{
			list = list + "<option SELECTED value=\"2\">10th</option>" ;
		}
		else
		{
			list = list + "<option value=\"2\">10th</option>" ;
		}
		if(  id.equals("3") )
		{
			list = list + "<option SELECTED value=\"3\">12th Under</option>" ;
		}
		else
		{
			list = list + "<option value=\"3\">12th Under</option>" ;
		}
		if(  id.equals("4") )
		{
			list = list + "<option SELECTED value=\"4\">12th</option>" ;
		}
		else
		{
			list = list + "<option value=\"4\">12th</option>" ;
		}
		if(  id.equals("5") )
		{
			list = list + "<option SELECTED value=\"5\">Graduate Under</option>" ;
		}
		else
		{
			list = list + "<option value=\"5\">Graduate Under</option>" ;
		}
		if(  id.equals("6") )
		{
			list = list + "<option SELECTED value=\"6\">Graduate</option>" ;
		}
		else
		{
			list = list + "<option value=\"6\">Graduate</option>" ;
		}

		if(  id.equals("7") )
		{
			list = list + "<option SELECTED value=\"7\">Post Graduate</option>" ;
		}
		else
		{
			list = list + "<option value=\"7\">Post Graduate</option>" ;
		}
		if(  id.equals("8") )
		{
			list = list + "<option SELECTED value=\"8\">Post Graduate (Upper)</option>" ;
		}
		else
		{
			list = list + "<option value=\"8\">Post Graduate (Upper)</option>" ;
		}
		list = list + "</select>";
		return list ;
	}
	
	public synchronized static String admintypeLoginDist(String id, String key) 
	{
		id = DataValidator.checkAlphaNumeric(id);
		String list = "<select name=\""+key+"\"     >";
		list = list + "<option value=\"0\"> Select </option>"  ;
		if(  id.equals("1") )
		{
			list = list + "<option SELECTED value=\"1\">Taluka</option>" ;
		}
		else
		{
			list = list + "<option value=\"1\">Taluka</option>" ;
		}

		if(  id.equals("2") )
		{
			list = list + "<option SELECTED value=\"2\">District</option>" ;
		}
		else
		{
			list = list + "<option value=\"2\">District</option>" ;
		}

		if(  id.equals("9") )
		{
			list = list + "<option SELECTED value=\"9\">School</option>" ;
		}
		else
		{
			list = list + "<option value=\"9\">School</option>" ;
		}
		list = list + "</select>";
		return list ;
	}
	
	public synchronized static String admintypeLogin(String id, String key) 
	{
		id = DataValidator.checkAlphaNumeric(id);
		String list = "<select name=\""+key+"\"     >";
		list = list + "<option value=\"0\"> Select </option>"  ;
		if(  id.equals("1") )
		{
			list = list + "<option SELECTED value=\"1\">Taluka</option>" ;
		}
		else
		{
			list = list + "<option value=\"1\">Taluka</option>" ;
		}

		if(  id.equals("2") )
		{
			list = list + "<option SELECTED value=\"2\">District</option>" ;
		}
		else
		{
			list = list + "<option value=\"2\">District</option>" ;
		}

		if(  id.equals("3") )
		{
			list = list + "<option SELECTED value=\"3\">Division</option>" ;
		}
		else
		{
			list = list + "<option value=\"3\">Division</option>" ;
		}

		if(  id.equals("4") )
		{
			list = list + "<option SELECTED value=\"4\">State</option>" ;
		}
		else
		{
			list = list + "<option value=\"4\">State</option>" ;
		}
		if(  id.equals("9") )
		{
			list = list + "<option SELECTED value=\"9\">School</option>" ;
		}
		else
		{
			list = list + "<option value=\"9\">School</option>" ;
		}
		list = list + "</select>";
		return list ;
	}
	
}