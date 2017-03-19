package com.lostad.applib.util;

import java.text.*;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期时间辅助类  月份从1开始
 * @author Hocean  @version 2016年8月24日 下午4:45:58.
 *
 */
public class DateTime extends GregorianCalendar {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static DateTime dateTimeBase = new DateTime().setTimeMillis(0);

	public void setFORMAT_DEFUALT(String fORMAT_DEFUALT)
	{
		FORMAT_DEFUALT = fORMAT_DEFUALT;
	}

	public String getFORMAT_DEFUALT()
	{
		return FORMAT_DEFUALT;
	}  //0毫秒时候的日期

	public static void main(String[] args) {
		DateTime dt = new DateTime();
		System.out.println("转换为Date： " + dt.toDate().toString()); //转换为Date
		System.out.println("一般格式： " + dt.format()); //一般格式输出
		System.out.println("分别輸出： " + String.format("%s_%s_%s %s:%s:%s  %s", dt.getYear(), dt.getMonth(), dt.getDate(), dt.getHours(), dt.getMinutes(), dt.getSeconds(), dt.getWeek()));//获取
		dt.setYear(2017);
		dt.setMonth(7);
		dt.setDate(7);
		dt.setHours(7);
		dt.setMinutes(7);
		dt.setSeconds(7);
		System.out.println("分別设置2017/7/7/7/7： " + dt.format()); //一般格式输出
		System.out.println("归零0： " + dt.setTimeMillis(0).format()); //一般格式输出
		dt.parse("2018-10-3 3:6:3"); //反序列化
		System.out.println("反序列化2018-10-3 3:6:3： " + dt.format()); //一般格式输出
		dt.parse("2018-10-3 3:6:3:123", "yyyy-MM-dd HH:mm:ss:SSS"); //自定义 反序列化
		System.out.println("反序列化2018-10-3 3:6:3:123： " + dt.format("yyyy-MM-dd HH:mm:ss:SSS")); //格式输出
		dt.parse("2016-6-6 6:6:6"); //反序列化
		dt.addDate(1);//计算
		dt.addYear(1);
		dt.addMonth(1);
		dt.addHours(1);
		dt.addMinutes(1);
		dt.addSeconds(1);
		dt.addTimeMillis(1000 * 60 * 60 * 24 * 10);
		System.out.println("增加时间2017/7/17 7/7/7： " + dt.format());//一般格式输出
		System.out.println("自定义格式： " + dt.format("yyyy=MM=dd hh&mm&ss E"));//格式输出
		System.out.println("自定义获取： " + "今天是 " +  dt.get(DateTime.DATE) + "号， 星期" + dt.getWeek());//获取特殊时间

		DateTime minus = new DateTime();
		DateTime minus2 = minus.clon();
		minus = DateTime.subtract(minus.addMinutes(70), minus2);
		System.out.println("计算相差时间1:10:00： " + minus.getCountSeconds());
		System.out.println("输出相差的时间： " + String.format("s-s-%s %s:%s:%s", minus.getDate(), minus.getHours(), minus.getMinutes(), minus.getSeconds()));
		System.out.println("输出相差的时间分别计算： " + String.format("s-s-%s %s:%s:%s", minus.getCountDate(), minus.getCountHours(), minus.getCountMinutes(), minus.getCountSeconds()));

		System.out.println("创建2020-6-6 13:06:15： " + DateTime.create("2020-6-6 13:06:15").format());
		System.out.println("创建2020-6-6： " + DateTime.createDate("2020-6-6").format());
		System.out.println("创建13:06:15： " + DateTime.createTime("13:06:15").format());
		System.out.println("创建--------： " + DateTime.createTime("13:06:15").getHours());
		System.out.println("创建2020-6-6 13:06:15： " + new DateTime("2020-6-6 13:06:15").format());
		System.out.println("创建当前： " + new DateTime(new Date()).format());
		System.out.println("创建1000ms： " + new DateTime(1000).format());

	}//*/

	public final static String FORMAT_TAG_YEAR = "yyyy";

	public final static String FORMAT_BASE = "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_TIME = "HH:mm:ss";

	private final static int hourToDate = 24;
	private final static int minuteToHour = 60;
	private final static int SecondToMinute = 60;
	private final static int millisToSecond = 1000;

	private String FORMAT_DEFUALT = null;

	public DateTime() {
		super();
	}

	public DateTime(TimeZone zone) {
		super(zone);
	}

	public DateTime(Locale aLocale) {
		super(aLocale);
	}

	public DateTime(TimeZone zone, Locale aLocale) {
		super(zone, aLocale);
	}
	public DateTime(String time) {
		this(time, FORMAT_BASE);
	}
	public DateTime(String time, String format) {
		//this(new SimpleDateFormat(format).parse(time));
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(time);
			setTime(date);
		}
		catch (Exception e) {
		}
	}
	public DateTime(Date date) {
		setTime(date);
	}
	public DateTime(long millis) {
		setTimeMillis(millis);
	}

	public Date toDate(){
		return getTime();
	}
	public void parseDate(Date date){
		setTime(date);
	}

	//获取
	public int getYear() {
		int year = get(GregorianCalendar.YEAR);//年
		return year;
	}

	public int getMonth() {
		int month = get(GregorianCalendar.MONTH);//月
		return month + 1;
	}

	public int getDate() {
		int date = get(GregorianCalendar.DATE);//日
		return date;
	}

	public int getHours() {
		int hour = get(GregorianCalendar.HOUR_OF_DAY);//时
		return hour;
	}

	public int getMinutes() {
		int minute = get(GregorianCalendar.MINUTE);//分
		return minute;
	}

	public int getSeconds() {
		int second = get(GregorianCalendar.SECOND);//秒
		return second;
	}

	public int getMillis(){
		int millis = get(GregorianCalendar.MILLISECOND);//秒
		return millis;
	}
	public long getTimeMillis() {
		return super.getTimeInMillis();
	}

	public int getWeek() {
		int week = get(GregorianCalendar.DAY_OF_WEEK); //星期
		return week - 1;
	}

	//相减使用
	public double getCountDate() {
		return getCountHours() / (float) hourToDate;
	}

	public double getCountHours() {
		return getCountMinutes() / (float) minuteToHour;
	}

	public double getCountMinutes() {
		return getCountSeconds() / (float) SecondToMinute;
	}

	public double getCountSeconds() {
		return getCountMillis() / (float) millisToSecond;
	}
	public double getCountMillis() {
		DateTime dt = new DateTime();
		dt.setTime(getTime());
		dt.addYear(1970);
		dt.addHours(8);
		return dt.getTimeMillis();
	}

	//设置
	public DateTime setYear(int year) {
		set(GregorianCalendar.YEAR, year);
		return this;
	}

	public DateTime setMonth(int month) {
		set(GregorianCalendar.MONTH, month - 1);
		return this;
	}

	public DateTime setDate(int day) {
		set(GregorianCalendar.DATE, day);
		return this;
	}

	public DateTime setHours(int hour) {
		set(GregorianCalendar.HOUR_OF_DAY, hour);
		return this;
	}

	public DateTime setMinutes(int minute) {
		set(GregorianCalendar.MINUTE, minute);
		return this;
	}

	public DateTime setSeconds(int second) {
		set(GregorianCalendar.SECOND, second);
		return this;
	}

	public DateTime setMillis(int millis) {
		set(GregorianCalendar.MILLISECOND, millis);
		return this;
	}

	public DateTime setTimeMillis(long millis) {
		super.setTimeInMillis(millis);
		return this;
	}

	//计算
	public DateTime addYear(int year) {
		add(GregorianCalendar.YEAR, year);
		return this;
	}

	public DateTime addMonth(int month) {
		add(GregorianCalendar.MONTH, month);
		return this;
	}

	public DateTime addDate(int day) {
		add(GregorianCalendar.HOUR_OF_DAY, day);
		return this;
	}

	public DateTime addHours(int hour) {
		add(GregorianCalendar.HOUR_OF_DAY, hour);
		return this;
	}

	public DateTime addMinutes(int minute) {
		add(GregorianCalendar.MINUTE, minute);
		return this;
	}

	public DateTime addSeconds(int second) {
		add(GregorianCalendar.SECOND, second);
		return this;
	}

	public DateTime addMillis(int millis) {
		add(GregorianCalendar.MILLISECOND, millis);
		return this;
	}

	public DateTime addTimeMillis(long time) {
		long vltime = getTimeMillis();
		vltime += time;
		setTimeMillis(vltime);
		return this;
	}

	//序列化
	public String format() {
		return format(FORMAT_DEFUALT == null ? FORMAT_BASE : FORMAT_DEFUALT);
	}
	public String formatDate() {
		return format(FORMAT_DATE);
	}
	public String formatTime() {
		return format(FORMAT_TIME);
	}

	public String format(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(getTime());
	}


	//反序列化
	public DateTime parse(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try {
			Date d = sdf.parse(time);
			setTime(d);
			return this;
		}
		catch (ParseException e) {
		}
		return null;

	}

	public DateTime parse(String time) {
		return parse(time, FORMAT_DEFUALT == null ? FORMAT_BASE : FORMAT_DEFUALT);
	}
	public DateTime parseDate(String time) {
		return parse(time, FORMAT_DATE);
	}
	public DateTime parseTime(String time) {
		return parse(time, FORMAT_TIME);
	}

	//创建
	public static DateTime create(String time, String format) {
		return new DateTime(time,format);
	}
	public static DateTime create(String time) {
		return create(time, FORMAT_BASE);
	}
	public static DateTime createDate(String time) {
		return create(time, FORMAT_DATE);
	}
	public static DateTime createTime(String time) {
		return create(time, FORMAT_TIME);
	}

	/**
	 * 相减 后再减去8h 从 1970-01-01 00:00:00  开始
	 * 可获得 相差的 天数 小时 分钟 秒 毫秒
	 * 通过  *Count 获取合计天数 小时 分钟 秒 毫秒
	 * @param dtl
	 * @param dtr
	 * @return
	 */
	public static DateTime subtract(DateTime dtl, DateTime dtr) {
		DateTime minus = new DateTime();
		minus.setTimeMillis(dtl.getTimeMillis() - dtr.getTimeMillis());
		minus.addYear(-1970);
		minus.addHours(-8);
		return minus;
	}

	public DateTime subtract(DateTime dt) {
		DateTime minus = new DateTime();
		minus.setTimeMillis(getTimeMillis() - dt.getTimeMillis());
		return minus;
	}
	/**
	 * 克隆元素
	 * @return
	 */
	public DateTime clon() {
		DateTime dt = new DateTime(getTimeZone());
		dt.setTimeMillis(getTimeMillis());
		return dt;
	}

}
