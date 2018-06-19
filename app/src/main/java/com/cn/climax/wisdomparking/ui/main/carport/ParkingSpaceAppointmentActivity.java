package com.cn.climax.wisdomparking.ui.main.carport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.main.community.AddCommunityActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ParkingSpaceAppointmentActivity extends BaseSwipeBackActivity {
    
    @BindView(R.id.etInputInviteCode)
    EditText etInputInviteCode; //预约码

    @Override
    protected int initContentView() {
        return R.layout.activity_peter_main;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

    @OnClick({R.id.flAddSmartLock, R.id.tvOrderLock})
    void click(View view) {
        switch (view.getId()) {
            case R.id.flAddSmartLock: //添加车位锁
                startActivity(new Intent(ParkingSpaceAppointmentActivity.this, ParkingSpaceAddActivity.class));
                break;
            case R.id.tvOrderLock: //立即预约
                startActivity(new Intent(ParkingSpaceAppointmentActivity.this, AddCommunityActivity.class));
                break;
        }
    }

    /**
     * 立即预约
     */
    private void instantOrder() {
        HashMap<String,String > paramsMap = new HashMap<>();
//        paramsMap.put();
//
//        ApiManage.post()
    }
}
