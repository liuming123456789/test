package com.liuming.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class DateTimeUtil {
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat df2 = new SimpleDateFormat("yyMMdd");
	private static DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Calendar c;

	/**
	 * 得到minute 分钟后的时间,以HH:mm:ss格式的字符串返回
	 * 
	 * @return 以 HH:mm:ss 格式的字符串返回
	 */
	public static String getHMS(int minute) {
		java.util.Date d = new java.util.Date(System.currentTimeMillis() + minute * 60 * 1000);
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(d);
	}

	/**
	 * 获取今天相差N天的日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getDate(int days) {
		c = Calendar.getInstance();
		c.add(GregorianCalendar.HOUR_OF_DAY, 24 * days);
		return df.format(c.getTime());
	}

	/**
	 * 得到sec 秒后的时间,以yyyy-MM-dd HH:mm:ss 格式的字符串返回
	 * 
	 * @return 以 HH:mm:ss 格式的字符串返回
	 */
	public static String getAfterTimeOfSecond(int sec) {
		java.util.Date d = new java.util.Date(System.currentTimeMillis() + sec * 1000);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone zone = new SimpleTimeZone(28800000, "Asia/Shanghai");
		df.setTimeZone(zone);
		return df.format(d);
	}

	/**
	 * Get current time , return format 'yyyy-MM-dd hh:mm:ss'
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone zone = new SimpleTimeZone(28800000, "Asia/Shanghai");
		df.setTimeZone(zone);
		return df.format(c.getTime());
	}

	// Get current time , return format 'yyyy-MM-dd'
	public static String getCurrentDate1() {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone zone = new SimpleTimeZone(28800000, "Asia/Shanghai");
		df.setTimeZone(zone);
		return df.format(c.getTime());
	}

	/**
	 * 返回当前日期,格式 yyMMdd
	 * 
	 * @return
	 */
	public static String getCurrentDateyymmdd() {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyMMdd");
		TimeZone zone = new SimpleTimeZone(28800000, "Asia/Shanghai");
		df.setTimeZone(zone);
		return df.format(c.getTime());
	}

	// Get today's date, return format 'yyyy-MM-dd'
	public static String getTodayDate() {
		c = Calendar.getInstance();
		return df.format(c.getTime());
	}

	// Get today's date, return format 'yyMMdd'
	public static String getTodayDate2() {
		c = Calendar.getInstance();
		return df2.format(c.getTime());
	}

	// Get today's date, return format 'yyyy-MM-dd HH:mm:ss'
	public static String getTodayDate3() {
		c = Calendar.getInstance();
		return df3.format(c.getTime());
	}

	public static String df3Format(Date d) {
		return df3.format(d);
	}

	// Get yesterday's date, return format 'yyyy-MM-dd'
	public static String getYesterdayDate() {
		c = Calendar.getInstance();
		c.add(GregorianCalendar.HOUR_OF_DAY, -24);
		return df.format(c.getTime());
	}

	// For nearly 7 days of the start date and end date, return format
	// 'yyyy-MM-dd'
	public static String[] getServenDate() {

		c = Calendar.getInstance();
		String[] s = new String[2];
		c.add(GregorianCalendar.HOUR_OF_DAY, -6 * 24);
		s[0] = df.format(c.getTime());

		c = Calendar.getInstance();
		s[1] = df.format(c.getTime());
		return s;
	}

	// For nearly 3 months of the start date and end date, return format
	// 'yyyy-MM-dd'
	public static String[] getThreeMonthDate() {

		c = Calendar.getInstance();
		String[] s = new String[2];
		c.add(GregorianCalendar.HOUR_OF_DAY, -90 * 24);
		s[0] = df.format(c.getTime());

		c = Calendar.getInstance();
		s[1] = df.format(c.getTime());
		return s;
	}

	// For nearly 1 months of the start date and end date, return format
	// 'yyyy-MM-dd'
	public static String[] getOneMonthDate() {

		c = Calendar.getInstance();
		String[] s = new String[2];
		c.add(GregorianCalendar.HOUR_OF_DAY, -30 * 24);
		s[0] = df.format(c.getTime());

		c = Calendar.getInstance();
		s[1] = df.format(c.getTime());
		return s;
	}

	// Get last month of the start date and end date, return format 'yyyy-MM-dd'
	public static String[] getLastMonthDate() {
		c = Calendar.getInstance();
		Date d;
		c.add(Calendar.MONTH, -1);
		d = c.getTime();
		String lastmonth_thisday = df.format(d);
		String[] s = { lastmonth_thisday.substring(0, 8) + "01", getMonthLastDay(lastmonth_thisday) };
		return s;
	}

	// Access to the month of the start date and the date today, return format
	// 'yyyy-MM-dd'
	public static String[] getThisMonthDate() {
		String s = getTodayDate();
		return new String[] { s.substring(0, 8) + "01", s };

	}

	/**
	 * 得到minute 分钟后的时间,以yyyy-MM-dd HH:mm:ss 格式的字符串返回
	 * 
	 * @return 以 HH:mm:ss 格式的字符串返回
	 */
	public static String getAfterTime(int minute) {
		java.util.Date d = new java.util.Date(System.currentTimeMillis() + minute * 60 * 1000);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone zone = new SimpleTimeZone(28800000, "Asia/Shanghai");
		df.setTimeZone(zone);
		return df.format(d);
	}

	public static void main(String[] a) {
		System.out.println(getDate(0));
		System.out.println(getDate(-1));
		System.out.println(getDate(-2));
		System.out.println(getDate(-3));
		System.out.println(getDate(-4));
		System.out.println(getDate(-5));
		System.out.println(getDate(-31));

	}

	/**
	 * Get last month last day date, return format'YYYY-MM-DD'
	 */
	public static String getMonthLastDay(String special_month) {
		String latetime = null;
		int i, j;
		String starttime = special_month.substring(0, 8) + "28";
		for (i = 28, j = 1;; i++, j++) {
			latetime = getChangeDateByHour(starttime, j);
			if (Integer.parseInt(latetime.substring(8, 10)) == 1) {
				break;
			}
		}
		return special_month.substring(0, 8) + i;
	}

	public static String getChangeDateByHour(String currdate, int n) {
		String sday = null;
		try {
			Date time = df.parse(currdate);
			Date changedate = new Date(time.getTime() + n * 24 * 3600 * 1000);
			sday = df.format(changedate);
		} catch (Exception e) {
			System.out.println("error : " + e.getMessage());
		}
		return sday;
	}

	/**
	 * get date
	 * 
	 * @param queryTimeType
	 *            // 1=today,2=yesterday,3=nearly 7 days,4=last month,5=this
	 *            month,6=user-defined
	 * @param queryStartDate
	 *            // query start date
	 * @param queryEndDate
	 *            // query enddate
	 * @return
	 */
	public static String[] getSelectDate(String queryTimeType, String queryStartDate, String queryEndDate) {
		String[] res = new String[2];
		String s;
		switch (Integer.parseInt(queryTimeType)) {
		case 1: // 1=����
			s = DateTimeUtil.getTodayDate();
			res[0] = s;
			res[1] = s;
			break;
		case 2:// 2=����
			s = DateTimeUtil.getYesterdayDate();
			res[0] = s;
			res[1] = s;
			break;
		case 3:// 3=��7��
			res = DateTimeUtil.getServenDate();
			break;
		case 4: // 4=�ϸ���
			res = DateTimeUtil.getLastMonthDate();
			break;
		case 5: // 5=����
			res = DateTimeUtil.getThisMonthDate();
			break;
		default: // 6=�Զ���
			res[0] = queryStartDate;
			res[1] = queryEndDate;
			break;
		}
		return res;
	}

	/**
	 * get date
	 * 
	 * @param queryTimeType
	 *            // 1=today,2=yesterday,3=nearly 7 days,4=nearly 1
	 *            month,5=nearly 3 month,6=user-defined
	 * @param queryStartDate
	 *            // query start date
	 * @param queryEndDate
	 *            // query enddate
	 * @return
	 */
	public static String[] getSelectDate2(String queryTimeType, String queryStartDate, String queryEndDate) {
		String[] res = new String[2];
		String s;
		switch (Integer.parseInt(queryTimeType)) {
		case 1:
			s = DateTimeUtil.getTodayDate();
			res[0] = s;
			res[1] = s;
			break;
		case 2:
			s = DateTimeUtil.getYesterdayDate();
			res[0] = s;
			res[1] = s;
			break;
		case 3:
			res = DateTimeUtil.getServenDate();
			break;
		case 4:
			res = DateTimeUtil.getOneMonthDate();
			break;
		case 5:
			res = DateTimeUtil.getThreeMonthDate();
			break;
		default:
			res[0] = queryStartDate;
			res[1] = queryEndDate;
			break;
		}
		return res;
	}

	// Get today's date, return format 'yyyy-MM-dd'
	public static String parseDate(Date date) {
		return df.format(date);
	}

	// Get today's date, return format 'yyyy-MM-dd'
	public static String getLastWeek() {
		c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -7);
		return df.format(c.getTime());
	}

	// Get today's date, return format 'yyyy-MM-dd'
	public static String getLastMonth() {
		c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		return df.format(c.getTime());
	}

	public static Date formateDate(String date) throws Exception {
		return df.parse(date);
	}

	/**
	 * 2个字符串格式时间减法得到秒
	 * 
	 * @param time1
	 *            yyyy-MM-dd HH:mm:ss
	 * @param time2
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long getTimeMinusToSec(String time1, String time2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		java.util.Date now;
		try {
			now = df.parse(time1);
			java.util.Date date = df.parse(time2);

			long l = now.getTime() - date.getTime();
			return l / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
		/*
		 * long day=l/(24*60*60*1000);
		 * 
		 * long hour=(l/(60*60*1000)-day*24);
		 * 
		 * long min=((l/(60*1000))-day*24*60-hour*60);
		 * 
		 * long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		 */

		// System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
	}

	public static String getHM() {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("HH:mm");
		TimeZone zone = new SimpleTimeZone(28800000, "Asia/Shanghai");
		df.setTimeZone(zone);
		return df.format(c.getTime());
	}
	
	/**
	 * 得到minute 分钟后的时间,以yyyy-MM-dd HH:mm:ss 格式的字符串返回
	 * 
	 * @return 以 HH:mm:ss 格式的字符串返回
	 */
	public static String getAfterTime2(int minute) {
		java.util.Date d = new java.util.Date(System.currentTimeMillis() + minute * 60 * 1000);
		DateFormat df = new SimpleDateFormat("yyMMddHHmm");
		TimeZone zone = new SimpleTimeZone(28800000, "Asia/Shanghai");
		df.setTimeZone(zone);
		return df.format(d);
	}

}
