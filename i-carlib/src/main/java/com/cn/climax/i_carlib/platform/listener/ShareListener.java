package com.cn.climax.i_carlib.platform.listener;


import com.cn.climax.i_carlib.platform.PlatformType;

/**
 * 分享回调监听
 */
public interface ShareListener {
    void onComplete(PlatformType platform_type);

    void onError(PlatformType platform_type, String err_msg);

    void onCancel(PlatformType platform_type);
}
