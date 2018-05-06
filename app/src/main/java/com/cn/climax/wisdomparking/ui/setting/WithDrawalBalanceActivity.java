package com.cn.climax.wisdomparking.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

import butterknife.BindView;

public class WithDrawalBalanceActivity extends BaseSwipeBackActivity {

    @BindView(R.id.btnWithDrawal)
    Button btnWithDrawal;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "账户余额");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_with_drawal_balance;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        btnWithDrawal.setOnClickListener(new CommonClick());
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.btnWithDrawal:
                    withDrawal();
                    break;
            }
        }
    }

    private void withDrawal() {
        startActivity(new Intent(WithDrawalBalanceActivity.this, WithDrawalActivity.class));
    }
}
