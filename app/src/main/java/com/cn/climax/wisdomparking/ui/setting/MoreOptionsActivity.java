package com.cn.climax.wisdomparking.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cn.climax.i_carlib.okgo.app.AppActivityManager;
import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.account.LoginActivity;

import butterknife.BindView;

public class MoreOptionsActivity extends BaseSwipeBackActivity {

    @BindView(R.id.btnExitApp)
    Button btnExitApp;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "更多");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_more_options;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        btnExitApp.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                startActivity(new Intent(MoreOptionsActivity.this, LoginActivity.class));
            }
        });
    }

}
