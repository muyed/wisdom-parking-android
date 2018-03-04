package com.cn.climax.wisdomparking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.ui.setting.NotifyMineActivity;
import com.cn.climax.wisdomparking.widget.ofo.OfoConvcaveMenuActivity;

import butterknife.OnClick;

public class PeterMainActivity extends OfoConvcaveMenuActivity {

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tvSkip2OfoMenu, R.id.ivSkip2Notify})
    void click(View view) {
        switch (view.getId()) {
            case R.id.tvSkip2OfoMenu:

                break;
            case R.id.ivSkip2Notify:
                startActivity(new Intent(PeterMainActivity.this, NotifyMineActivity.class));
                break;
        }
    }
}
