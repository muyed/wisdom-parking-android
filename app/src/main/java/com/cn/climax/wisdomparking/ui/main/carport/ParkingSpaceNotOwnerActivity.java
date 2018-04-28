package com.cn.climax.wisdomparking.ui.main.device;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.base.help.CustomLinearLayoutManager;
import com.cn.climax.wisdomparking.ui.main.device.adapter.RVCarIdentifyAdapter;
import com.cn.climax.wisdomparking.ui.main.device.adapter.RVDeviceShowAdapter;

import butterknife.BindView;

public class ParkingSpaceNotOwnerActivity extends BaseSwipeBackActivity {
    
    @BindView(R.id.rvShowParkSpaceList) RecyclerView rvShowParkSpaceList;
    @BindView(R.id.rvAddCarIdentifyList) RecyclerView rvAddCarIdentifyList;
    @BindView(R.id.nsvScrollView) NestedScrollView nsvScrollView;
    
    private RVDeviceShowAdapter mAdapter;
    private int insertPos = 1;

    @Override
    protected int initContentView() {
        return R.layout.activity_parking_space_not_owner;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initSmartLock();
        initCarIdentify();
    }

    private void initCarIdentify() {
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setScrollVerticallyEnabled(false);
        rvAddCarIdentifyList.setLayoutManager(layoutManager);
        final RVCarIdentifyAdapter mCarAdapter = new RVCarIdentifyAdapter(this);
        rvAddCarIdentifyList.setAdapter(mCarAdapter);
        mCarAdapter.setOnClickListener(new RVCarIdentifyAdapter.OnClickAddCarListener() {
            @Override
            public void addItem() {
                insertPos++;
                mCarAdapter.setNotifyData(insertPos);
                nsvScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        nsvScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }

            @Override
            public void deleteItem() {
                insertPos--;
                mCarAdapter.setNotifyData(insertPos);
            }
        });
    }

    private void initSmartLock() {
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setScrollVerticallyEnabled(false);
        rvShowParkSpaceList.setLayoutManager(layoutManager);
        mAdapter = new RVDeviceShowAdapter(this);
        rvShowParkSpaceList.setAdapter(mAdapter);
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "添加设备");
    }
}
