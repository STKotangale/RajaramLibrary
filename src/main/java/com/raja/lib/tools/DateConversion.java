package com.raja.lib.tools;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

public class DateConversion  implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static String currentDate = "";
    
    public static DateConversion getInstance()
    {
        return new DateConversion();
    }
    
    public static String StringYYYYMMDDToStringDDMMYYYY(String utildate) throws ParseException
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date convertedDate = sdf.parse(utildate);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = dateFormat.format(convertedDate);
        return dateStr;
    }    
    
// ---- Set Date British DD -- MM -- YYYY
    
    public static String getStringCurrentDateDDMMYYYY()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date utildate = new Date();
        currentDate = dateFormat.format(utildate);
        return currentDate;
    }
    
    public static String getStringCurrentDateTimeDDMMYYYY()
    {
        currentDate = "";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.S a");
        Date utildate = new Date();
        currentDate = dateFormat.format(utildate);
        return currentDate;
    }

    public static String getStringCurrentTimeHHMM()
    {
        currentDate = "";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.S a");
        Date utildate = new Date();
        currentDate = dateFormat.format(utildate);
        return currentDate.substring(11, 16);
    }

    public static Date StringToDateDDMMYYYY(String sdob)
    {
        Date chDate = null;        
        try
        {
            Calendar calendar = new GregorianCalendar();
            calendar.set(Integer.parseInt(sdob.substring(6,10)), Integer.parseInt(sdob.substring(3,5))-1, Integer.parseInt(sdob.substring(0,2)), 0, 0, 0);
            chDate = calendar.getTime();
        } 
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return chDate;
    }

    public Date StringToDateTimeDDMMYYYY(String sdob)
    {
        Date chDate = null;
        try
        {
            Calendar calendar = new GregorianCalendar();
            if(sdob.trim().length() < 12 )
            {
            	calendar.set( Integer.parseInt(sdob.substring(6,10)), Integer.parseInt(sdob.substring(3,5))-1, Integer.parseInt(sdob.substring(0,2)) );
            }
            else
            {
            	if(sdob.substring(22,23).equals("A"))
            	{
            		calendar.set( Integer.parseInt(sdob.substring(6,10)), Integer.parseInt(sdob.substring(3,5))-1, Integer.parseInt(sdob.substring(0,2)), Integer.parseInt(sdob.substring(11,13)), Integer.parseInt(sdob.substring(14,16)), Integer.parseInt(sdob.substring(17,19))  );
            	}
            	else
            	{
            		calendar.set( Integer.parseInt(sdob.substring(6,10)), Integer.parseInt(sdob.substring(3,5))-1, Integer.parseInt(sdob.substring(0,2)), Integer.parseInt(sdob.substring(11,13))+12, Integer.parseInt(sdob.substring(14,16)), Integer.parseInt(sdob.substring(17,19))  );
            	}
            }
            chDate = calendar.getTime();
        } 
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return chDate;
    }

    public static String DateToStringDDMMYYYY(Date utildate)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = dateFormat.format(utildate);
        return dateStr;
    }    
    
    public String DateToStringTimeDDMMYYYY(Date utildate)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.S a");
        String dateStr = dateFormat.format(utildate);
        return dateStr;
    }

    public String StringDDMMYYYYToStringTimeDDMMYYYY(String ddmmyyyy)
    {
        Date chDate = null;
        try
        {
            Calendar calendar = new GregorianCalendar();
            if(ddmmyyyy.trim().length() < 12 )
            {
            	calendar.set( Integer.parseInt(ddmmyyyy.substring(6,10)), Integer.parseInt(ddmmyyyy.substring(3,5))-1, Integer.parseInt(ddmmyyyy.substring(0,2)) );
            }
            else
            {
            	if(ddmmyyyy.substring(22,23).equals("A"))
            	{
            		calendar.set( Integer.parseInt(ddmmyyyy.substring(6,10)), Integer.parseInt(ddmmyyyy.substring(3,5))-1, Integer.parseInt(ddmmyyyy.substring(0,2)), Integer.parseInt(ddmmyyyy.substring(11,13)), Integer.parseInt(ddmmyyyy.substring(14,16)), Integer.parseInt(ddmmyyyy.substring(17,19))  );
            	}
            	else
            	{
            		calendar.set( Integer.parseInt(ddmmyyyy.substring(6,10)), Integer.parseInt(ddmmyyyy.substring(3,5))-1, Integer.parseInt(ddmmyyyy.substring(0,2)), Integer.parseInt(ddmmyyyy.substring(11,13))+12, Integer.parseInt(ddmmyyyy.substring(14,16)), Integer.parseInt(ddmmyyyy.substring(17,19))  );
            	}
            }
            chDate = calendar.getTime();
        } 
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.S a");
        String dateStr = dateFormat.format(chDate);
        return dateStr;
    }

    public static String StringDDMMYYYYToStringYYYYMMDD(String ddmmyyyy)
    {
    	ddmmyyyy = ddmmyyyy.trim() ;
        String dateStr = "" ;
        // sdob.substring(6,10)), Integer.parseInt(sdob.substring(3,5))-1, Integer.parseInt(sdob.substring(0,2)
        try
        {  // 01-04-2024
        	dateStr = ddmmyyyy.substring(6,10)+"-"+ddmmyyyy.substring(3,5)+"-"+ddmmyyyy.substring(0,2) ;  
        } 
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return dateStr;
    }
    
    public Date DateToDateTimeDDMMYYYY(Date chDate)
    {
        try
        {
        	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String dateStr = dateFormat.format(chDate);
            chDate = StringToDateTimeDDMMYYYY(dateStr);
        } 
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return chDate;
    }    
    // Convertion From String DD-MM-YYYY to String YYYY-MM-DD
    
    public String StringDDMMYYYtoStringYYYYMMDD(String ddmmyyyy)
    {
    	return ddmmyyyy.substring(6,10)+"-"+ddmmyyyy.substring(3,5)+"-"+ddmmyyyy.substring(0,2);
    }
     
    
 // --- Set Date YYYY -- MM -- DD
    
    public static String getCurrentDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date utildate = new Date();
        currentDate = dateFormat.format(utildate);
        return currentDate;
    }

 // --- Set Date DD -- MM -- YYYY
    
    public static String getDD_MM_YYYYCurrentDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date utildate = new Date();
        currentDate = dateFormat.format(utildate);
        return currentDate;
    }
    
    public static int getCurrentMonth()  
    {
        int mon = Integer.parseInt(getStringCurrentDateDDMMYYYY().substring(3, 5)); 
        return mon;
    }
    
    public static int getCurrentYear()  
    {
        int mon = Integer.parseInt(getStringCurrentDateDDMMYYYY().substring(6, 10)); 
        return mon;
    }
    
    public String getCurrentDateTime()
    {
        currentDate = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S a");
        Date utildate = new Date();
        currentDate = dateFormat.format(utildate);
        return currentDate;
    }

    public Date StringToDate(String sdob)
    {
        Date chDate = null;
        try
        {
    		String[] dtArray = sdob.split("-");
            Calendar calendar = new GregorianCalendar();
            calendar.set(Integer.parseInt(dtArray[0]), Integer.parseInt(dtArray[1]) - 1, Integer.parseInt(dtArray[2]), 0, 0, 0);
            chDate = calendar.getTime();
        } 
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return chDate;
    }
    
    public Date StringToDateTime(String sdob)
    {
        Date chDate = null;
        try
        {
    		String[] dtArray = sdob.split("-");
            Calendar calendar = new GregorianCalendar();
            calendar.set(Integer.parseInt(dtArray[0]), Integer.parseInt(dtArray[1]) - 1, Integer.parseInt(dtArray[2].substring(0,2)));
            chDate = calendar.getTime();
        } 
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return chDate;
    }

    public String DateToString(Date utildate)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(utildate);
        return dateStr;
    }
    
    public String DateToStringTime(Date utildate)
    {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S a");
        String dateStr = dateFormat.format(utildate);
        return dateStr;
    }
    
// -- Get Month Name By passing parameter as month number  
    
    public static String getMonthName(int month)
    {
        String monthName = "";
        switch (month)
        {
            case 1 :
                monthName = "JANUARY";
                break;
            case 2 :
                monthName = "FEBRUARY";
                break;
            case 3 :
                monthName = "MARCH";
                break;
            case 4 :
                monthName = "APRIL";
                break;
            case 5 :
                monthName = "MAY";
                break;
            case 6 :
                monthName = "JUNE";
                break;
            case 7 :
                monthName = "JULY";
                break;
            case 8 :
                monthName = "AUGUST";
                break;
            case 9 :
                monthName = "SEPTEMBER";
                break;
            case 10 :
                monthName = "OCTOBER";
                break;
            case 11 :
                monthName = "NOVEMBER";
                break;
            case 12 :
                monthName = "DECEMBER";
                break;
        }
        return monthName;
    }

	public synchronized static String getMonthNameByMonthNo(int month)
	{
		String monthName=null;
		switch(month)
		{
		case 1:monthName="Jan";break;
		case 2:monthName="Feb";break;
		case 3:monthName="Mar";break;
		case 4:monthName="Apr";break;
		case 5:monthName="May";break;
		case 6:monthName="Jun";break;
		case 7:monthName="July";break;
		case 8:monthName="Aug";break;
		case 9:monthName="Sept";break;
		case 10:monthName="Oct";break;
		case 11:monthName="Nov";break;
		case 12:monthName="Dec";break;
		}
		return monthName;
	}	
    
// --  Getting Indian Time by adding difference with US
    
    public String getIndiaCurrentDateTime()
    {
        currentDate = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S a");
        Date utildate = new Date(new Date().getTime() + 570 * 60 * 1000);
        currentDate = dateFormat.format(utildate);
        return currentDate;
    }

//  --  Getting Year month String format
    public String getYearMonth(int year, int month)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month - 1, 1);
        Date utildate = calendar.getTime();
        currentDate = dateFormat.format(utildate);
        return currentDate.substring(0, 7);
    }
    
    public static String getStartDateFromYearMonth(int year, int month)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month - 1, 1);
        Date utildate = calendar.getTime();
        currentDate = dateFormat.format(utildate);
        return currentDate;
    }

    public static String getEndDateFromYearMonth(int year, int month)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month - 1, getDaysInMonths(year, month, 1) );
        Date utildate = calendar.getTime();
        currentDate = dateFormat.format(utildate);
        return currentDate;
    }

    public int SDateToIYear(String date)
    {
        int year = Integer.parseInt(date.substring(0, 4));
        return year;
    }

    public static int SDateToIMonth(String date)
    {
        int mon = Integer.parseInt(date.substring(3, 5)); 
        return mon;
    }

    public static int SDateDDMMYYYYToIYear(String date)
    {
        int year = Integer.parseInt(date.substring(6, 10));  
        return year;
    }

    public static int SDateDDMMYYYYToWeekNo(String date)
    {
		Date curr = DateConversion.StringToDateDDMMYYYY(date) ;
		Calendar cal = Calendar.getInstance();
        cal = Calendar.getInstance();
        cal.setTime(curr);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }
    
    public static int getCurrentFinancialYear(String todaysDate)
    {
    	int year = Integer.parseInt(todaysDate.substring(6,10)) ;
    	int month = Integer.parseInt(todaysDate.substring(3,5)) ;
    	if(month == 1 || month == 2 || month == 3)
    	{
    		year = year - 1 ;
    	}
        return year;
    }
    
    public static int getCurrentFinancialYearYYYYMMDD(String todaysDate)
    {
    	int year = Integer.parseInt(todaysDate.substring(0,4)) ;
    	int month = Integer.parseInt(todaysDate.substring(5,7)) ;
    	if(month == 1 || month == 2 || month == 3)
    	{
    		year = year - 1 ;
    	}
        return year;
        
    }
    
   
    public int getCurrentFinancialYear()
    {
    	String todaysDate = getStringCurrentDateDDMMYYYY();
    	int year = Integer.parseInt(todaysDate.substring(6,10)) ;
    	int month = Integer.parseInt(todaysDate.substring(3,5)) ;
    	if(month == 1 || month == 2 || month == 3 || month == 4)
    	{
    		year = year - 1 ;
    	}
        return year;
    }
    
    public static String changeDateByYear(String date, int finacialYear )
    {
    	String todaysDate = "" ;
    	int month = Integer.parseInt(date.substring(3,5)) ;
    	if(month == 1 || month == 2 || month == 3 || month == 4)
    	{
    		finacialYear = finacialYear + 1 ;
    		todaysDate = date.substring(0,6) + finacialYear ;
    	}
    	else
    	{
    		todaysDate = date.substring(0,6) + finacialYear ;
    	}
        return todaysDate;
    }

	public synchronized static String getMonths(String attmonth, int mon ) 
	{
		String list = "<select name=\""+attmonth+"\"    >";
		list = list + "<option value=\"0\"> Select </option>"  ;
		if(mon == 1)
		{
			list = list + "<option SELECTED value=\"1\">January</option>" ;
		}
		else
		{
			list = list + "<option  value=\"1\">January</option>" ;
		}
		if(mon == 2)
		{
			list = list + "<option SELECTED value=\"2\">Feb</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"2\">Feb</option>"  ;
		}
		if(mon == 3)
		{
			list = list + "<option SELECTED value=\"3\">March</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"3\">March</option>"  ;
		}
		if(mon == 4)
		{
			list = list + "<option SELECTED value=\"4\">April</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"4\">April</option>"  ;
		}
		if(mon == 5)
		{
			list = list + "<option SELECTED value=\"5\">May</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"5\">May</option>"  ;
		}
		if(mon == 6)
		{
			list = list + "<option SELECTED value=\"6\">June</option>"  ;
		}
		else
		{
			list = list + "<option  value=\"6\">June</option>"   ;
		}
		if(mon == 7)
		{
			list = list + "<option SELECTED value=\"7\">July</option>"    ;
		}
		else
		{
			list = list + "<option  value=\"7\">July</option>"    ;
		}
		
		if(mon == 8)
		{
			list = list + "<option SELECTED value=\"8\">August</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"8\">August</option>"   ;
		}
		
		if(mon == 9)
		{
			list = list + "<option SELECTED value=\"9\">September</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"9\">September</option>"   ;
		}
		
		if(mon == 10)
		{
			list = list + "<option SELECTED value=\"10\">October</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"10\">October</option>"   ;
		}
		
		if(mon == 11)
		{
			list = list + "<option SELECTED value=\"11\">November</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"11\">November</option>"   ;
		}
		
		if(mon == 12)
		{
			list = list + "<option SELECTED value=\"12\">December</option>"   ;
		}
		else
		{
			list = list + "<option  value=\"12\">December</option>"   ;
		}
		list = list + "</select>" ;
		return list ;
	}

	
    public static int getDaysInMonths(int year, int month, int day )
    {
    	Calendar cal = new GregorianCalendar(year, month-1, day);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH) ;
    }
	
	public  static int DaysinMonth(int year,int month)
	{
		Calendar calendar = Calendar.getInstance();
        switch(month)
		{
			case 1:{ month= Calendar.JANUARY; break;  }
			case 2:{ month= Calendar.FEBRUARY;  break;}
			case 3:{ month= Calendar.MARCH;  break;}
			case 4:{ month= Calendar.APRIL;  break;}
			case 5:{ month= Calendar.MAY;  break;}
			case 6:{ month= Calendar.JUNE;  break;}
			case 7:{ month= Calendar.JULY;  break;}
			case 8:{ month= Calendar.AUGUST;  break;}
			case 9:{ month= Calendar.SEPTEMBER;  break;}
			case 10:{ month= Calendar.OCTOBER;  break;}
			case 11:{ month= Calendar.NOVEMBER;  break;}
			case 12:{ month= Calendar.DECEMBER; break;}
     	}

			calendar.set(year,month,1);
	        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	        return days;
	}
	
// ---- Set First Date Of Month
// Parameter Is Month	
    
    public static String getFirstDateOfMonth(int month, int year)
    {
    	if(month == 1 || month == 2 || month == 3)
    	{
    		year = year +1 ;
    	}	
        Calendar calendar = new GregorianCalendar();
    	calendar.set( year, month-1, 1 );

    	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date utildate = calendar.getTime() ;
        currentDate = dateFormat.format(utildate);
        return currentDate;
    }

 // ---- Set Last Date Of Month
 // Parameter Is Month	
     
     public static String getLastDateOfMonth(int month, int year)
     {
     	if(month == 1 || month == 2 || month == 3)
    	{
    		year = year +1 ;
    	}	
     	int lastday = DaysinMonth(year, month) ; 
        Calendar calendar = new GregorianCalendar();
     	calendar.set( year, month-1,  lastday);
     	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date utildate = calendar.getTime() ;
        currentDate = dateFormat.format(utildate);
        return currentDate;
     }
    
     public static String getCurrentTime() {
    	Calendar cal = Calendar.getInstance();
 	    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
 	    String time = sdf.format(cal.getTime());
 	    return time;
	 }
     
     @SuppressWarnings("static-access")
	public static String addInCurrentTime(int hr, int min, int addtime ) 
    {
    	Calendar cal = Calendar.getInstance();
    	cal.set(cal.DATE, cal.MONTH, cal.YEAR, hr, (  min + addtime ) );
 	    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
 	    String time = sdf.format(cal.getTime());
  	    return time;
 	}

     public static String AddDaysToStringDateDDMMYYYY(String sdob, int days)
     {
    	 String adddate = "" ;
         Date chDate = null;        
         try
         {
             Calendar calendar = new GregorianCalendar();
             int newdays = Integer.parseInt(sdob.substring(0,2)) + days ; 
             calendar.set(Integer.parseInt(sdob.substring(6,10)), Integer.parseInt(sdob.substring(3,5))-1, newdays, 0, 0, 0);
             chDate = calendar.getTime();
             adddate = (String) DateToStringDDMMYYYY( chDate ); 
         } 
         catch (Exception e)
         {
             System.out.println(e.toString());
         }
         return adddate;
     }     

     // Method use for Date Compare
     // If date one is big value is 1
     // If date sec is big value is -1
     // date are equal then value is 0
     public static int DateCompareDDMMYYYY(String one, String sec)
     {
    	 int value = 0 ;
    	 Date date1 = (Date) StringToDateDDMMYYYY(one) ;
    	 Date date2 = (Date) StringToDateDDMMYYYY(sec) ;
         try
         {
        	 
        	 value = date1.compareTo(date2);
         } 
         catch (Exception e)
         {
             System.out.println(e.toString());
         }
         return value;
     }     
     
     // Get String Date mm-dd-yyyy 
     @SuppressWarnings("deprecation")
	public static int DayOfWeekFromDateDDMMYYYY(String date)
    {
    	 int value = 0 ;
         try
         {
        	DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
      		Date today = (Date) df.parse(date);
      		value = today.getDay();
 	  	 } 
         catch (Exception e)
         {
             System.out.println(e.toString());
         }
         return value;
    }     

 	public static int getCurrentHOUR()
    {
    	 int value = 0 ;
         try
         {
			Calendar calendar = Calendar.getInstance();
      		value = calendar.get(Calendar.HOUR_OF_DAY);
 	  	 } 
         catch (Exception e)
         {
             System.out.println(e.toString());
         }
         return value;
    }     
    
	public synchronized static String getFinancialYearList(String key, int financialYear) 
	{
		String list = "<select name=\""+key+"\"    >";
		list = list + "<option value=\"0000\"> Select </option>" ;
		if(  financialYear == 2007 )
		{
			list = list + "<option SELECTED value=\"2007\">2007-2008</option>" ;
		}
		else
		{
			list = list + "<option value=\"2007\">2007-2008</option>" ;
		}

		if(  financialYear == 2008 )
		{
			list = list + "<option SELECTED value=\"2008\">2008-2009</option>" ;
		}
		else
		{
			list = list + "<option value=\"2008\">2008-2009</option>" ;
		}

		if(  financialYear == 2009 )
		{
			list = list + "<option SELECTED value=\"2009\">2009-2010</option>" ;
		}
		else
		{
			list = list + "<option value=\"2009\">2009-2010</option>" ;
		}
		if(  financialYear == 2010 )
		{
			list = list + "<option SELECTED value=\"2010\">2010-2011</option>" ;
		}
		else
		{
			list = list + "<option value=\"2010\">2010-2011</option>" ;
		}
		if(  financialYear == 2011 )
		{
			list = list + "<option SELECTED value=\"2011\">2011-2012</option>" ;
		}
		else
		{
			list = list + "<option value=\"2011\">2011-2012</option>" ;
		}
		if(  financialYear == 2012 )
		{
			list = list + "<option SELECTED value=\"2012\">2012-2013</option>" ;
		}
		else
		{
			list = list + "<option value=\"2012\">2012-2013</option>" ;
		}
		if(  financialYear == 2013 )
		{
			list = list + "<option SELECTED value=\"2013\">2013-2014</option>" ;
		}
		else
		{
			list = list + "<option value=\"2013\">2013-2014</option>" ;
		}
		if(  financialYear == 2014 )
		{
			list = list + "<option SELECTED value=\"2014\">2014-2015</option>" ;
		}
		else
		{
			list = list + "<option value=\"2014\">2014-2015</option>" ;
		}
		if(  financialYear == 2015 )
		{
			list = list + "<option SELECTED value=\"2015\">2015-2016</option>" ;
		}
		else
		{
			list = list + "<option value=\"2015\">2015-2016</option>" ;
		}
		if(  financialYear == 2016 )
		{
			list = list + "<option SELECTED value=\"2016\">2016-2017</option>" ;
		}
		else
		{
			list = list + "<option value=\"2016\">2016-2017</option>" ;
		}
		if(  financialYear == 2017 )
		{
			list = list + "<option SELECTED value=\"2017\">2017-2018</option>" ;
		}
		else
		{
			list = list + "<option value=\"2017\">2017-2018</option>" ;
		}
		if(  financialYear == 2018 )
		{
			list = list + "<option SELECTED value=\"2018\">2018-2019</option>" ;
		}
		else
		{
			list = list + "<option value=\"2018\">2018-2019</option>" ;
		}
		if(  financialYear == 2019 )
		{
			list = list + "<option SELECTED value=\"2019\">2019-2020</option>" ;
		}
		else
		{
			list = list + "<option value=\"2019\">2019-2020</option>" ;
		}
		if(  financialYear == 2020 )
		{
			list = list + "<option SELECTED value=\"2020\">2020-2021</option>" ;
		}
		else
		{
			list = list + "<option value=\"2020\">2020-2021</option>" ;
		}
		if(  financialYear == 2021 )
		{
			list = list + "<option SELECTED value=\"2021\">2021-2022</option>" ;
		}
		else
		{
			list = list + "<option value=\"2021\">2021-2022</option>" ;
		}
		if(  financialYear == 2022 )
		{
			list = list + "<option SELECTED value=\"2022\">2022-2023</option>" ;
		}
		else
		{
			list = list + "<option value=\"2022\">2022-2023</option>" ;
		}
		if(  financialYear == 2023 )
		{
			list = list + "<option SELECTED value=\"2023\">2023-2024</option>" ;
		}
		else
		{
			list = list + "<option value=\"2023\">2023-2024</option>" ;
		}
		list = list + "</select>";
		return list ;
	}	



	public synchronized static int noOfDaysInMonth(int month,int year)
	{
		int numDays=0;
		switch (month) 
		{
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			numDays = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			numDays = 30;
			break;
		case 2:
			if ( ((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0) )
				numDays = 29;
			else
				numDays = 28;
			break;
		default:
			System.out.println("Invalid month.");
		break;
		}
		return numDays;

	}

	public synchronized static String dayList(String id, String key, String onchange)  
	{
		String list = "" ;
		if(onchange.equals(""))
		{
			list = "<select name=\""+key+"\" >";
		}
		else
		{
			list = "<select name=\""+key+"\"   onchange=\""+onchange+"()\"  >";
		} 
		list = list + "<option value=\"\"> Select </option>" ;
		if(  id.equals("0") )
		{
			list = list + "<option SELECTED value=\"0\">Sunday</option>" ;
		}
		else
		{
			list = list + "<option  value=\"0\">Sunday</option>" ;
		}
		if(  id.equals("1") )
		{
			list = list + "<option SELECTED value=\"1\">Monday</option>" ;
		}
		else
		{
			list = list + "<option  value=\"1\">Monday</option>" ;
		}
		if(  id.equals("2") )
		{
			list = list + "<option SELECTED value=\"2\">Tuesday</option>" ;
		}
		else
		{
			list = list + "<option  value=\"2\">Tuesday</option>" ;
		}
		if(  id.equals("3") )
		{
			list = list + "<option SELECTED value=\"3\">Wednesday</option>" ;
		}
		else
		{
			list = list + "<option  value=\"3\">Wednesday</option>" ;
		}
		if(  id.equals("4") )
		{
			list = list + "<option SELECTED value=\"4\">Thursday</option>" ;
		}
		else
		{
			list = list + "<option  value=\"4\">Thursday</option>" ;
		}
		if(  id.equals("5") )
		{
			list = list + "<option SELECTED value=\"5\">Friday</option>" ;
		}
		else
		{
			list = list + "<option  value=\"5\">Friday</option>" ;
		}
		if(  id.equals("6") )
		{
			list = list + "<option SELECTED value=\"6\">Saturday</option>" ;
		}
		else
		{
			list = list + "<option  value=\"6\">Saturday</option>" ;
		}
		list = list + "</select>";
		return list ;
	}	

	public static int getDayNumber(Object object){
		String dayNo[]={"0","1","2","3","4","5","6",};
		String dayName[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

		String dayNm = String.valueOf(object);
		int result=0;
		try{
			if(dayNm!=""){
				for(int i=0;i<dayName.length;i++){

					if(dayName[i].contains(dayNm) || dayName[i].contains(dayNm.toLowerCase())){
						result = Integer.parseInt(dayNo[i]);
					}
				}
			}

		}catch (Exception e) {
			return -1;
		}
		return result;
	}

	public static int getDay(String day){

		String dayName[]={"sunday","monday","tuesday","wednesday","thursday","friday","saturday"};
		try{
			if(day!=""){
				for(int i=0;i<dayName.length;i++){

					if(dayName[i].contains(day.toLowerCase())){
						return i;
					}
				}
			}

		}catch (Exception e) {
			return -1;
		}
		return -1;
	}
	public static String getDayByNumber(int nday){
		String dayName[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","saturday"};
		if(nday>=0 && nday<7)
		{
			return dayName[nday];
		}

		return "";
	}
	
	public synchronized static String monthList(String id, String key, String onchange) 
	{
		String list = "" ;
		id = Tools.PaddingLeft(2, id) ;
 		if(onchange.equals(""))
		{
			list = "<select name=\""+key+"\" >";
		}
		else
		{
			list = "<select name=\""+key+"\"   onchange=\""+onchange+"()\"  >";
		} 
		list = list + "<option value=\"0\"> Select </option>" ;
		
		try
		{
			if(  id.equals("01") )
			{
				list = list + "<option SELECTED value=\"01\">January</option>" ;
			}
			else
			{
				list = list + "<option value=\"01\">January</option>" ;
			}

			if(  id.equals("02") )
			{
				list = list + "<option SELECTED value=\"02\">February</option>" ;
			}
			else
			{
				list = list + "<option value=\"02\">February</option>" ;
			}
			if(  id.equals("03") )
			{
				list = list + "<option SELECTED value=\"03\">March</option>" ;
			}
			else
			{
				list = list + "<option value=\"03\">March</option>" ;
			}
			if(  id.equals("04") )
			{
				list = list + "<option SELECTED value=\"04\">April</option>" ;
			}
			else
			{
				list = list + "<option value=\"04\">April</option>" ;
			}
			if(  id.equals("05") )
			{
				list = list + "<option SELECTED value=\"05\">May</option>" ;
			}
			else
			{
				list = list + "<option value=\"05\">May</option>" ;
			}
			if(  id.equals("06") )
			{
				list = list + "<option SELECTED value=\"06\">June</option>" ;
			}
			else
			{
				list = list + "<option value=\"06\">June</option>" ;
			}
			if(  id.equals("07") )
			{
				list = list + "<option SELECTED value=\"07\">July</option>" ;
			}
			else
			{
				list = list + "<option value=\"07\">July</option>" ;
			}
			if(  id.equals("08") )
			{
				list = list + "<option SELECTED value=\"08\">August</option>" ;
			}
			else
			{
				list = list + "<option value=\"08\">August</option>" ;
			}
			if(  id.equals("09") )
			{
				list = list + "<option SELECTED value=\"09\">September</option>" ;
			}
			else
			{
				list = list + "<option value=\"09\">September</option>" ;
			}
			if(  id.equals("10") )
			{
				list = list + "<option SELECTED value=\"10\">October</option>" ;
			}
			else
			{
				list = list + "<option value=\"10\">October</option>" ;
			}
			if(  id.equals("11") )
			{
				list = list + "<option SELECTED value=\"11\">November</option>" ;
			}
			else
			{
				list = list + "<option value=\"11\">Novmber</option>" ;
			}
			if(  id.equals("12") )
			{
				list = list + "<option SELECTED value=\"12\">December</option>" ;
			}
			else
			{
				list = list + "<option value=\"12\">December</option>" ;
			}
		}
		catch(Exception e)
		{

		}
		list = list + "</select>";
		return list ;
	}	


	public synchronized static boolean validateFromDateAndToDate(String frDate, String toDate) 
	{
		//dd-mm-yyyy
		if(Integer.parseInt(frDate.substring(7, 4))>Integer.parseInt(toDate.substring(7, 4)))
		{
			return false;
		}
		else if(Integer.parseInt(frDate.substring(7, 4))==Integer.parseInt(toDate.substring(7, 4)))
		{
			if(Integer.parseInt(frDate.substring(4, 2))>Integer.parseInt(toDate.substring(4, 2)))
			{
				return false;
			}
			else if(Integer.parseInt(frDate.substring(4, 2))==Integer.parseInt(toDate.substring(4, 2)))
			{
				if(Integer.parseInt(frDate.substring(1, 2))>Integer.parseInt(toDate.substring(1, 2)))
				{
					return false;
				}
				else
				{
					return true;
				}
			}
			else
			{
				return true;
			}
		}
		else
		{
			return true;
		}

	}

	public static synchronized List<String> dateListBetTwoDates(String frDate, String toDate )
	{
		System.out.println(frDate);
		System.out.println(toDate);
		String frDateSplit[] = frDate.split("-");
		String toDateSplit[] = toDate.split("-");
		Calendar gcFrDate = new GregorianCalendar(Integer.parseInt(frDateSplit[2]),Integer.parseInt(frDateSplit[1])-1,Integer.parseInt(frDateSplit[0]));
		Calendar gcToDate = new GregorianCalendar(Integer.parseInt(toDateSplit[2]),Integer.parseInt(toDateSplit[1])-1,Integer.parseInt(toDateSplit[0]));
		Format formatter;
		formatter = new SimpleDateFormat("dd-MM-yyyy");
		List <String> list = new Vector <String> ();
		list.add(formatter.format(gcFrDate.getTime()));	        
		Calendar gcFrDateC = (Calendar) gcFrDate.clone();

		while(gcFrDateC.before(gcToDate))
		{
			gcFrDateC.add(Calendar.DATE, 1);
			//list.add(formatter.format( ( (Calendar) gcFrDateC.clone()).getTime())    );
			list.add(formatter.format( (  gcFrDateC).getTime())    );
		}
		return list;
	}

	public static synchronized List<String> monthListBetTwoDates(String frDate, String toDate )
	{
		System.out.println(frDate);
		System.out.println(toDate);
		String frDateSplit[] = frDate.split("-");
		String toDateSplit[] = toDate.split("-");
		Calendar gcFrDate = new GregorianCalendar(Integer.parseInt(frDateSplit[2]),Integer.parseInt(frDateSplit[1])-1,Integer.parseInt(frDateSplit[0]));
		Calendar gcToDate = new GregorianCalendar(Integer.parseInt(toDateSplit[2]),Integer.parseInt(toDateSplit[1])-1,Integer.parseInt(toDateSplit[0]));
		Format formatter;
		formatter = new SimpleDateFormat("dd-MM-yyyy");
		List <String> list = new Vector <String> ();
		list.add(formatter.format(gcFrDate.getTime()));	        
		Calendar gcFrDateC = (Calendar) gcFrDate.clone();
		while(gcFrDateC.before(gcToDate))
		{
			gcFrDateC.add(Calendar.MONTH, 1);
			list.add(formatter.format( (  gcFrDateC).getTime())    );
		}
		return list;
	}

	public static synchronized String incrementYear(String frDate, int incrby)
	{
		int year = Integer.parseInt(frDate.substring(6,10));
		year++;
		frDate = frDate.substring(0,6)+year;
		return frDate;
	}
	
	public static void main(String argu[])
	{
 		System.out.println("Value "+ getCurrentHOUR() );
 		
	}
    
}