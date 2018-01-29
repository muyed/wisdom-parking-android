package com.cn.climax.i_carlib.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * author：leo on 2017/11/24 0024 01:49
 * email： leocheung4ever@gmail.com
 * description: 软件盘工具类
 * what & why is modified:
 */
public class SoftInputUtil {

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); // 显示软键盘  
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); // 显示软键盘  
        imm.showSoftInput(view, 0);
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager immHide = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); // 隐藏软键盘  
        immHide.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
