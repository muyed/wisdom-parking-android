package com.cn.climax.wisdomparking.ui.account;

import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class RetrievePwdActivity extends BaseSwipeBackActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_retrieve_pwd;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "找回密码");
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

}
