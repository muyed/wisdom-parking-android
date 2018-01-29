package com.app.framework.utils.toast;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.app.framework.app.AppControl;
import com.app.framework.app.BaseApplication;
import com.app.framework.loger.Loger;


/**
 * Toast工具类
 */
public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("不能实例化！");
    }

    public static String tag = "T";

    static boolean printOnce = true;

    private static Toast mToast;

    @Deprecated
    private static void show(Context context, String text) {
        if ("空指针异常".equals(text) || TextUtils.isEmpty(text)) {
            return;
        }
        if (printOnce) {
            if (mToast != null) {
                mToast.cancel();
                mToast = null;
            }
            Loger.d(text);
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            Loger.d(text);
        }
    }

    /**
     * 正式环境可见的的Toast
     *
     */
    public static void show(String text, int duration) {
        if ("空指针异常".equals(text) || TextUtils.isEmpty(text)) {
            return;
        }
        Activity activity = BaseApplication.getInstance().getCurrentActivity();
        if (activity != null) {
            if (printOnce) {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                Loger.d(text);
                mToast = Toast.makeText(activity, text, duration);
                mToast.show();
            } else {
                Toast.makeText(activity, text, duration).show();
                Loger.d(text);
            }
        } else {
            Loger.d(text);
            Toast.makeText(BaseApplication.getInstance(), text, duration).show();
        }
    }

    /**
     * 正式环境可见的的Toast
     *
     */
    public static void show(String text) {
        show(text, Toast.LENGTH_SHORT);

    }


    /**
     * debug 时才能显示的Toast
     *
     */
    public static void showDebug(String text) {
        if (!AppControl.isDeBug()) {
            return;
        }

        show("[DeBug]:" + text);

    }

    private static Toast myCustomToast;

}
