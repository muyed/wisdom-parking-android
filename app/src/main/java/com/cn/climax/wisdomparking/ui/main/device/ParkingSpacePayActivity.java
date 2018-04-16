package com.cn.climax.wisdomparking.ui.main.device;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.main.device.adapter.RVDevicePayAdapter;

import butterknife.BindView;

public class ParkingSpacePayActivity extends BaseSwipeBackActivity {

    @BindView(R.id.rvPayParkSpaceList)
    RecyclerView rvPayParkSpaceList;

    private RVDevicePayAdapter mAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_parking_space_pay;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "支付");
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPayParkSpaceList.setLayoutManager(layoutManager);
        mAdapter = new RVDevicePayAdapter(this);
        rvPayParkSpaceList.setAdapter(mAdapter);
    }

}
