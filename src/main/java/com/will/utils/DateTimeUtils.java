package com.will.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 *
 * @author DJ
 */
public class DateTimeUtils {
    /** 格式为: yyyy-MM-dd*/
    private static String defaultFormat="yyyy-MM-dd";
    /** 格式为: yyyy-MM-dd*/
    public static String DATE_FORMAT_YMD = "yyyy-MM-dd";
    /** 格式为: yyyyMMdd*/
    public static String DATE_FORMAT_LX_YMD = "yyyyMMdd";
    /** 格式为: yyyy-MM-dd HH*/
    public static String DATE_FORMAT_YMDH = "yyyy-MM-dd HH";
    /** 格式为: yyyy-MM-dd HH:mm*/
    public static String DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    /** 格式为: yyyy-MM-dd HH:mm:ss*/
    public static String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    /** 格式为: yyyyMMddHHmmss*/
    public static String DATE_FORMAT_LX_YMDHMS = "yyyyMMddHHmmss";
    /** 格式为: yyyyMMddHHmm*/
    public static String DATE_FORMAT_LX_YMDHM = "yyyyMMddHHmm";
    /** 格式为: HHmmss*/
    public static String DATE_FORMAT_LX_HMS = "HHmmss";

    /**
     * 以yyyy-MM-dd HH:mm:ss获得当前时间
     *
     * @return
     */
    public static String formatCurrDate() {
        return formatCurrDate("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 以fmt格式获得当前时间
     *
     * @return
     */
    public static String formatCurrDate(String fmt) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    /**
     * 以fmt格式获得时间
     *
     * @return
     */
    public static String getDate(Date date, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        String dateNowStr = sdf.format(date);
        return dateNowStr;
    }

    /**
     * 以yyyy-MM-dd HH:mm:ss格式获得时间
     *
     * @return
     */
    public static String getDate(Date date) {
        return getDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 从String 获得Date
     *
     * @throws ParseException
     */
    public static Date getDateByStringFmt(String timeString, String fmt) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            date = sdf.parse(timeString);
        } catch (Exception e) {
            return null;
        }

        return date;
    }

    /**
     * 从格式为"yyyy-MM-dd HH:mm:ss" 的String 获得Date
     *
     * @throws ParseException
     */
    public static Date getDateByStringFmt(String timeString) {
        return getDateByStringFmt(timeString, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static Date getCurrDate() {
        return new Date();
    }

    /**
     * 取得日期：年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return year;
    }

    /**
     * 取得日期：月
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        return month + 1;
    }

    /**
     * 取得日期：日
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int da = c.get(Calendar.DAY_OF_MONTH);
        return da;
    }

    /**
     * 取得当天日期是周几
     *
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(Calendar.DAY_OF_WEEK);
        return week_of_year - 1;
    }

    /**
     * 取得一年的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(Calendar.WEEK_OF_YEAR);
        return week_of_year;
    }

    /**
     * 取得月的剩余天数
     *
     * @param date
     * @return
     */
    public static int getRemainDayOfMonth(Date date) {
        int dayOfMonth = getDayOfMonth(date);
        int day = getPassDayOfMonth(date);
        return dayOfMonth - day;
    }

    /**
     * 取得月已经过的天数
     *
     * @param date
     * @return
     */
    public static int getPassDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得月天数
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得季度第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfQuarter(Date date) {
        return getFirstDateOfMonth(getQuarterDate(date)[0]);
    }

    /**
     * 取得季度最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfQuarter(Date date) {
        return getLastDateOfMonth(getQuarterDate(date)[2]);
    }

    /**
     * 取得季度天数
     *
     * @param date
     * @return
     */
    public static int getDayOfQuarter(Date date) {
        int day = 0;
        Date[] seasonDates = getQuarterDate(date);
        for (Date date2 : seasonDates) {
            day += getDayOfMonth(date2);
        }
        return day;
    }

    /**
     * 取得季度剩余天数
     *
     * @param date
     * @return
     */
    public static int getRemainDayOfQuareter(Date date) {
        return getDayOfQuarter(date) - getPassDayOfQuarter(date);
    }

    /**
     * 取得季度已过天数
     *
     * @param date
     * @return
     */
    public static int getPassDayOfQuarter(Date date) {
        int day = 0;

        Date[] quarterDates = getQuarterDate(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);

        if (month == Calendar.JANUARY || month == Calendar.APRIL
                || month == Calendar.JULY || month == Calendar.OCTOBER) {// 季度第一个月
            day = getPassDayOfMonth(quarterDates[0]);
        } else if (month == Calendar.FEBRUARY || month == Calendar.MAY
                || month == Calendar.AUGUST || month == Calendar.NOVEMBER) {// 季度第二个月
            day = getDayOfMonth(quarterDates[0])
                    + getPassDayOfMonth(quarterDates[1]);
        } else if (month == Calendar.MARCH || month == Calendar.JUNE
                || month == Calendar.SEPTEMBER || month == Calendar.DECEMBER) {// 季度第三个月
            day = getDayOfMonth(quarterDates[0])
                    + getDayOfMonth(quarterDates[1])
                    + getPassDayOfMonth(quarterDates[2]);
        }
        return day;
    }

    /**
     * 取得季度月
     *
     * @param date
     * @return
     */
    public static Date[] getQuarterDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nQuarter = getQuarter(date);
        if (nQuarter == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nQuarter == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nQuarter == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nQuarter == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getQuarter(Date date) {

        int quarter = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                quarter = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                quarter = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                quarter = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                quarter = 4;
                break;
            default:
                break;
        }
        return quarter;
    }

    /**
     * 计算时差
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Duration getDuration(Date beginDate, Date endDate) {
        Long beginDateLong = beginDate.getTime(); // Date型转换成Long型毫秒数，用于计算
        Long endDateLong = endDate.getTime();
        Long durationLong = endDateLong - beginDateLong;

        long totalSeconds = durationLong / 1000;// 总共的秒数
        long secondsOfDay = 24 * 60 * 60;// 一天的秒数
        long secondsOfHour = 60 * 60; // 一小时的秒数
        long secondsOfMinute = 60; // 一分钟的秒数

        long days = totalSeconds / secondsOfDay;// 得到天数
        long hours = (totalSeconds % secondsOfDay) / secondsOfHour;// 余数中的小时个数
        long minutes = ((totalSeconds % secondsOfDay) % secondsOfHour)
                / secondsOfMinute; // 余数中的分钟数
        long seconds = totalSeconds % 60;// 余数中的秒数

        Duration duration = new Duration();
        duration.setDays(days);
        duration.setHours(hours);
        duration.setMinutes(minutes);
        duration.setSeconds(seconds);

        return duration;
    }

    public static Date getDateByMillis(long millis){
        return new Date(millis);
    }

    public static String getFmtDateByDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        return sdf.format(date);
    }

    static class Duration {
        private long days;
        private long hours;
        private long minutes;
        private long seconds;

        public long getDays() {
            return days;
        }

        public void setDays(long days) {
            this.days = days;
        }

        public long getHours() {
            return hours;
        }

        public void setHours(long hours) {
            this.hours = hours;
        }

        public long getMinutes() {
            return minutes;
        }

        public void setMinutes(long minutes) {
            this.minutes = minutes;
        }

        public long getSeconds() {
            return seconds;
        }

        public void setSeconds(long seconds) {
            this.seconds = seconds;
        }

        public String format() {
            if (days != 0) {
                return days + "天" + hours + "时" + minutes + "分" + seconds + "秒";
            } else {
                return hours + "时" + minutes + "分" + seconds + "秒";
            }
        }
    }

}
