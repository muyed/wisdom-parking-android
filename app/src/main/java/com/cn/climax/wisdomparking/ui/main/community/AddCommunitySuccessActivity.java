package com.cn.climax.wisdomparking.ui.main.community;

import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class AddCommunitySuccessActivity extends BaseSwipeBackActivity {

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle, String rightTitle) {
        super.setToolBar(isShowNavBack, "小区认证", "完成");
    }

    @Override
    protected String isSHowRightTitle() {
        return "完成";
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_add_community_success;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        setOnClickRightListener(new OnClickRightBarListener() {
            @Override
            public void click() {
                finish();
            }
        });
    }

}
