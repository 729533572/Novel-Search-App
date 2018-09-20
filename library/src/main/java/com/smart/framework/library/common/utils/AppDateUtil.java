package com.smart.framework.library.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 客户端日期工具类 提供常用时间日期处理
 *
 * @author joe
 * @version 2015.03.26
 */
public class AppDateUtil {

    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 日期格式：yyyyMMdd
     */
    public static final String YYYYMMDD = "yyyyMMdd";
    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 日期格式：yy-MM-dd
     */
    public static final String YY_MM_DD = "yy-MM-dd";

    /**
     * 日期格式：HH:mm
     */
    public static final String HH_MM = "HH:mm";
    /**
     * 日期格式：MM/dd HH:mm:ss
     */
    public static final String MM_DD_HH_MM_SS = "MM/dd HH:mm:ss";
    /**
     * 日期格式：yyyy-MM-dd HH:mm
     */
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式：yyyy-MM-ddHH:mm:ss
     */
    public static final String YYYY_MM_DDHH_MM_SS = "yyyy-MM-ddHH:mm:ss";
    /**
     * 日期格式：MM-dd HH:mm
     */
    public static final String MM_DD_HH_MM = "MM-dd HH:mm";

    /**
     * 日期格式：MM-dd
     */
    public static final String MM_DD = "MM-dd";

    /**
     * 日期格式：MM月 dd日 HH:mm
     */
    public static final String MM_DD_HH_MM1 = "MM月dd日 HH:mm";
    /**
     * 日期格式：YY年 MM月 dd日 HH:mm
     */
    public static final String YYYY_MM_DD_HH_MM1 = "yyyy年MM月dd日 HH:mm";

    // 日期格式：YY年 MM月 dd日
    public static final String YYYY_MM_DD_POINT = "yyyy年MM月dd日";

    /**
     * 根据long值，转化为yyyy-MM-dd hh:mm的格式
     *
     * @param timelong
     * @return String
     */
    public static String getTimeStamp(long timelong) {
        Date d = new Date(timelong);
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
        return df.format(d);
    }

    /**
     * timepatten就是SimpleDateFormat用到的patten，比如："yyyy-MM-dd HH:mm"
     *
     * @param timelong
     * @param timepatten
     * @return String
     */
    public static String getTimeStamp(long timelong, String timepatten) {
        Date d = new Date(timelong);
        SimpleDateFormat df = new SimpleDateFormat(timepatten);
        return df.format(d);
    }

    /**
     * timepatten就是SimpleDateFormat用到的patten，比如："yyyy-MM-dd HH:mm"
     *
     * @param timelong
     * @param timepatten
     * @return String
     */
    public static String getTimeStamp(Long timelong, String timepatten) {
        if (null == timelong) {
            return "";
        }
        Date d = new Date(timelong);
        SimpleDateFormat df = new SimpleDateFormat(timepatten);
        return df.format(d);
    }

    /**
     * timepatten就是SimpleDateFormat用到的patten，比如："yyyy-MM-dd HH:mm"
     *
     * @param date
     * @param timepatten
     * @return String
     */
    public static String getTimeStamp(Date date, String timepatten) {
        SimpleDateFormat df = new SimpleDateFormat(timepatten);
        return df.format(date);
    }

    /**
     * 根据年月日的值取得时间片
     *
     * @param mYear
     * @param mMonth
     * @param mDay
     * @return long
     */
    public static long getTimeStampFromYearMonthDay(int mYear, int mMonth,
                                                    int mDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, mDay);
        return calendar.getTimeInMillis();
    }

    /**
     * 解析字符串，返回Date对象，字符串格式默认为yyyy-MM-dd；如果解析出现异常，返回null。
     *
     * @param strDate
     * @return Date
     */
    public static Date parseDate(String strDate) {
        return parseDate(strDate, YYYY_MM_DD);
    }

    /**
     * 解析字符串，返回Date对象，可以传入字符串格式进行解析，建议使用DateUtil内置的字符串格式。如果解析出现异常，返回null。
     *
     * @param strDate
     * @return Date
     */
    public static Date parseDate(String strDate, String dateFormat) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据年月日的值取得时间片
     *
     * @param mYear
     * @param mMonth
     * @param mDay
     * @return long
     */
    public static long getTimeStampFromYearMonthDay(int mYear, int mMonth,
                                                    int mDay, int mHour, int mMin) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, mDay);
        calendar.set(Calendar.HOUR, mHour);
        calendar.set(Calendar.MINUTE, mMin);
        return calendar.getTimeInMillis();
    }

    /**
     * 比较传入时间是否比当前小
     *
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static boolean compareDateIsLessThanNow(int year, int month,
                                                   int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        int nowDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if (year < nowYear) {
            return true;
        }
        if (nowYear > year) {
            return false;
        }
        if (month < nowMonth) {
            return true;
        }
        if (nowMonth > month) {
            return false;
        }
        if (dayOfMonth < nowDayOfMonth) {
            return true;
        }
        return false;
    }

    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "星期日";
        }
        if (wek == 2) {
            Week += "星期一";
        }
        if (wek == 3) {
            Week += "星期二";
        }
        if (wek == 4) {
            Week += "星期三";
        }
        if (wek == 5) {
            Week += "星期四";
        }
        if (wek == 6) {
            Week += "星期五";
        }
        if (wek == 7) {
            Week += "星期六";
        }
        return Week;
    }

    /**
     * 时间戳转换为时分秒——HH:mm:ss
     *
     * @return
     */
    public static String getHMS(long timetemp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timetemp);
        SimpleDateFormat fmat = new SimpleDateFormat("HH:mm:ss");
        String time = fmat.format(calendar.getTime());
        return time;
    }
}
