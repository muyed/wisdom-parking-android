package com.cn.climax.i_carlib.okgo.app;

/**
 * app全局控制开关：在application 中开启设置

 */
public abstract class AppControl {


    /**
     * API 网络选择
     *
     * @外网 ：true_正式
     * @外网备用 ：false_测试
     */
    private static boolean IsFormal = true;



    /**
     * 开发模式
     * false：关闭
     */
    private static boolean IsDeBug = false;




    /**
     * 显示log
     * false：关闭
     */
    private static boolean IsDeBug_IsShowLog = true;

    /**
     * 显示log调用者
     * false：关闭
     */
    private static boolean isShowLogCatCaller = false;

    /**
     * 网络选择
     *
     * @外网 ：true_正式
     * @外网备用 ：false_测试
     */
    public static boolean isFormal() {
        return IsFormal;
    }

    /**
     * 网络选择
     *
     * @param isFormal true：正式、false：测试
     */
    public static void setIsFormal(boolean isFormal) {
        IsFormal = isFormal;
    }




    /**
     * 开发模式
     * false：关闭
     */
    public static boolean isDeBug() {
        return IsDeBug;
    }

    /**
     * 开发模式
     *
     * @param isDeBug false：关闭
     */
    public static void setIsDeBug(boolean isDeBug) {
        IsDeBug = isDeBug;
    }





    /**
     * 显示log
     * false：关闭
     */
    public static boolean isDeBug_IsShowLog() {
        return IsDeBug_IsShowLog;
    }

    /**
     * 显示log
     *
     * @param isDeBug_IsShowLog false：关闭
     */
    public static void setIsDeBug_IsShowLog(boolean isDeBug_IsShowLog) {
        IsDeBug_IsShowLog = isDeBug_IsShowLog;
    }

    /**
     * 显示log调用者
     * false：关闭
     */
    public static boolean isShowLogCatCaller() {
        return isShowLogCatCaller;
    }

    /**
     * 显示log调用者
     * false：关闭
     */
    public static void setIsShowLogCatCaller(boolean isShowLogCatCaller) {
        AppControl.isShowLogCatCaller = isShowLogCatCaller;
    }
}