package com.evisible.os.controlcenter.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConvert {

	/**
	 * 日期转化, Date --> String
	 * 
	 * @param date
	 * @param formate
	 * @return
	 */
	public static String date2Str(Date date, String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.format(date);
	}

	/**
	 * 日期转化, String --> Date
	 * 
	 * @param date
	 * @param formate
	 * @return
	 */
	public static Date str2Date(String date, String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 指定日期转化, Date --> String
	 * 获取前一天
	 * @param date
	 * @param formate
	 * @return
	 */
	public static String date2StrForDay(Date date, String formate ,int offset) {
		Date as = new Date(date.getTime()+offset*24*60*60*1000);
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.format(as);
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(DateConvert.date2StrForDay(new Date(), "yyyyMMdd", 30));
	}
}