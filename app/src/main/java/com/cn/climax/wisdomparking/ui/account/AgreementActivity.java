package com.cn.climax.wisdomparking.ui.account;

import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class AgreementActivity extends BaseSwipeBackActivity {

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "注册协议");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

}
