package com.cn.climax.wisdomparking.ui.main.device;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

import butterknife.OnClick;

public class AddDeviceActivity extends BaseSwipeBackActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_add_device;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "添加设备");
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btnImCarOwner, R.id.btnImNotCarOwner, R.id.btnMineParkingSpace})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btnImCarOwner: //我是车位主
                startActivity(new Intent(AddDeviceActivity.this, ParkingSpaceOwnerActivity.class));
                break;
            case R.id.btnImNotCarOwner: //我不是车位主
                startActivity(new Intent(AddDeviceActivity.this, ParkingSpaceNotOwnerActivity.class));
                break;
            case R.id.btnMineParkingSpace: //我不是车位主
                startActivity(new Intent(AddDeviceActivity.this, ParkingSpaceMineActivity.class));
                break;
        }
    }
}
