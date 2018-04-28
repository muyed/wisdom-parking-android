package com.cn.climax.wisdomparking.ui.main.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.CommunityAuthListResponse;

import butterknife.BindView;

public class CommunityDeniedActivity extends BaseSwipeBackActivity {

    @BindView(R.id.tvCommunityName)
    TextView tvCommunityName;
    @BindView(R.id.tvCommunityAddr)
    TextView tvCommunityAddr;

    private CommunityAuthListResponse mCommunityDetail;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        mCommunityDetail = (CommunityAuthListResponse) getIntent().getSerializableExtra("community_detail");
        super.setToolBar(isShowNavBack, mCommunityDetail.getCommunityName());
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_community_denied;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        mCommunityDetail = (CommunityAuthListResponse) getIntent().getSerializableExtra("community_detail");
        initView();
    }

    private void initView() {
        tvCommunityName.setText(mCommunityDetail.getCommunityName());
        tvCommunityAddr.setText(mCommunityDetail.getAddr());
    }

}
