package com.cn.climax.wisdomparking.base;

import android.app.Activity;
import android.content.Context;

import com.cn.climax.i_carlib.okgo.app.AppControl;
import com.cn.climax.i_carlib.okgo.app.BaseApplication;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParams;
import com.cn.climax.wisdomparking.BuildConfig;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.lzy.okgo.OkGo;

import java.util.LinkedList;
import java.util.List;

/**
 * author：leo on 2018/1/31 22:49
 * email： leocheung4ever@gmail.com
 * description: director
 * what & why is modified:
 */

public class Core extends BaseApplication {

    private static final String TAG = "Core";
    public static Core instances;
    private List<Activity> mList = new LinkedList<>();

    @Override
    protected void initAppControl() {
        /**  设置Debug log 开关 */
        AppControl.setIsDeBug_IsShowLog(BuildConfig.DEBUG);
        /**  设置显示log调用者 开关 */
        AppControl.setIsShowLogCatCaller(BuildConfig.DEBUG);
        /** 初始化ZXing */
//        ZXingLibrary.initDisplayOpinion(this);
        //注册极光
//        JPushInterface.setDebugMode(BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);            // 初始化 JPush

        ApiHost.getInstance().init();
        instances = this;

        initRongYun();
        initXunFei();

    }

    private void initXunFei() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5ae43498");
    }

    /*初始化融云IM*/
    private void initRongYun() {
//        RongIM.init(this);
//
//        /**
//         * 设置消息体内是否携带用户信息。
//         *
//         * @param state 是否携带用户信息，true 携带，false 不携带。
//         */
//        RongIM.getInstance().setMessageAttachedUserInfo(true);
//        RongIM.getInstance().registerMessageTemplate(new ITextMessageProvider());
//        RongIM.getInstance().registerMessageTemplate(new IImgMessageProvider());
//
//        //注册消息体类型
//        RongIM.registerMessageType(CustomizeMessage.class);
//        RongIM.registerMessageType(MLEnquiryMessage.class);
//        RongIM.registerMessageType(MLSupplyDemandMessage.class);
//        RongIM.registerMessageType(MLClaimOrderMessage.class);
//        RongIM.registerMessageType(MLConfirmOrderMessage.class);
//        //注册消息模版 可以控制消息显示样式
//        RongIM.registerMessageTemplate(new CustomizeMessageProvider()); //自定义消息模板
//        RongIM.registerMessageTemplate(new MLEnquiryMessageProvider()); //我要询价 消息模板
//        RongIM.registerMessageTemplate(new MLSupplyDemandMessageProvider()); //求购/供给 消息模板
//        RongIM.registerMessageTemplate(new MLClaimOrderMessageProvider()); //报价 消息模板
//        RongIM.registerMessageTemplate(new MLConfirmOrderMessageProvider()); //确认订单 消息模板
//
//        setIExtensionModule();
    }

    @Override
    protected void initThirdParty() {
        /**初始化OkGo*/
        OkGo.init(this);
        /** 网络框架初始化 */
        ApiParams.getInstance().setOkGoHeader();
    }

    public static Core getInstances() {
        return instances;
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
//        PlatformConfig.setWeixin("wx2e45e300ba1fc6f6", "08e003f699b777b6c2d3d907bb9a6314");
//        PlatformConfig.setQQZone("1106414411", "1ceCTDTKacSvpSsu");
    }

}
