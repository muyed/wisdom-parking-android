package com.cn.climax.wisdomparking.ui.main.licenseplate;

import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class ReleaseLockActivity extends BaseSwipeBackActivity {

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "扫码付费");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_release_lock;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

}
