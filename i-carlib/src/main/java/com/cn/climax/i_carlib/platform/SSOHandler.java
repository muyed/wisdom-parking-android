package com.cn.climax.i_carlib.platform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.cn.climax.i_carlib.platform.listener.AuthListener;
import com.cn.climax.i_carlib.platform.listener.ShareListener;
import com.cn.climax.i_carlib.platform.share_media.IShareMedia;

public abstract class SSOHandler {

    /**
     * 初始化
     * @param config 配置信息
     */
    public void onCreate(Context context, PlatformConfig.Platform config) {

    }

    /**
     * 登录授权
     * @param activity
     * @param authListener 授权回调
     */
    public void authorize(Activity activity, AuthListener authListener) {

    }

    /**
     * 分享
     * @param shareMedia 分享内容
     * @param shareListener 分享回调
     */
    public void share(Activity activity, IShareMedia shareMedia, ShareListener shareListener) {

    }

    /**
     * 重写onActivityResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    /**
     * 是否安装
     * @return
     */
    public boolean isInstall() {
        return true;
    }
}
