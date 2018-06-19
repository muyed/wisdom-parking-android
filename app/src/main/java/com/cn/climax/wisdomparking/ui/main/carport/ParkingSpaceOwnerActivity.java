package com.cn.climax.wisdomparking.ui.main.carport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cn.climax.i_carlib.util.widget.SoftInputUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.main.licenseplate.adapter.RVDeviceAddAdapter;

import butterknife.BindView;
import butterknife.OnClick;

public class ParkingSpaceOwnerActivity extends BaseSwipeBackActivity {

    @BindView(R.id.rvAddParkSpaceList)
    RecyclerView rvAddParkSpaceList;

    private RVDeviceAddAdapter mAdapter;
    private int insertPos = 1;

    @Override
    protected int initContentView() {
        return R.layout.activity_parking_space_owner;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "添加设备");
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        SoftInputUtil.hideSoftInput(this, rvAddParkSpaceList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAddParkSpaceList.setLayoutManager(layoutManager);
        mAdapter = new RVDeviceAddAdapter(this);
        rvAddParkSpaceList.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new RVDeviceAddAdapter.OnClickAddParkListener() {
            @Override
            public void addItem() {
                insertPos++;
                mAdapter.setNotifyData(insertPos);
            }

            @Override
            public void deleteItem() {
                insertPos--;
                mAdapter.setNotifyData(insertPos);
            }
        });
    }

    @OnClick({R.id.tvNextStep1})
    void click(View view) {
        switch (view.getId()) {
            case R.id.tvNextStep1:
                startActivity(new Intent(ParkingSpaceOwnerActivity.this, ParkingSpacePayActivity.class));
                break;
        }
    }
}
