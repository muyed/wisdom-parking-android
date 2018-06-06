package com.cn.climax.wisdomparking.ui.setting.utils;

import android.content.Context;
import android.graphics.Color;

import com.cn.climax.wisdomparking.R;

/**
 * author：leo on 2018/6/5 0005 15:48
 * email： leocheung4ever@gmail.com
 * description: 银行卡相关工具类
 * what & why is modified:
 */
public class BankManager {

    //    public static String[] bankPinYinArr = new String[]{"beijing", "gonghang", "guangda", "hebei", "huaxia", "jianhang", "jiaohang", "minsheng", "nonghang", "renmin", "shanghai", "xingye", "youzheng", "zhaohang", "zhengzhou", "zhonghang", "zhongxin"};
//    public static String[] bankNameArr = new String[]{"北京银行", "中国工商银行", "中国光大银行", "河北银行", "华夏银行", "中国建设银行", "中国交通银行", "中国民生银行", "中国农业银行", "中国人民银行", "上海银行", "兴业银行", "中国邮政", "招商银行", "郑州银行", "中国银行", "中信银行"};
    public static String[] bankPinYinArr = new String[]{"gonghang", "nonghang", "zhonghang", "jianhang", "zhaohang", "youzheng", "jiaohang", "", "minsheng", "xingye", "", "zhongxin", "huaxia", "", "guangda", "beijing", ""};
    public static String[] bankCodeArr = new String[]{"1002", "1005", "1026", "1003", "1001", "1066", "1020", "1004", "1006", "1009", "1010", "1021", "1025", "1027", "1022", "1032", "1056"};
    public static String[] bankNameArr = new String[]{"工商银行", "农业银行", "中国银行", "建设银行", "招商银行", "邮储银行", "交通银行", "浦发银行", "民生银行", "兴业银行", "平安银行", "中信银行", "华夏银行", "广发银行", "光大银行", "北京银行", "宁波银行"};

    public static int getImageResId(Context context, String name) {
        String shortName = "";
        for (int i = 0; i < bankNameArr.length; i++) {
            if (name.contains(bankNameArr[i])) {
                shortName = bankPinYinArr[i];
            }
        }
        String iconName = "icon_bank_" + shortName;
        int iconId = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
        return iconId;
    }

    public static int setBgColor(String name) {
        int bgColorId = R.color.basic_color_primary;
        for (int i = 0; i < bankNameArr.length; i++) {
            if (name.contains(bankNameArr[i])) {
                if (i % 3 == 0)
                    bgColorId = R.color.basic_color_primary;
                else if (i % 3 == 1)
                    bgColorId = Color.parseColor("#495AA2");
                else if (i % 3 == 2)
                    bgColorId = Color.parseColor("#F59484");
                else
                    bgColorId = Color.parseColor("#26ca8a");
            }
        }
        return bgColorId;
    }

}
