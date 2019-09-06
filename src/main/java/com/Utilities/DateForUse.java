package com.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.annotations.Test;


public class DateForUse {
	
	   // To get date and time as number
		public static String getDateTimeAsNumber() {

			String dt1 = new SimpleDateFormat("yyMdHmss").format(new Date());
			return dt1;
		}
		
		// To get date and time as Format like '10Oct2017 13:56:31'
		public static String getDateTimeAsFormat() {

			 String dt2 = new SimpleDateFormat("ddMMMyyyy H-mm-ss").format(new Date());
			 return dt2;
		}
		
		//To Split given date(Ex: 10/May/2017 to 10,May,2017)
		public static String[] splitDate(String date){
			String[] dates=new String[3];
			
			dates=date.split("/");
						
			return dates;
		}
		
		
		//To get the dates between given date range without saturday and sunday
		
		@Test
		public static List getDatesExcludeSatSun(String startDate,String endDate ) throws ParseException{
			
			
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
			DateTime today = new DateTime().withTimeAtStartOfDay();
	      
	      
			Date startdate=simpleDateFormat.parse(startDate);
			Date enddate=simpleDateFormat.parse(endDate);
	      
			DateTime dateStart = new DateTime(startdate);
			DateTime dateEnd = new DateTime(enddate);
					
			int days = Days.daysBetween(dateStart, dateEnd).getDays();
		    System.out.println(days);
		     
		    List <String> li=new ArrayList<String>(days);
		   // Specific format demanded by the StackOverflow.com question.
		   DateTimeFormatter formatter = DateTimeFormat.forPattern( "EEEE,dd MMM yyyy" );
		   for(int i=0; i<=days; i++){
		       String s= formatter.print( dateStart.plusDays( i ) ) ;
		       if(!(s.startsWith("S"))){
		    	   
		    	   
		    	   li.add(s.split(",")[1]);
//		    	   System.out.println(li.get(i));
		    	   
		       }
		       
		   }
			return li;
		}
		
	
		
}
