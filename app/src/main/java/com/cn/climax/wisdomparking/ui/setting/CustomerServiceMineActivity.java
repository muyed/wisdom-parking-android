package com.cn.climax.wisdomparking.ui.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class CustomerServiceMineActivity extends BaseSwipeBackActivity {

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "我的客服");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_customer_service_mine;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

}
