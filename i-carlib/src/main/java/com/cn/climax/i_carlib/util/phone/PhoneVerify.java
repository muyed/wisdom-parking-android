package com.app.framework.utils.phone;

import android.text.TextUtils;

/**

 */
public class PhoneVerify {

    /**
     * 验证手机 正则表达式
     */
    public static final String mValidatePhone = "^((13[0-9]) | (15[^4,\\D]) | (18[0,5-9]) | (17[0]) )\\d{8}$";//加入170 虚拟号段

    /**
     * 验证手机号码
     *
     * @param phone 要验证的手机号
     * @return true：合格； false：不合格。
     */
    public static boolean isPhoneNumber(String phone) {
//        if (TextUtils.isEmpty(phone)) {
//            return false ;
//        }
//
//        Pattern p = Pattern.compile(mValidatePhone);
//        Matcher m = p.matcher(phone);
//        LogUtils.d("验证手机号码", "phone = " + phone) ;
//        return m.matches();

        return !TextUtils.isEmpty(phone) && phone.length() == 11 && strIsNumber(phone);
    }

    /**
     * Str是否是数字
     *
     * @return
     */
    public static boolean strIsNumber(String str) {
        boolean isNumber = false;
        try {
            double d = Double.valueOf(str);
            isNumber = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNumber;
    }

}
