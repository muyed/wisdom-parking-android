package com.cn.climax.wisdomparking.ui.main.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.CommunityAuthListResponse;
import com.cn.climax.wisdomparking.ui.main.community.adapter.CommunityAuthAdapter;
import com.cn.climax.wisdomparking.ui.main.device.AddDeviceActivity;

import butterknife.BindView;

public class CommunityAuthedActivity extends BaseSwipeBackActivity {

    @BindView(R.id.tvCommunityName)
    TextView tvCommunityName;
    @BindView(R.id.tvCommunityProvinceCity)
    TextView tvCommunityProvinceCity;
    @BindView(R.id.tvCommunityAddr)
    TextView tvCommunityAddr;
    @BindView(R.id.tvCommunityStatus)
    TextView tvCommunityStatus;
    @BindView(R.id.tvGoToBind)
    TextView tvGoToBind;

    private CommunityAuthListResponse mCommunityDetail;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        mCommunityDetail = (CommunityAuthListResponse) getIntent().getSerializableExtra("community_detail");
        super.setToolBar(isShowNavBack, mCommunityDetail.getCommunityName());
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_community_authed;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        mCommunityDetail = (CommunityAuthListResponse) getIntent().getSerializableExtra("community_detail");
        initView();
    }

    private void initView() {
        tvCommunityName.setText(mCommunityDetail.getCommunityName());
        tvCommunityProvinceCity.setText(mCommunityDetail.getProvince() + "　" + mCommunityDetail.getCity());
        tvCommunityAddr.setText(mCommunityDetail.getAddr());
        dispatchStatus2List(tvCommunityStatus, mCommunityDetail);

        tvGoToBind.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                startActivity(new Intent(CommunityAuthedActivity.this, AddCommunityActivity.class));
            }
        });
    }

    private void dispatchStatus2List(TextView tvCommunityStatus, final CommunityAuthListResponse authListResponse) {
        switch (authListResponse.getType()) {
            case 1: //审核中
                tvCommunityStatus.setText("认证中");
                tvCommunityStatus.setTextColor(ContextCompat.getColor(CommunityAuthedActivity.this, R.color.colorPrimary));
                break;
            case 2: //小区审核通过
                tvCommunityStatus.setText("已认证");
                tvCommunityStatus.setTextColor(ContextCompat.getColor(CommunityAuthedActivity.this, R.color.color_52c41a));
                break;
            case 3: //已拒绝
                tvCommunityStatus.setText("已拒绝");
                tvCommunityStatus.setTextColor(ContextCompat.getColor(CommunityAuthedActivity.this, R.color.color_f5222d));
                break;
        }
    }

}
