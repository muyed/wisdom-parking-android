package com.cn.climax.wisdomparking.ui.main.device;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.widget.car.LicensePlateView;

import butterknife.BindView;

public class LicensePlateInputActivity extends BaseSwipeBackActivity implements LicensePlateView.InputListener {

    @BindView(R.id.main_rl_container)
    RelativeLayout mContainer;
    @BindView(R.id.activity_lpv)
    LicensePlateView mPlateView;

    @Override
    protected int initContentView() {
        return R.layout.activity_license_plate_input;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "添加车牌");
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        mPlateView.setInputListener(this);

        mPlateView.setKeyboardContainerLayout(mContainer);
        mPlateView.showLastView();
        mPlateView.hideLastView();
        mPlateView.onSetTextColor(R.color.colorAccent);
    }

    @Override
    public void inputComplete(String content) {

    }

    @Override
    public void deleteContent() {

    }
}
