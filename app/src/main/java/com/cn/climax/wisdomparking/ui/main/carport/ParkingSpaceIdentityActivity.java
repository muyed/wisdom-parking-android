package com.cn.climax.wisdomparking.ui.main.carport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class ParkingSpaceIdentityActivity extends BaseSwipeBackActivity {

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "认证车位");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_identity_parking_space;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

}
