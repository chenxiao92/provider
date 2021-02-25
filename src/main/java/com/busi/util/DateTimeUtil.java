/*
 * Copyright 2003 博升优势公司(Broadadv, Inc.) All rights reserved.
 * Use is subject to license terms.
 */

/*
 * @(#)DateTimeUtil.java	1.00 03/03/21
 */
package com.busi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class DateTimeUtil{

	public static long currentDateTime() {

		return System.currentTimeMillis();
	}

	public static String getDateString() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd"); // "yyyyMMdd G
		return fm.format(new Date());
	}
	
	public static String getCnDateString() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy年MM月dd日"); // "yyyyMMdd G
		return fm.format(new Date());
	}
	
	public static String getCnTimeString() {
		SimpleDateFormat fm = new SimpleDateFormat("HH时mm分ss秒"); // "yyyyMMdd G
		return fm.format(new Date());
	}
	
	public static String getTimeString() {
		SimpleDateFormat fm = new SimpleDateFormat("HHmmss"); // "yyyyMMdd G
		return fm.format(new Date());
	}
	
	public static String getDateString(long dt) {

		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd"); // "yyyyMMdd G
		// 'at' hh:mm:ss
		// a zzz"
		return fm.format(new Date(dt));
	}

	public static String getTimeString(long dt) {

		SimpleDateFormat fm = new SimpleDateFormat("HHmmss"); // "yyyyMMdd G
		// 'at' hh:mm:ss
		// a zzz"
		return fm.format(new Date(dt));
	}

	public static String getDateTimeString(long dt) {

		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss"); // "yyyyMMdd
		// G
		// 'at'
		// hh:mm:ss
		// a
		// zzz"
		return fm.format(new Date(dt));
	}
	
	public static String getDateTimeString() {

		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss"); // "yyyyMMdd
		// G
		// 'at'
		// hh:mm:ss
		// a
		// zzz"
		return fm.format(new Date());
	}

	/**
	 * 获取当前日期到毫秒
	 * @return
	 */
	public static String getDateTimeMilliSecondString() {
		String orderNum = "";
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		orderNum = sFormat.format(new Date());
		return orderNum;
	}
	/**
	 * 根据指定日期得到过期日期
	 * @param d                yyyyMMdd格式的日期
	 * @param period           有效期数量
	 * @param periodUnit       有效期类型：0：年；1：月；2：日；3：时；4：分
	 * @return
	 * @throws Exception
	 */
	public static Date getExpireDate(String d, String period, String periodUnit) throws Exception{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(d);
		} catch (ParseException e){
			e.printStackTrace();
			throw new Exception("日期格式转换出现异常！请核实日期格式：" + d, e);
		}
		cal.setTime(date);
		try {
			if ("0".equals(periodUnit)){
				System.out.println("0");
				cal.add(Calendar.YEAR, Integer.parseInt(period));
			} else if ("1".equals(periodUnit)){
				System.out.println("1");
				cal.add(Calendar.MONTH, Integer.parseInt(period));
			} else if ("2".equals(periodUnit)){
				System.out.println("2");
				cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(period));
			} else if ("3".equals(periodUnit)){
				System.out.println("3");
				cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(period));
			} else if ("4".equals(periodUnit)){
				System.out.println("4");
				cal.add(Calendar.MINUTE, Integer.parseInt(period));
			}
		} catch (NumberFormatException e1){
			e1.printStackTrace();
			throw new Exception("PERIOD参数不是表示数字的字符串，请检查！", e1);
		}
		return new Date(cal.getTimeInMillis());
	}
	
	/**
	 * 根据指定日期得到过期日期
	 * @param date             Date对象
	 * @param period           有效期数量
	 * @param periodUnit       有效期类型：0：年；1：月；2：日；3：时；4：分
	 * @return
	 * @throws Exception
	 */
	public static Date getExpireDate(Date date, String period, String periodUnit) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		try {
			if ("0".equals(periodUnit)){
				System.out.println("0");
				cal.add(Calendar.YEAR, Integer.parseInt(period));
			} else if ("1".equals(periodUnit)){
				System.out.println("1");
				cal.add(Calendar.MONTH, Integer.parseInt(period));
			} else if ("2".equals(periodUnit)){
				System.out.println("2");
				cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(period));
			} else if ("3".equals(periodUnit)){
				System.out.println("3");
				cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(period));
			} else if ("4".equals(periodUnit)){
				System.out.println("4");
				cal.add(Calendar.MINUTE, Integer.parseInt(period));
			}
		} catch (NumberFormatException e){
			e.printStackTrace();
			throw new Exception("PERIOD参数不是表示数字的字符串，请检查！", e);
		}
		return new Date(cal.getTimeInMillis());
	}
	
	public static Date getExpireDate(String period, String periodUnit) throws Exception{
		Date d = new Date();
		return getExpireDate(d, period, periodUnit);
	}

	public static long getSpNextMonth(long dt) {

		Date d = new Date(dt);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int curDay = cal.get(Calendar.DAY_OF_MONTH);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(curDay == lastDay) { // 是月末，返回下月月末
			cal.add(Calendar.MONTH, 1);
			lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
		} else { // 增加一月
			cal.add(Calendar.MONTH, 1);
		}
		return cal.getTimeInMillis();
	}

	// 2005-11-03 屏蔽
	// 2006-05-31 取消屏蔽 孙晓时
	public static long getNextMonth(long dt, int monHow) {

		Date d = new Date(dt);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int curDay = cal.get(Calendar.DAY_OF_MONTH);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(curDay == lastDay) { // 是月末，返回下月月末
			cal.add(Calendar.MONTH, monHow);
			lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
		} else { // 增加一月
			cal.add(Calendar.MONTH, monHow);
		}
		return cal.getTimeInMillis();
	}

	public static long getSpNextMonth(long firstdt, long lastdt) {

		Date d = new Date(firstdt);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int firstDay = cal.get(Calendar.DAY_OF_MONTH); // 取得首次定制的日期
		d = new Date(lastdt);
		cal.setTime(d);
		cal.add(Calendar.MONTH, 1); // 上次定制的月份 + 1 月
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(firstDay >= maxDay) {
			cal.set(Calendar.DAY_OF_MONTH, maxDay);
		} else {
			cal.set(Calendar.DAY_OF_MONTH, firstDay);
		}
		return cal.getTimeInMillis();
	}
	
	/**
	 * 获得特定日期当月第一天的日期字符串
	 * @param date 日期
	 * @param regex 格式,如：yyyyMMdd
	 * @return
	 */
	public static String getFirstDayOfMonth(String date,String regex){
		if(date == null|| regex == null || "".equals(regex)) return "";
		
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(regex);
		Calendar now = Calendar.getInstance();
		try {
			now.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(date+"--不是有效的日期字符串",e);
		}
		now.set(Calendar.DATE, 1);
		
		result = sdf.format(now.getTime());
		return result;
	}
	
	/**
	 * 获得特定日期当月最后一天的日期字符串
	 * @param date 日期
	 * @param regex 格式,如：yyyyMMdd
	 * @return
	 */
	public static String getEndDayOfMonth(String date,String regex){
		if(date == null|| regex == null || "".equals(regex)) return "";
		
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(regex);
		Calendar now = Calendar.getInstance();
		try {
			now.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(date+"--不是有效的日期字符串",e);
		}
		now.set(Calendar.DATE, 1);
		now.add(Calendar.MONTH, 1);
		now.add(Calendar.DATE, -1);
		
		result = sdf.format(now.getTime());
		return result;
	}		
}