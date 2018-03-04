package com.cn.climax.i_carlib.platform.sina;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cn.climax.i_carlib.platform.PlatformConfig;
import com.cn.climax.i_carlib.platform.PlatformType;
import com.cn.climax.i_carlib.platform.SocialApi;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;

public class WBShareCallbackActivity extends Activity implements IWeiboHandler.Response {

    protected SinaWBHandler mSinaWBHandler = null;

    public WBShareCallbackActivity() {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SocialApi api = SocialApi.get(this.getApplicationContext());
        this.mSinaWBHandler = (SinaWBHandler) api.getSSOHandler(PlatformType.SINA_WB);
        this.mSinaWBHandler.onCreate(this.getApplicationContext(), PlatformConfig.getPlatformConfig(PlatformType.SINA_WB));

        if(this.getIntent() != null) {
            this.handleIntent(this.getIntent());
        }
    }

    protected final void onNewIntent(Intent paramIntent) {
        super.onNewIntent(paramIntent);
        SocialApi api = SocialApi.get(this.getApplicationContext());
        this.mSinaWBHandler = (SinaWBHandler) api.getSSOHandler(PlatformType.SINA_WB);
        this.mSinaWBHandler.onCreate(this.getApplicationContext(), PlatformConfig.getPlatformConfig(PlatformType.SINA_WB));

        this.handleIntent(this.getIntent());
    }

    protected void handleIntent(Intent intent) {
        this.mSinaWBHandler.onNewIntent(intent, this);
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        this.mSinaWBHandler.onResponse(baseResponse);
        finish();
    }
}
