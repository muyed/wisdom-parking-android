package com.cn.climax.i_carlib.share.core.handler.wx;

import android.app.Activity;

import com.cn.climax.i_carlib.share.core.CarSmartShareConfiguration;
import com.cn.climax.i_carlib.share.core.SocializeMedia;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

public class WxChatShareHandler extends BaseWxShareHandler {

    public WxChatShareHandler(Activity context, CarSmartShareConfiguration configuration) {
        super(context, configuration);
    }

    @Override
    int getShareType() {
        return SendMessageToWX.Req.WXSceneSession;
    }

    @Override
    protected SocializeMedia getSocializeType() {
        return SocializeMedia.WEIXIN;
    }

    @Override
    public SocializeMedia getShareMedia() {
        return SocializeMedia.WEIXIN;
    }
}
