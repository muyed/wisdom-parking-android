package com.cn.climax.wisdomparking.widget.citypicker.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * author：leo on 2017/3/17 14:10
 * email： leocheung4ever@gmail.com
 * description: 拼音处理相关工具类
 * what & why is modified:
 */

public class PinyinUtils {

    /**
     * 获取拼音的首字母（大写）
     */
    public static String getFirstLetter(final String pinyin) {
        if (TextUtils.isEmpty(pinyin)) return "定位";
        String c = pinyin.substring(0, 1);
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c).matches()) {
            return c.toUpperCase();
        } else if ("0".equals(c)) {
            return "定位";
        } else if ("1".equals(c)) {
            return "热门";
        }
        return "定位";
    }
}
