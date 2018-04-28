package com.cn.climax.wisdomparking.ui.pay.activity;

import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class PayWechatActivity extends BaseSwipeBackActivity {

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "微信支付");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_pay_wechat;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

}
