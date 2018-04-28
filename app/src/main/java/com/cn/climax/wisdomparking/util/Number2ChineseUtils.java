package com.cn.climax.wisdomparking.util;

/**
 * author：leo on 2018/3/5 0005 01:42
 * email： leocheung4ever@gmail.com
 * description: 数字转中文工具类
 * what & why is modified:
 */
public class Number2ChineseUtils {

    public static String toChinese(String string) {
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  " + result);
        }

        System.out.println("----------------");
        System.out.println(result);
        return result;
    }
}
