package com.app.framework.utils.phone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.app.framework.loger.Loger;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 手机本地信息
 */
public class PhoneInfo {

    private static PhoneInfo instance;

    private PhoneInfo() {

    }

    public static PhoneInfo getInstance() {
        if (instance == null) {
            instance = new PhoneInfo();
        }
        return instance;
    }

    /**
     * 页面Log_Title
     */
    private String PAGETAG = "MyPhoneInfo";

    /**
     * 状态栏高度
     */
    public int mIntZhuangTaiLanGaoDu = -1;

    /**
     * 屏幕宽度
     */
    public int mIntKuangDu = -1;

    /**
     * 屏幕高度
     */
    public int mIntGaoDu = -1;

    /**
     * 系统版本号
     */
    public String mStrXiTongBanBenHao = "";

    /**
     * 手机型号
     */
    public String mStrShouJiXingHao = "";

    /**
     * 本地软件版本号
     */
    public int mVersionCode = 1;
    /**
     * 本地软件版本名
     */
    public String mVersionName = "";

    /**
     * 屏幕分辨率
     */
    public String mStrFenBianLv = "";

    /**
     * 手机设备ID
     */
    public String mStrDeviceId = "100000000000000";

    /**
     * 手机类型，0:ios，1:android
     */
    public String mIntMobileType = "1";

    /**
     * 获取手机信息
     * 在 Activity 的onCreate 或者 在权限获取后 在调用一次
     */
    @SuppressWarnings("deprecation")
    public void init(Context context, Activity activity) {
        if (!TextUtils.isEmpty(mStrDeviceId) && !"100000000000000".equals(mStrDeviceId)) {
            return;
        }
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        mIntZhuangTaiLanGaoDu = rect.top;// 状态栏高度

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mIntKuangDu = wm.getDefaultDisplay().getWidth();// 屏幕宽度
        mIntGaoDu = wm.getDefaultDisplay().getHeight();// 屏幕高度

        mStrXiTongBanBenHao = android.os.Build.VERSION.RELEASE;// 系统版本号
        mStrShouJiXingHao = android.os.Build.MODEL;
        mVersionName = getVersionName(context);
        mVersionCode = getVersionCode(context);// 本地软件版本号
        // 屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mStrFenBianLv = dm.heightPixels * dm.widthPixels + "";// 屏幕分辨率

        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        //仅能拿到有效设备号才更新，否则用默认设备号
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            if (!TextUtils.isEmpty(tm.getDeviceId())) {
                mStrDeviceId = tm.getDeviceId();
            }
        }
        Loger.d(PAGETAG, "PhoneInfo = " + "手机信息" +
                ": 状态栏高度 = " + mIntZhuangTaiLanGaoDu +
                "、屏幕宽度 = " + mIntKuangDu +
                "、屏幕高度 = " + mIntGaoDu +
                "、系统版本号 = " + mStrXiTongBanBenHao +
                "、手机型号 = " + mStrShouJiXingHao +
                "、本地软件版本号= " + mVersionCode +
                "、本地软件版本名= " + mVersionName +
                "、屏幕分辨率= " + mStrFenBianLv +
                "、手机设备ID= " + mStrDeviceId + ";");
    }

    /**
     * 回收变量
     */
    public void myHuiShou() {

        /** 状态栏高度 */
        mIntZhuangTaiLanGaoDu = -1;

        /** 屏幕宽度 */
        mIntKuangDu = -1;

        /** 屏幕高度 */
        mIntGaoDu = -1;

        /** 系统版本号 */
        mStrXiTongBanBenHao = "";

        /** 本地软件版本号 */
        mVersionCode = 0;

        /** 屏幕分辨率 */
        mStrFenBianLv = "";

        /** 手机设备ID */
        mStrDeviceId = "";
    }

    /**
     * 获取本机IP
     *
     * @return 本机IP
     */
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();

                    String tempIP = inetAddress.getHostAddress().toString();
                    Loger.d("WifiPreference IpAddress", "tempIP = {" + tempIP
                            + "} ;");
                    if (!inetAddress.isLoopbackAddress()) {
                        String mIP = inetAddress.getHostAddress().toString();
                        if (!mIP.contains("::")) {
                            return mIP;
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本机MAC
     *
     * @return 本机MAC
     */
    @SuppressLint("HardwareIds")
    public String getLocalMacAddress(Context mContext) {
        @SuppressLint("WifiManagerPotentialLeak") WifiManager wifi = (WifiManager) mContext
                .getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = wifi.getConnectionInfo();
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 显示内存
     */
    public MemoryInfo getDisplayBriefMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo info = new MemoryInfo();
        activityManager.getMemoryInfo(info);
        Loger.d(PAGETAG, "系统剩余内存:" + (info.availMem >> 10) + "k");
        Loger.d(PAGETAG, "系统是否处于低内存运行：" + info.lowMemory);
        Loger.d(PAGETAG, "当系统剩余内存低于" + info.threshold + "时就看成低内存运行");
        return info;
    }

    /**
     * 验证手机号码格式
     *
     * @param mStrPhone
     * @return true 合格\false 不合格
     */
    public boolean checkAccountValid(String mStrPhone) {
        Pattern p = Pattern.compile(PhoneVerify.mValidatePhone);
        Matcher m = p.matcher(mStrPhone);
        return m.matches();
    }


    /**
     * 版本名
     *
     * @param context
     * @return
     */
    public String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }


    /**
     * 版本号
     *
     * @param context
     * @return
     */
    public int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 包的信息
     *
     * @param context
     * @return
     */
    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 该手机是OppO手机
     *
     * @return true:是，false:不是
     */
    public boolean isOppoPhone() {

//      PhoneInfo = 手机信息: 状态栏高度 = 0、屏幕宽度 = 1080、屏幕高度 = 1920、
//      系统版本号 = 5.1、手机型号 = OPPO R9m、本地软件版本号= 999、
//      本地软件版本名= 9.9.94、屏幕分辨率= 2073600、手机设备ID= 862021036070772;
//
        if (mStrShouJiXingHao.contains("OPPO")
                || mStrShouJiXingHao.contains("oppo")) {
            Loger.d(PAGETAG, "是OPPO手机！");
            return true;
        }

        Loger.d(PAGETAG, "不是OPPO手机！");
        return false;
    }

}
