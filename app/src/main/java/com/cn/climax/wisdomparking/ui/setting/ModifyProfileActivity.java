package com.cn.climax.wisdomparking.ui.setting;

import android.os.Bundle;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

public class ModifyProfileActivity extends BaseSwipeBackActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_modify_profile;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
    }
}
