package com.raja.lib.tools;

public class ToolsList  
{
	
	public synchronized static String NosName( int mon ) 
	{
		String nm = "";
		if(mon == 1)
		{
			nm = mon+" St" ;
		}
		if(mon == 2)
		{
			nm = mon+" nd" ;
		}
		if(mon == 3)
		{
			nm = mon+" rd" ;
		}
		if(mon == 4)
		{
			nm = mon+" th" ;
		}
		if(mon == 5)
		{
			nm = mon+" th" ;
		}
		if(mon == 6)
		{
			nm = mon+" th" ;
		}
		if(mon == 7)
		{
			nm = mon+" th" ;
		}
		if(mon == 8)
		{
			nm = mon+" th" ;
		}
		if(mon == 9)
		{
			nm = mon+" th" ;
		}
		if(mon == 10)
		{
			nm = mon+" th" ;
		}
		if(mon == 11)
		{
			nm = mon+" th" ;
		}
		if(mon == 12)
		{
			nm = mon+" th" ;
		}
		return nm ;
	}

	
	public synchronized static String lowerClassList(String attmonth, int mon ) 
	{
		String list = "<select name=\""+attmonth+"\"    >";
		list = list + "<option value=\"0\"> Select </option>"  ;
		if(mon == 1)
		{
			list = list + "<option SELECTED value=\"1\">1</option>" ;
		}
		else
		{
			list = list + "<option  value=\"1\">1</option>" ;
		}
		if(mon == 2)
		{
			list = list + "<option SELECTED value=\"2\">2</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"2\">2</option>"  ;
		}
		if(mon == 3)
		{
			list = list + "<option SELECTED value=\"3\">3</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"3\">3</option>"  ;
		}
		if(mon == 4)
		{
			list = list + "<option SELECTED value=\"4\">4</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"4\">4</option>"  ;
		}
		if(mon == 5)
		{
			list = list + "<option SELECTED value=\"5\">5</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"5\">5</option>"  ;
		}
		if(mon == 6)
		{
			list = list + "<option SELECTED value=\"6\">6</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"6\">6</option>"   ;
		}
		if(mon == 7)
		{
			list = list + "<option SELECTED value=\"7\">7</option>"    ;
		}
		else
		{
			list = list + "<option  value=\"7\">7</option>"    ;
		}
		
		if(mon == 8)
		{
			list = list + "<option SELECTED value=\"8\">8</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"8\">8</option>"   ;
		}
		
		if(mon == 9)
		{
			list = list + "<option SELECTED value=\"9\">9</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"9\">9</option>"   ;
		}
		
		if(mon == 10)
		{
			list = list + "<option SELECTED value=\"10\">10</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"10\">10</option>"   ;
		}
		
		if(mon == 11)
		{
			list = list + "<option SELECTED value=\"11\">11</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"11\">11</option>"   ;
		}
		
		if(mon == 12)
		{
			list = list + "<option SELECTED value=\"12\">12</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"12\">12</option>"   ;
		}
		list = list + "</select>" ;
		return list ;
	}
	
	public synchronized static String ClassList(String standard_nm, int standard) 
	{
		String list = "<select name=\""+standard_nm+"\"    >";
		list = list + "<option value=\"0\"> Select </option>"  ;
		if(standard == 1)
		{
			list = list + "<option SELECTED value=\"1\">1</option>" ;
		}
		else
		{
			list = list + "<option  value=\"1\">1</option>" ;
		}
		if(standard == 2)
		{
			list = list + "<option SELECTED value=\"2\">2</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"2\">2</option>"  ;
		}
		if(standard == 3)
		{
			list = list + "<option SELECTED value=\"3\">3</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"3\">3</option>"  ;
		}
		if(standard == 4)
		{
			list = list + "<option SELECTED value=\"4\">4</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"4\">4</option>"  ;
		}
		if(standard == 5)
		{
			list = list + "<option SELECTED value=\"5\">5</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"5\">5</option>"  ;
		}
		if(standard == 6)
		{
			list = list + "<option SELECTED value=\"6\">6</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"6\">6</option>"   ;
		}
		if(standard == 7)
		{
			list = list + "<option SELECTED value=\"7\">7</option>"    ;
		}
		else
		{
			list = list + "<option  value=\"7\">7</option>"    ;
		}
		
		if(standard == 8)
		{
			list = list + "<option SELECTED value=\"8\">8</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"8\">8</option>"   ;
		}
		
		if(standard == 9)
		{
			list = list + "<option SELECTED value=\"9\">9</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"9\">9</option>"   ;
		}
		
		if(standard == 10)
		{
			list = list + "<option SELECTED value=\"10\">10</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"10\">10</option>"   ;
		}
		
		if(standard == 11)
		{
			list = list + "<option SELECTED value=\"11\">11</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"11\">11</option>"   ;
		}
		
		if(standard == 12)
		{
			list = list + "<option SELECTED value=\"12\">12</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"12\">12</option>"   ;
		}
		list = list + "</select>" ;
		return list ;
	}
	
	
	public synchronized static String schoolTypeList(String id) 
	{
		String list = "<select name=\"schooltype\"   >";
		list = list + "<option value=\"C\"> Co-Educational </option>" ;
		try{
			if(  id.equals("S") )
			{
				list = list + "<option SELECTED value=\"S\">Single-Sex Education</option>" ;
			}
			else
			{
				list = list + "<option value=\"S\">Single-Sex Education</option>" ;
			}

					}
		catch(Exception e){

			list = list + "<option value=\"C\">Co-Educational</option>" ;	
			list = list + "<option value=\"S\">Single-Sex Education</option>" ;
			
		}
		list = list + "</select>";
		return list ;
	}		

	public synchronized static String reportTypeList(String key, String id) 
	{
		String list = "<select name=\""+key+"\"   >";
		list = list + "<option value=\"0\"> Select </option>" ;
		try
		{
			if(id.equals("1") )
			{
				list = list + "<option SELECTED value=\"1\">Daily</option>" ;
			}
			else
			{
				list = list + "<option value=\"1\">Daily</option>" ;
			}
			if(id.equals("2") )
			{
				list = list + "<option SELECTED value=\"2\">Monthly</option>" ;
			}
			else
			{
				list = list + "<option value=\"2\">Monthly</option>" ;
			}

			if(id.equals("3") )
			{
				list = list + "<option SELECTED value=\"3\">Yearly</option>" ;
			}
			else
			{
				list = list + "<option value=\"3\">Yearly</option>" ;
			}
		}
		catch(Exception e)
		{
			list = list + "<option value=\"1\">Daily</option>" ;	
			list = list + "<option value=\"2\">Monthly</option>" ;
			list = list + "<option value=\"3\">Yearly</option>" ;
		}
		list = list + "</select>";
		return list ;
	}

	public synchronized static String statusName(String id) 
	{
		String reportType = "" ;
		if(id.equals("1") )
		{
			reportType =  "Daily" ;
		}
		else if(id.equals("2") )
		{
			reportType =  "Monthly" ;
		}
		else if(id.equals("3") )
		{
			reportType =  "Yearly" ;
		}
		return reportType ;
	}
	
	public synchronized static String sanction_rejected_List(String id, String key) 
	{
		String list = "<select name=\""+key+"\"    >";
		list = list + "<option value=\"0\"> Select </option>" ;
		if( id.equals("1") )
		{
			list = list + "<option SELECTED value=\"1\">Sanction</option>" ;
		}
		else
		{
			list = list + "<option value=\"1\">Sanction</option>" ;
		}
		if( id.equals("2") )
		{
			list = list + "<option SELECTED value=\"2\">Rejected</option>" ;
		}
		else
		{
			list = list + "<option value=\"2\">Rejected</option>" ;
		}
		list = list + "</select>";
		return list ;
	}		
		
}