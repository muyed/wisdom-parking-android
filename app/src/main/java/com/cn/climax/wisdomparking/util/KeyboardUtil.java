package com.cn.climax.wisdomparking.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * 键盘事件处理
 *
 */
public class KeyboardUtil {

    private static KeyboardUtil instance;

    private KeyboardUtil() {

    }

    /**
     * 键盘事件处理
     */
    public static KeyboardUtil getInstance() {
        if (instance == null) {
            instance = new KeyboardUtil();
        }
        return instance;
    }

    /**
     * 隐藏 软键盘
     */
    public void hiddenKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager manager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) {
            boolean isOpen = manager.isActive(); // 判断软键盘是否打开
            if (isOpen) {
                View view = activity.getCurrentFocus();
                if (view != null) {
                    manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    /**
     * 显示 软键盘
     */
    public void showKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager manager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }
    }
}
