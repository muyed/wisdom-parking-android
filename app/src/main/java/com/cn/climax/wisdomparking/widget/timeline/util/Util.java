package com.cn.climax.wisdomparking.widget.timeline.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author：leo on 2017/7/31 22:52
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */

public class Util {
    /**
     * long转成2015.01.03格式
     */
    public static String LongtoStringFormat(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd a HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static int Dp2Px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int Sp2Px(Context context, float spValue) {
        return (int) (spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }
}
