package com.cn.climax.i_carlib.util.time;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 日期时间工具类
 */
public class DateUtils {

    /**
     * 获取一个月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }
        return days;
    }

    /**
     * 获取今天星期几 1：周日，7：星期六
     */
    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取是本月的第几周
     */
    public static int getWeek() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDate() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month)) + "-01";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static int getWeekDayFromDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }

    /**
     * 获取今天的年月日
     */
    public static String getToday() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 分钟转换小时 + 分钟
     */
    public static String transferMin2HourAndMin(String minutes) {
        int hour = Integer.parseInt(minutes) / 60;
        String result;
        if (hour >= 1) {
            result = hour + "h" + (Integer.parseInt(minutes) - hour * 60) + "m";
        } else {
            result = "0h" + minutes + "m";
        }
        return result;
    }

    /**
     * 分钟转换小时
     */
    public static String transferMin2Hour(String minutes) {
//        int hour = (int) Math.rint(Integer.parseInt(minutes) / 60);
        String result;
        DecimalFormat df = new DecimalFormat("##0.0");
        result = df.format(
                Float.parseFloat(
                        String.valueOf(
                                Double.parseDouble(minutes) / 60)))
                + "h";
        return result;
    }

    /**
     * long类型时间戳转换时间 HH:mm
     */
    public static String transferLong2Time(String longTime) {
        long time = Long.parseLong(longTime);
        String result;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String temp = sdf.format(date);
        int ext = temp.lastIndexOf(" ");
        String hmsTemp = temp.substring(ext);
        result = hmsTemp.substring(0, 6);
        return result;
    }

    public static String transferLong2Time(long time) {
        String result;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String temp = sdf.format(date);
        int ext = temp.lastIndexOf(" ");
        String hmsTemp = temp.substring(ext);
        result = hmsTemp.substring(0, 6);
        return result;
    }

    public static String transferLong2Day(long time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String temp = sdf.format(date);
        int ext = temp.lastIndexOf(" ");
        return temp.substring(0, ext);
    }

    /**
     * 判断日期1是否比日期2早 整数值越大就越晚
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean isEarly(String date1, String date2) {
        Date mDate1 = null, mDate2 = null;
        try {
            mDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
            mDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert mDate1 != null;
        assert mDate2 != null;
        return mDate1.getTime() < mDate2.getTime();
    }

    /**
     * 根据长整型时间戳得到日期值
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDay(String time) {
        String result;
        Date date = new Date(Long.parseLong(time));
        result = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return result;
    }


    /**
     * long类型时间戳转换成分钟
     *
     * @param differ 两段时间戳相差值
     * @return 相差分钟数
     */
    public static String transferLong2Minute(String differ) {
        long dif = Long.parseLong(differ) / 1000;
        if (dif < 0) {
            throw new RuntimeException("这是一辆从未来穿越回来的车");
        }
        int minutes = (int) (Long.parseLong(differ) / 60000);
        return String.valueOf(minutes);
    }

    public static String getHourAndMinute(long differ) {
        long dif = differ / 1000;
        if (dif < 0) {
            throw new RuntimeException("这是一辆从未来穿越回来的车");
        }
        int minutes = (int) (differ / 60000);

        int hour = minutes / 60;
        int min = minutes % 60;
        return hour + "h" + min + "m";
    }

    public static String getHourAndMinute(double differ) {
        long dif = (long) (differ / 1000);
        if (dif < 0) {
            throw new RuntimeException("这是一辆从未来穿越回来的车");
        }
        int minutes = (int) (differ / 60000);

        int hour = minutes / 60;
        int min = minutes % 60;
        return hour + "h" + min + "m";
    }

    public static double getHour(long differ) {
        long dif = differ / 1000;
        if (dif < 0) {
            throw new RuntimeException("这是一辆从未来穿越回来的车");
        }
        double minutes = (double) (differ / 60000);
        return minutes / 60;
    }

    public static Date formatDate(Date date, int interval) {
        Calendar cal= Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -interval);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
}
