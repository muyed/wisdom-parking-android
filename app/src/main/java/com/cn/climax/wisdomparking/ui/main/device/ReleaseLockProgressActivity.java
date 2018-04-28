package com.cn.climax.wisdomparking.ui.main.device;

import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class ReleaseLockProgressActivity extends BaseSwipeBackActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_order_lock;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, headerTitle);
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        
    }

}
