package com.cn.climax.wisdomparking.ui.pay.activity;

import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class PayAliPayActivity extends BaseSwipeBackActivity {

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "支付宝支付");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_pay_ali_pay;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

}
