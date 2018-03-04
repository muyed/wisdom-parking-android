package com.cn.climax.i_carlib.platform.listener;

import com.cn.climax.i_carlib.platform.PlatformType;

import java.util.Map;

/**
 *  授权接口
 */
public interface AuthListener {
    void onComplete(PlatformType platform_type, Map<String, String> map);

    void onError(PlatformType platform_type, String err_msg);

    void onCancel(PlatformType platform_type);
}
