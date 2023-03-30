package com.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 此类专门用于操作Date<br/>
 * 如果要对日期进行操作，请调用此类的方法
 * </p>
 * @author myth
 */
public final class DateUtils {

	/**
	 * 得到当前日期的字符串格式
	 * @return
	 */
	public static final String getNowDateTimeStr() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date());

	}

	/**
	 * 得到当前日期的字符串格式
	 * @return
	 */
	public static final String getNowDateTimeStr3() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
		return formatter.format(new Date());

	}

	/**
	 * 得到当前日期的字符串格式
	 * by 叶乔
	 * @return
	 */
	public static final String getNowDateTimeStr2() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date());

	}

	/**
	 * 将String类型转为Calendar
	 * by 叶乔
	 * @return
	 */
	public static Calendar getDateTimeStr(String dateStr){
		return parseDate2Calendar(parseString2Date(dateStr,"yyyy-MM-dd HH:mm:ss"));
	}

	public static Calendar getDateTimeStr(String dateStr,String pattern){
		return parseDate2Calendar(parseString2Date(dateStr,pattern));
	}

	/**
	 * 将String类型转为Calendar
	 * by 叶乔
	 * @return
	 */
	public static Calendar getDateTimeStrs(String dateStr){
		return parseDate2Calendar(parseString2Date(dateStr,"yyyy-MM-dd"));
	}


	/**
	 * 判断传入的字符串是否是合法的日期格式
	 * @param str 日期格式的字符串
	 * @param pattern 格式化类型
	 * @return 布尔
	 */
	public static boolean isLegalDate(String str,String pattern){
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			format.parse(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将字符串转化为{@link Date}类型
	 * @param str 要转化的字符串
	 * @param pattern 格式化类型
	 * @return {@link Date}
	 */
	public static Date parseString2Date(String str,String pattern){
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 转化{@link Date}为{@link Calendar}
	 * @param date {@link Date}类型
	 * @return {@link Calendar}
	 */
	public static Calendar parseDate2Calendar(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 时间戳转String 时间
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String timet(Long time,String pattern) {
        SimpleDateFormat sdr = new SimpleDateFormat(pattern);
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;
	}

	/**
	 * 将{@link Calendar}转化为{@link Date}类型
	 * @param calendar {@link Calendar}类型
	 * @return {@link Date}类型
	 */
	public static Date parseCalendar2Date(Calendar calendar){
		return calendar.getTime();
	}

	public static String parseCalendar2String(Calendar calendar,String pattern){
		return parseDate2String(parseCalendar2Date(calendar), pattern);
	}

	/**
	 * 将{@link Date}类型转化为字符串
	 * @param date 日期类型
	 * @param pattern 格式化类型
	 * @return 字符串
	 */
	public static String parseDate2String(Date date,String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 格式化Calendar<br/>转化成响应的字符串，<br/>
	 * 例如：5分前、1小时前、1天前,<br/>
	 * 1小时内显示多少分前，24小时内显示多少小时前，5天内显示多少天前，5天后显示日期
	 * @return
	 */
	public static String formatTime(long time){
		long nowTime = System.currentTimeMillis();
		long interval = nowTime - time;
		long hours = 3600 * 1000;
		long days = hours * 24;
		long five_days = days * 5;
		if(interval < hours){
			long minute = interval / 1000 / 60;
			if(minute == 0){
				return "刚刚";
			}
			return minute + "分钟前";
		}else if(interval < days){
			return interval / 1000 / 3600 + "小时前";
		}else if(interval < five_days){
			return interval / 1000 / 3600 / 24 + "天前";
		}else{
			Date date = new Date(time);
			return parseDate2String(date, "yyyy-MM-dd HH:mm:ss");
		}
	}

	public static String subDate(Date v1,Date v2){
	      /**
	       * 日期相减  返回格式为 小时:分钟 格式的字符串
	       */
	      long daterange = v1.getTime() - v2.getTime();
	      long time = 1000*60; //A day in milliseconds
	      long minute = daterange/time;
	      String result = "";
	      if(minute>0){
	    	  result = minute/60 +":"+minute%60;
	      }else{
	    	  result = "00:00";
	      }
	      return result;
	}

	/**
	 * 计算两个时间相差的秒数
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Long subDate(Calendar v1,Calendar v2){
		Long second = 0l;
		Long v1Second = v1.getTime().getTime()/1000;
		Long v2Second = v2.getTime().getTime()/1000;
		second = v1Second - v2Second;
		return second;
	}

	/**
	 *
	 * @param v1 时间
	 * @param v2 提前 或者 减少的毫秒数
	 * @return
	 */
	public static String subDate(Calendar v1,long v2){
		long sub = v1.getTimeInMillis()+v2;
		Date date=new Date(sub);
		return parseDate2String(date, "yyyyMMddHHmmss");
	}

	/**
	 * 如果 v1 比 v2 时间大 则返回true 否则 返回false
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Boolean maxTime(Calendar v1,Calendar v2){
		if(v1.getTimeInMillis() > v2.getTimeInMillis()){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 计算两个时间差的天数
	 * @param start
	 * @param end
	 * @return
	 */
	public static int differentDaysByMillisecond(Date start,Date end){
		int days = (int) ((end.getTime() - start.getTime()) / (1000*3600*24));
		return days;
	}

	/**
	 * 获取本周开始时间
	 * @return
	 */
	public static String getBeginDayOfWeek() {
		Date date = new Date();
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return DateUtils.parseCalendar2String(cal,Constants.DATA_MEET);
	}






	/**
	 * 通过时间戳得到秒数
	 * @param v1
	 * @return
	 */
	public static Double timeSub(Long v1){
		return v1/1000.00;
	}

	private DateUtils(){
		throw new AssertionError();
	}

	public static final String getNowDateTimeStr(String patten) {
		SimpleDateFormat formatter = new SimpleDateFormat(patten);
		return formatter.format(new Date());

	}

	public static String getTime(Long secs)throws Exception{
		String times = "00:00:00";
		if(secs != null && secs > 0){
			Long second = 0l;
			Long minute = 0l;
			Long hour = 0l;
			second = secs%60;
			minute = secs/(60);
			minute = minute%60;
			hour = secs/(60*60);
			String secondStr = second+"";
			String minuteStr = minute+"";
			String hourStr = hour+"";
			if(second < 10){
				secondStr = "0"+secondStr;
			}
			if(minute < 10){
				minuteStr = "0"+minute;
			}
			if(hour < 10){
				hourStr = "0"+hour;
			}
			if(second > 0 ){
				times = "00:00:"+secondStr+"";
			}
			if(minute > 0){
				times = "00:"+minuteStr+":"+secondStr;
			}
			if(hour > 0){
				times = hourStr +":"+minuteStr+":"+secondStr;
			}
		}
		return times;
	}

	/**
	 * 获取时间描述 中文
	 * @param secs
	 * @return
	 * @throws Exception
	 */
	public static String getTimeDesc(Long secs)throws Exception{
		String times = "";
		Long second = 0l;
		Long minute = 0l;
		Long hour = 0l;
		second = secs%60;
		minute = secs/(60);
		minute = minute%60;
		hour = secs/(60*60);
		if(second > 0 ){
			times = "耗时"+second+"秒";
		}
		if(minute > 0){
			times = "耗时"+minute+"分"+second+"秒";
		}
		if(hour > 0){
			times = "耗时"+hour+"小时"+minute+"分"+second+"秒";
		}
		return times;
	}

	/**
	 * 处理开始时间 结束时间
	 * @param time
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static String getHandleTime(String time,Integer type){
		if(StringUtils.isNotBlank(time)){
			if(1 == type){
				time = time +" 00:00:00";
			}else if(2 == type){
				time = time +" 23:59:59";
			}
		}
		return time;
	}

	public static void main(String [] args){
		try {
			int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			System.out.println(month);
			String result = DateUtils.getMonthLastDay(2019,2)+"";
			System.out.println(result);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 取得当月天数
	 * */
	public static int getCurrentMonthLastDay()
	{
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 得到指定月的天数
	 * */
	public static int getMonthLastDay(int year, int month)throws Exception{
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取一年内的年份加月份
	 * @return
	 */
	public static List<String> getYearMonth(Integer startYear, Integer startMonth)throws Exception{
		List<String> yearMonth = new LinkedList<String>();
		for(int i = 0; i< 12 ; i++){
			if(12 < startMonth){
				startMonth = 1;
				startYear++;
			}
			if(startMonth < 10){
				yearMonth.add(startYear+"-0"+startMonth);
			}else{
				yearMonth.add(startYear+"-"+startMonth);
			}

			++startMonth;

		}
		return yearMonth;
	}

	/**
	 * 获取0时区时间
	 * @return
	 * @throws Exception
	 */
	public static Date getNowZone()throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//设置为东八区
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date();
		String dateStr = sdf.format(date);

		System.out.println("dateStr -> "+dateStr);
		//将字符串转成时间
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate=null;
		try {
			newDate = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	/**
	 * 获取0时区时间
	 * @return
	 * @throws Exception
	 */
	public static Date getTimeZone(String timeStr,String pattern)throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//设置为东八区
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = parseString2Date(timeStr,pattern);
		String dateStr = sdf.format(date);

		System.out.println("dateStr -> "+dateStr);
		//将字符串转成时间
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate=null;
		try {
			newDate = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

}
