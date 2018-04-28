package com.cn.climax.wisdomparking.ui.main.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.main.device.ParkingSpacePayActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderMineDetailActivity extends BaseSwipeBackActivity {

    @BindView(R.id.btnPayMoney)
    Button btnPayMoney;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "订单详情");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_order_mine_detail;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btnPayMoney})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btnPayMoney:
                payMoney();
                break;
        }
    }

    private void payMoney() {
        startActivity(new Intent(OrderMineDetailActivity.this, ParkingSpacePayActivity.class));
    }

}
