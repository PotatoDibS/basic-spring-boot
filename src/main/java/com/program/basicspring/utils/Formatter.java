package com.program.basicspring.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Formatter {
  
  public static Date nowDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));

    return calendar.getTime();
  }

  public static Long getLongFromStringDate(String _date) {
    Long millisecond = 0L;
    TimeZone tz = TimeZone.getTimeZone("Asia/Bangkok");

    try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setTimeZone(tz);
			millisecond = dateFormat.parse(_date).getTime();
		} catch (ParseException e) {
			return 0L;
		}
	
		return millisecond;
  }

  public static String getDateString(Date timestamp, String format) {
    String formatVal = format == null ? "yyyy-MM-dd hh:mm:ss" : format;
    Date tStamp = timestamp == null ? Calendar.getInstance().getTime() : timestamp;
    TimeZone tz = TimeZone.getTimeZone("Asia/Bangkok");
    SimpleDateFormat dateFormat = new SimpleDateFormat(formatVal);
    dateFormat.setTimeZone(tz);
    return dateFormat.format(tStamp);
  }

  public static Date stringToDateOnly(String s){
    Date result = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      result = dateFormat.parse(s);
    } catch(ParseException e){
      return null;
    }
    return result;
  }

  public static Date stringToTimestamps(String s){
      Date result = null;
      try{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result = dateFormat.parse(s);
      } catch(ParseException e){
        e.printStackTrace();
      }
      return result;
  }
}
