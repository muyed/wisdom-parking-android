package com.cn.climax.wisdomparking.ui.setting;

import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class NotifyMineActivity extends BaseSwipeBackActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_notify_mine;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "我的消息");
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

}
