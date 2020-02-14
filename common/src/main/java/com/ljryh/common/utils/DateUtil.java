package com.ljryh.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 */
public class DateUtil {

    /**
     * Base ISO 8601 Date format yyyyMMdd i.e., 20021225 for the 25th day of December in the year 2002
     */
    public static final String ISO_DATE_FORMAT = "yyyyMMdd";
    
    /**
     * Base ISO 8601 Date format yyyyMMddHH i.e., 2002122512 for 12h the 25th day of December in the year 2002
     */
    public static final String ISO_DATE_FORMAT_H_m_s = "yyyyMMddHHmmss";

    /**
     * Expanded ISO 8601 Date format yyyy-MM-dd i.e., 2002-12-25 for the 25th day of December in the year 2002
     */
    public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * yyyy-MM-dd hh:mm:ss
     */
    public static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_PATTERN = "yyyyMMddHHmmss";
    /**
     * Default lenient setting for getDate.
     */
    private static boolean LENIENT_DATE = false;


    private static Random random = new Random();
    private static final int ID_BYTES = 10;

    public synchronized static String generateId() {
        StringBuffer result = new StringBuffer();
        result = result.append(System.currentTimeMillis());
        for (int i = 0; i < ID_BYTES; i++) {
            result = result.append(random.nextInt(10));
        }
        return result.toString();
    }

    protected static final float normalizedJulian(float JD) {

        float f = Math.round(JD + 0.5f) - 0.5f;

        return f;
    }

    /**
     * Returns the Date from a julian. The Julian date will be converted to noon GMT,
     * such that it matches the nearest half-integer (i.e., a julian date of 1.4 gets
     * changed to 1.5, and 0.9 gets changed to 0.5.)
     *
     * @param JD the Julian date
     * @return the Gregorian date
     */
    public static final Date toDate(float JD) {

        /* To convert a Julian Day Number to a Gregorian date, assume that it is for 0 hours, Greenwich time (so
         * that it ends in 0.5). Do the following calculations, again dropping the fractional part of all
         * multiplicatons and divisions. Note: This method will not give dates accurately on the
         * Gregorian Proleptic Calendar, i.e., the calendar you get by extending the Gregorian
         * calendar backwards to years earlier than 1582. using the Gregorian leap year
         * rules. In particular, the method fails if Y<400. */
        float Z = (normalizedJulian(JD)) + 0.5f;
        float W = (int) ((Z - 1867216.25f) / 36524.25f);
        float X = (int) (W / 4f);
        float A = Z + 1 + W - X;
        float B = A + 1524;
        float C = (int) ((B - 122.1) / 365.25);
        float D = (int) (365.25f * C);
        float E = (int) ((B - D) / 30.6001);
        float F = (int) (30.6001f * E);
        int day = (int) (B - D - F);
        int month = (int) (E - 1);

        if (month > 12) {
            month = month - 12;
        }

        int year = (int) (C - 4715); //(if Month is January or February) or C-4716 (otherwise)

        if (month > 2) {
            year--;
        }

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1); // damn 0 offsets
        c.set(Calendar.DATE, day);

        return c.getTime();
    }

    /**
     * Returns the days between two dates. Positive values indicate that
     * the second date is after the first, and negative values indicate, well,
     * the opposite. Relying on specific times is problematic.
     *
     * @param early the "first date"
     * @param late the "second date"
     * @return the days between the two dates
     */
    public static final int daysBetween(Date early, Date late) {

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(early);
        c2.setTime(late);

        return daysBetween(c1, c2);
    }

    /**
     * Returns the days between two dates. Positive values indicate that
     * the second date is after the first, and negative values indicate, well,
     * the opposite.
     *
     * @param early
     * @param late
     * @return the days between two dates.
     */
    public static final int daysBetween(Calendar early, Calendar late) {

        return (int) (toJulian(late) - toJulian(early));
    }

    /**
     * Return a Julian date based on the input parameter. This is
     * based from calculations found at
     * <a href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day Calculations
     * (Gregorian Calendar)</a>, provided by Bill Jeffrys.
     * @param c a calendar instance
     * @return the julian day number
     */
    public static final float toJulian(Calendar c) {

        int Y = c.get(Calendar.YEAR);
        int M = c.get(Calendar.MONTH);
        int D = c.get(Calendar.DATE);
        int A = Y / 100;
        int B = A / 4;
        int C = 2 - A + B;
        float E = (int) (365.25f * (Y + 4716));
        float F = (int) (30.6001f * (M + 1));
        float JD = C + D + E + F - 1524.5f;

        return JD;
    }

    /**
     * Return a Julian date based on the input parameter. This is
     * based from calculations found at
     * <a href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day Calculations
     * (Gregorian Calendar)</a>, provided by Bill Jeffrys.
     * @param date
     * @return the julian day number
     */
    public static final float toJulian(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return toJulian(c);
    }

    /**
     * @param isoString
     * @param fmt
     * @param field   Calendar.YEAR/Calendar.MONTH/Calendar.DATE
     * @param amount
     * @return
     * @throws ParseException
     */
    public static final String dateIncrease(String isoString, String fmt,
                                            int field, int amount) {

        try {
            Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(
                    "GMT"));
            cal.setTime(stringToDate(isoString, fmt, true));
            cal.add(field, amount);

            return dateToString(cal.getTime(), fmt);

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Time Field Rolling function.
     * Rolls (up/down) a single unit of time on the given time field.
     *
     * @param isoString
     * @param field the time field.
     * @param up Indicates if rolling up or rolling down the field value.
     * @param expanded use formating char's
     * @exception ParseException if an unknown field value is given.
     */
    public static final String roll(String isoString, String fmt, int field,
                                    boolean up) throws ParseException {

        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(
                "GMT"));
        cal.setTime(stringToDate(isoString, fmt));
        cal.roll(field, up);

        return dateToString(cal.getTime(), fmt);
    }

    /**
     * Time Field Rolling function.
     * Rolls (up/down) a single unit of time on the given time field.
     *
     * @param isoString
     * @param field the time field.
     * @param up Indicates if rolling up or rolling down the field value.
     * @exception ParseException if an unknown field value is given.
     */
    public static final String roll(String isoString, int field, boolean up) throws
            ParseException {

        return roll(isoString, DATETIME_PATTERN, field, up);
    }

    /**
     *  java.util.Date
     * @param dateText
     * @param format
     * @param lenient
     * @return
     */
    public static Date stringToDate(String dateText, String format,
                                    boolean lenient) {

        if (dateText == null) {

            return null;
        }

        DateFormat df = null;

        try {

            if (format == null) {
                df = new SimpleDateFormat();
            } else {
                df = new SimpleDateFormat(format);
            }

            // setLenient avoids allowing dates like 9/32/2001
            // which would otherwise parse to 10/2/2001
            df.setLenient(false);

            return df.parse(dateText);
        } catch (ParseException e) {

            return null;
        }
    }

    /**
     * @return Timestamp
     */
    public static java.sql.Timestamp getCurrentTimestamp() {
        return new java.sql.Timestamp(new Date().getTime());
    }

    /** java.util.Date
     * @param dateText
     * @param format
     * @return
     */
    public static Date stringToDate(String dateString, String format) {

        return stringToDate(dateString, format, LENIENT_DATE);
    }

    /**
     * java.util.Date
     * @param dateText
     */
    public static Date stringToDate(String dateString) {
        return stringToDate(dateString, ISO_EXPANDED_DATE_FORMAT, LENIENT_DATE);
    }

    /**
     * @return
     * @param pattern
     * @param date
     */
    public static String dateToString(Date date, String pattern) {

        if (date == null) {

            return null;
        }

        try {

            SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
            sfDate.setLenient(false);

            return sfDate.format(date);
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date, ISO_EXPANDED_DATE_FORMAT);
    }

    public static String dateToString2(Date date) {
        return dateToString(date, "yyyy-M-d");
    }

    /**
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static String dateToString3(Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm");
    }
    /**
     * @return
     */
    public static Date getCurrentDateTime() {
        Calendar calNow = Calendar.getInstance();
        Date dtNow = calNow.getTime();

        return dtNow;
    }

    /**
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDateString(String pattern) {
        return dateToString(getCurrentDateTime(), pattern);
    }

    /**
     *   yyyy-MM-dd
     * @return
     */
    public static String getCurrentDateString() {
        return dateToString(getCurrentDateTime(), ISO_EXPANDED_DATE_FORMAT);
    }

    /**
     * 返回固定格式的当前时间
     *   yyyy-MM-dd hh:mm:ss
     * @param date
     * @return
     */
    public static String dateToStringWithTime( ) {

        return dateToString(new Date(), DATETIME_PATTERN);
    }


    /**
     *   yyyy-MM-dd hh:mm:ss
     * @param date
     * @return
     */
    public static String dateToStringWithTime(Date date) {

        return dateToString(date, DATETIME_PATTERN);
    }

    /**
     *
     * @param date
     * @param days
     * @return java.util.Date
     */
    public static Date dateIncreaseByDay(Date date, int days) {

        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(
                "GMT"));
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    /**
     *
     * @param date
     * @param days
     * @return java.util.Date
     */
    public static Date dateIncreaseByMonth(Date date, int mnt) {

        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(
                "GMT"));
        cal.setTime(date);
        cal.add(Calendar.MONTH, mnt);

        return cal.getTime();
    }

    /**
     *
     * @param date
     * @param mnt
     * @return java.util.Date
     */
    public static Date dateIncreaseByYear(Date date, int mnt) {

        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(
                "GMT"));
        cal.setTime(date);
        cal.add(Calendar.YEAR, mnt);

        return cal.getTime();
    }

    /**
     *
     * @param date   yyyy-MM-dd
     * @param days
     * @return  yyyy-MM-dd
     */
    public static String dateIncreaseByDay(String date, int days) {
        return dateIncreaseByDay(date, ISO_DATE_FORMAT, days);
    }

    /**
     * @param date
     * @param fmt
     * @param days
     * @return
     */
    public static String dateIncreaseByDay(String date, String fmt, int days) {
        return dateIncrease(date, fmt, Calendar.DATE, days);
    }

    /**
     *
     * @param src
     * @param srcfmt
     * @param desfmt
     * @return
     */
    public static String stringToString(String src, String srcfmt,
                                        String desfmt) {
        return dateToString(stringToDate(src, srcfmt), desfmt);
    }

    /**
     *
     * @param date
     * @return string
     */
    public static String getYear(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat(
                "yyyy");
        String cur_year = formater.format(date);
        return cur_year;
    }

    /**
     *
     * @param date
     * @return string
     */
    public static String getMonth(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat(
                "MM");
        String cur_month = formater.format(date);
        return cur_month;
    }

    /**
     * @param date
     * @return string
     */
    public static String getDay(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat(
                "dd");
        String cur_day = formater.format(date);
        return cur_day;
    }

    /**
     * @param date
     * @return string
     */
    public static String getHour(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat(
                "HH");
        String cur_day = formater.format(date);
        return cur_day;
    }

    public static int getMinsFromDate(Date dt) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dt);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        return ((hour * 60) + min);
    }

    /**
     * Function to convert String to Date Object. If invalid input then current or next day date
     * is returned (Added by Ali Naqvi on 2006-5-16).
     * @param str String input in YYYY-MM-DD HH:MM[:SS] format.
     * @param isExpiry boolean if set and input string is invalid then next day date is returned
     * @return Date
     */
    public static Date convertToDate(String str, boolean isExpiry) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt = null;
        try {
            dt = fmt.parse(str);
        } catch (ParseException ex) {
            Calendar cal = Calendar.getInstance();
            if (isExpiry) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
            } else {
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
            }
            dt = cal.getTime();
        }
        return dt;
    }

    public static Date convertToDate(String str) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date dt = null;
        try {
            dt = fmt.parse(str);
        } catch (ParseException ex) {
            dt = new Date();
        }
        return dt;
    }

    public static String dateFromat(Date date, int minute) {
        String dateFormat = null;
        int year = Integer.parseInt(getYear(date));
        int month = Integer.parseInt(getMonth(date));
        int day = Integer.parseInt(getDay(date));
        int hour = minute / 60;
        int min = minute % 60;
        dateFormat = String.valueOf(year)
                     +
                     (month > 9 ? String.valueOf(month) :
                      "0" + String.valueOf(month))
                     +
                     (day > 9 ? String.valueOf(day) : "0" + String.valueOf(day))
                     + " "
                     +
                     (hour > 9 ? String.valueOf(hour) : "0" + String.valueOf(hour))
                     +
                     (min > 9 ? String.valueOf(min) : "0" + String.valueOf(min))
                     + "00";
        return dateFormat;
    }
    
    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
    	Calendar cal = Calendar.getInstance();

    	if (cal.before(birthday)) {
    		throw new IllegalArgumentException(
    				"The birthDay is before Now.It's unbelievable!");
    	}

    	int yearNow = cal.get(Calendar.YEAR);
    	int monthNow = cal.get(Calendar.MONTH) + 1;
    	int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

    	cal.setTime(birthday);
    	int yearBirth = cal.get(Calendar.YEAR);
    	int monthBirth = cal.get(Calendar.MONTH) + 1;
    	int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

    	int age = yearNow - yearBirth;

    	if (monthNow <= monthBirth) {
    		if (monthNow == monthBirth) {
    			// monthNow==monthBirth 
    			if (dayOfMonthNow < dayOfMonthBirth) {
    				age--;
    			}
    		} else {
    			// monthNow>monthBirth 
    			age--;
    		}
    	}
    	return age;
    }
    
    public static String sDateFormat() {
    	return new SimpleDateFormat(DATE_PATTERN).format(Calendar.getInstance().getTime());	
    }
   /* public static void main(String[] args) {
    	System.out.println(getAgeByBirthday(stringToDate("1983-05-02 00:00:00"))) ;
	}*/
    /**    
     * 获得指定日期所在当月第一天    
     *     
     * @param date    
     * @return    
    * @throws ParseException 
     */    
    public static String getFirstDayOfMonth(Date dated, String formatStr) throws ParseException { 
    	SimpleDateFormat formatDate = new SimpleDateFormat(formatStr);
        Calendar c = Calendar.getInstance();    
        c.setTime(dated);    
        c.set(Calendar.DAY_OF_MONTH, 1);    
        return formatDate.format(c.getTime());  
    }    
    
    /**    
     * 获得指定日期所在当月最后一天    
     *     
     * @param date    
     * @return    
    * @throws ParseException 
     */    
    public static String getLastDayOfMonth(Date dated, String formatStr) throws ParseException {    
   	 	SimpleDateFormat formatDate = new SimpleDateFormat(formatStr);
        Calendar c = Calendar.getInstance();    
        c.setTime(dated);    
        c.set(Calendar.DATE, 1);    
        c.add(Calendar.MONTH, 1);    
        c.add(Calendar.DATE, -1);    
        return  formatDate.format(c.getTime());  
    }
	public static List<String> getStartEndDateList(String sdate,String edate) throws ParseException{
		List<String> rtnList = new ArrayList<String>();
		if(sdate.compareTo(edate)>0){
			return rtnList;
		}
		if(sdate.compareTo(edate)==0){
			rtnList.add(sdate);
			return rtnList;
		}
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		Date startDate = formatDate.parse(sdate);
		Date endDate = formatDate.parse(edate);
		
		while(startDate.before(endDate)){
			String str = formatDate.format(startDate);
			rtnList.add(str);
			cal.setTime(startDate);
			cal.add(Calendar.DATE, 1);
			startDate = cal.getTime();
		}
		rtnList.add(edate);
		return rtnList;
	}
	
	// 季度一年四季， 第一季度：1月-3月， 第二季度：4月-6月， 第三季度：7月-9月， 第四季度：10月-12月  
    public static int getQuarterInMonth(int month, boolean isQuarterStart) {  
    	int months[] = {0, 3, 6, 9};  
        if (!isQuarterStart) {  
            months = new int[] {2, 5, 8, 11 };  
        }  
        if (month >= 0 && month <= 2)  
            return months[0];  
        else if (month >= 3 && month <= 5)  
            return months[1];  
        else if (month >= 6 && month <= 8)  
            return months[2];  
        else  
            return months[3]; 
    } 
    /** 
     * 判断给定日期是星期几<br> 
     * <br> 
     * @param pTime 修要判断的时间<br> 
     * @return dayForWeek 判断结果<br> 
     * @Exception 发生异常<br> 
     */  
	 public static String dayForWeek(String pTime) throws Exception {  
		  SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");  
		  Calendar c = Calendar.getInstance();  
		  c.setTime(format.parse(pTime));  
		  int dayForWeek = 0;  
		  if(c.get(Calendar.DAY_OF_WEEK) == 1){  
			  dayForWeek = 7;  
		  }else{  
			  dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;  
		  }  
		  return String.valueOf(dayForWeek);  
	 } 
	 
	 /** 
	     * 判断当前日期是星期几<br> 
	     * <br> 
	     * @param pTime 修要判断的时间<br> 
	     * @return dayForWeek 判断结果<br> 
	     * @Exception 发生异常<br> 
	     */  
		 public static String todayForWeek() throws Exception {  
			//获取今天日期
			String today = dateToString(new Date());
			String week = DateUtil.dayForWeek(today);
			return week;  
		 } 
	 
	 /**
	 * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * @param nowTime
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	 public static boolean isEffectiveDate(String startTime, String endTime) {
		 String today = dateToString(new Date());
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	     
	     Date nowDate = null;
	     Date startDate = null;
	     Date endDate = null;
		 try {
			 nowDate = df.parse(today);
			 startDate = df.parse(startTime);
			 endDate = df.parse(endTime);
		 } catch (ParseException e) {
			 e.printStackTrace();
		 }
	     
         if (nowDate.getTime() == startDate.getTime()
                 || nowDate.getTime() == endDate.getTime()) {
             return true;
         }

         Calendar date = Calendar.getInstance();
         date.setTime(nowDate);

         Calendar begin = Calendar.getInstance();
         begin.setTime(startDate);

         Calendar end = Calendar.getInstance();
         end.setTime(endDate);

         if (date.after(begin) && date.before(end)) {
             return true;
         } else {
             return false;
         }
	 }
	 
	 
	 /**
		 * 判断时间是否在[startTime, endTime]区间，注意时间格式要一致
		 * @param nowTime
		 * @param startTime
		 * @param endTime
		 * @return
		 */
		 public static boolean isEffectiveDate2(Date startDate, Date endDate) {
			 String today = dateToString(new Date());
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		     
		     Date nowDate = null;
			 try {
				 nowDate = df.parse(today);
			 } catch (ParseException e) {
				 e.printStackTrace();
			 }
		     
	         if (nowDate.getTime() == startDate.getTime()
	                 || nowDate.getTime() == endDate.getTime()) {
	             return true;
	         }

	         Calendar date = Calendar.getInstance();
	         date.setTime(nowDate);

	         Calendar begin = Calendar.getInstance();
	         begin.setTime(startDate);

	         Calendar end = Calendar.getInstance();
	         end.setTime(endDate);

	         if (date.after(begin) && date.before(end)) {
	             return true;
	         } else {
	             return false;
	         }
		 }
	 
	 /**
		 * @throws ParseException 
		 * 
		    * @Title: excutetime
		    * @Description: 格式08:00-12:00 处理成 cron  格式为 "00 00 08 10 07 2018"
		    * @param @param time
		    * @param @return    参数
		    * @return String    返回类型
		    * @throws
		 */
		@SuppressWarnings("deprecation")
		public static String excutetime (String time) throws ParseException{
			String today = DateUtil.dateToString(new Date());
	        StringBuilder sb = new StringBuilder();
			if(time != null && !time.equals("")){
				//String startTime = time.split("-")[0];
				int startMinute = 0;
				int startHour = 0;
				Date nowTime = new Date();
		        //如果想比较日期则写成"yyyy-MM-dd"就可以了  
		        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        //将字符串形式的时间转化为Date类型的时间  
		        Date startTime=sdf.parse(today+ " "+time.split("-")[0]);
		        Date endTime=sdf.parse(today+ " "+time.split("-")[1]);
		        //Date类的一个方法，如果a早于b返回true，否则返回false  
		        if(nowTime.before(startTime)) {
		        	startMinute = startTime.getMinutes();
		        	startHour = startTime.getHours();
		        } else if (nowTime.before(endTime) && nowTime.after(startTime)) {
		        	//当前时间 1分钟后执行
		        	Date excutetime = new Date(nowTime.getTime() + 60000);
		        	startMinute = excutetime.getMinutes();
		        	startHour = excutetime.getHours();
				}else{
					return "";
				}
				sb.append("0").append(" ");
				sb.append(startMinute).append(" ");
				sb.append(startHour).append(" ");
				sb.append(today.split("-")[2]).append(" ");
				sb.append(today.split("-")[1]).append(" ");
				sb.append("?").append(" ");
				sb.append(today.split("-")[0]);
			}
			return sb.toString();
		}
		
		public static void main(String[] args) throws ParseException {
			String cron = excutetime("15:00-18:00");
			System.out.println("result:"+cron);
	  }
		
		/**
		 * @throws ParseException 
		 * 
		    * @Title: excutetime
		    * @Description: 格式08:00-12:00 处理成 cron  格式为 "00 00 08 10 07 2018"
		    * @param @param time
		    * @param @return    参数
		    * @return String    返回类型
		    * @throws
		 */
		@SuppressWarnings("deprecation")
		public static String excutetime2 (String time) throws ParseException{
			String today = DateUtil.dateToString(new Date());
	        StringBuilder sb = new StringBuilder();
			if(time != null && !time.equals("")){
				//String startTime = time.split("-")[0];
				int startMinute = 0;
				int startHour = 0;
				Date nowTime = new Date();
		        //如果想比较日期则写成"yyyy-MM-dd"就可以了  
		        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        //将字符串形式的时间转化为Date类型的时间  
		        Date startTime=sdf.parse(today+ " "+time.split("-")[0]);
		        //Date类的一个方法，如果a早于b返回true，否则返回false  
		        if(nowTime.before(startTime)) {
		        	startMinute = startTime.getMinutes();
		        	startHour = startTime.getHours();
		        } else{
					return "";
				}
				sb.append("0").append(" ");
				sb.append(startMinute).append(" ");
				sb.append(startHour).append(" ");
				sb.append(today.split("-")[2]).append(" ");
				sb.append(today.split("-")[1]).append(" ");
				sb.append("?").append(" ");
				sb.append(today.split("-")[0]);
			}
			return sb.toString();
		}
		public static Date getLastDay(Date date) {  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);  
	        calendar.add(Calendar.DAY_OF_MONTH, -1);  
	        date = calendar.getTime();  
	        return date;  
	    } 
		
		public static String getLastDayString() {  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(new Date());  
	        calendar.add(Calendar.DAY_OF_MONTH, -1);  
	        Date date = calendar.getTime();  
	        String lastDay = DateUtil.dateToString(date);
	        return lastDay;  
	    }
		
		//time1 小于 time2 true
		public static boolean compare(String time1,String time2) throws ParseException{
			if(time1 == null || time1.equals("")){
				return true;
			}
			//如果想比较日期则写成"yyyy-MM-dd"就可以了  
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			////将字符串形式的时间转化为Date类型的时间  
			Date a=sdf.parse(time1);
			Date b=sdf.parse(time2);
			////Date类的一个方法，如果a早于b返回true，否则返回false
			if(a.before(b))
				return true;
			else 
				return false;  
		}	

}
