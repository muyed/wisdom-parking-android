package com.cn.climax.wisdomparking.ui.main;

import android.os.Bundle;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.BulletinResponse;

import butterknife.BindView;

public class AppBulletinActivity extends BaseSwipeBackActivity {

    @BindView(R.id.tvBody)
    TextView tvBody;

    private BulletinResponse mCurTipBean;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        mCurTipBean = (BulletinResponse) getIntent().getSerializableExtra("cur_tip_bean");
        super.setToolBar(isShowNavBack, mCurTipBean.getTitle());
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_app_bulletin;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        mCurTipBean = (BulletinResponse) getIntent().getSerializableExtra("cur_tip_bean");
        tvBody.setText(mCurTipBean.getBody());
    }

}
