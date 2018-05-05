package com.cn.climax.wisdomparking.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * author：leo on 2018/5/4 0004 10:48
 * email： leocheung4ever@gmail.com
 * description: 时间相关工具类
 * what & why is modified:
 */
public class TimeUtils {

    private static final long YEAR_OF_MILLISECONDS = 1000 * 60 * 60 * 24 * 365;
    private static final long MOUTH_OF_MILLISECONDS = 1000 * 60 * 60 * 24 * 30;
    private static final long DAY_OF_MILLISECONDS = 1000 * 60 * 60 * 24;
    private static final long HOUR_OF_MILLISECONDS = 1000 * 60 * 60;
    private static final long MINUTES_OF_MILLISECONDS = 1000 * 60;

    /**
     * 时间戳转为时间(年月日，时分秒)
     *
     * @param cc_time 时间戳
     * @return
     */
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        //同理也可以转为其它样式的时间格式.例如："yyyy/MM/dd HH:mm"  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // 例如：cc_time=1291778220  
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;
    }

    /**
     * 时间转换为时间戳
     *
     * @param timeStr 时间 例如: 2016-03-09
     * @param format  时间对应格式  例如: yyyy-MM-dd
     */
    public static long getTimeStamp(String timeStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取相关年
     */
    public static String getCurrentYear(String timeStr) {
        String year;
        if (timeStr.length() > 4)
            year = timeStr.substring(0, 4);
        else
            year = new Date().getYear() + "";
        return year;

    }

    /**
     * 获取当前日期
     */
    public static String getCurrentDate(String timeStr) {
        String date;
        if (timeStr.length() > 4)
            date = timeStr.substring(5, 10);
        else
            date = new Date().getDate() + "";
        return date;
    }

    /**
     * 获取当前时分秒
     */
    public static String getCurrentHour(String timeStr) {
        String hour;
        if (timeStr.length() > 4)
            hour = timeStr.substring(11, 16);
        else
            hour = new Date().getHours() + ":" + new Date().getMinutes();
        return hour;
    }

    /**
     * 按照格式转换成对应日期
     *
     * @param milliseconds 时间戳毫秒为单位
     * @param format       yyyy-MM-dd  HH:mm:ss 类似的日期
     * @return
     */
    public static String getCustomFormat(long milliseconds, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date(milliseconds);
        return formatter.format(date);
    }

    /**
     * 时间转换成星期几
     *
     * @param milliseconds 时间戳，单位毫秒
     * @return 返回星期几
     */
    public static int getDayOfWeek(long milliseconds) {
        Date date = new Date(milliseconds);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return Calendar.DAY_OF_WEEK;
    }

    /**
     * 时间转换成对应日期 如：1月7日（周四）
     *
     * @param milliseconds
     * @return
     */
    public static String getDateAndDayOfWeek(long milliseconds, String format) {
        Date date = new Date(milliseconds);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("MM月dd日");
        return formatter.format(calendar) + "(周" + Calendar.DAY_OF_WEEK + ")";
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateAndDayNoWeek(long milliseconds) {
        Date date = new Date(milliseconds);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy年MM月dd日");
        return formatter.format(calendar);
    }

    /**
     * 时间转换成对应日期 如：1月7日（周四）
     */
    public static String getDateAndDayOfWeek(long milliseconds) {
        Date date = new Date(milliseconds);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return "周" + GetCH(Calendar.DAY_OF_WEEK);
    }

    /**
     * 把时间戳转换成最近的时间
     *
     * @param milliseconds
     * @return
     */
    public static String getRecentlyDate(long milliseconds) {
        long currentMilliseconds = System.currentTimeMillis();
        long timeDistance = currentMilliseconds - milliseconds;
        SimpleDateFormat dateFormat;
        if (timeDistance > 0 && timeDistance < 1000) { //一秒内
            return "刚刚";
            //过去不超过3天的时间
        } else if (timeDistance > 1000 && timeDistance <= DAY_OF_MILLISECONDS * 3) {
            if (timeDistance / 1000 < 60) {
                return timeDistance / 1000 + "秒前";
            } else if (timeDistance / MINUTES_OF_MILLISECONDS < 60) {
                return timeDistance / MINUTES_OF_MILLISECONDS + "分钟前";
            } else if (timeDistance / HOUR_OF_MILLISECONDS < 24) {
                return timeDistance / HOUR_OF_MILLISECONDS + "小时前";
            } else if (timeDistance / DAY_OF_MILLISECONDS <= 3) {
                return timeDistance / DAY_OF_MILLISECONDS + "天前";
            }
            //将来不超过三天时间
        } else if (timeDistance < 0 && timeDistance >= DAY_OF_MILLISECONDS * -3) {
            timeDistance = timeDistance * -1;
            if (timeDistance / 1000 < 60) {
                return timeDistance / 1000 + "秒后";
            } else if (timeDistance / MINUTES_OF_MILLISECONDS < 60) {
                return timeDistance / MINUTES_OF_MILLISECONDS + "分钟后";
            } else if (timeDistance / HOUR_OF_MILLISECONDS < 24) {
                return timeDistance / HOUR_OF_MILLISECONDS + "小时后";
            } else if (timeDistance / DAY_OF_MILLISECONDS <= 3) {
                return timeDistance / DAY_OF_MILLISECONDS + "天后";
            }
            //过去或将来超过3天，但小于一年
        } else if (Math.abs(timeDistance) < YEAR_OF_MILLISECONDS) {
            dateFormat = new SimpleDateFormat("MM-dd");
            return dateFormat.format(new Date(milliseconds));
        }
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date(milliseconds));
    }

    public static String getSimple(long milliseconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(new Date(milliseconds));
    }

    private static SimpleDateFormat sf = null;

    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 直接获取时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        String currentDate = getCurrentDate();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date.getTime());
    }

    private static String GetCH(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 7:
                sd = "日";
                break;
            default:
                break;
        }
        return sd;
    }
}
