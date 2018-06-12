package com.cn.climax.wisdomparking.ui.main.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.util.phone.ScreenUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.Constant;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.base.help.FullyLinearLayoutManager;
import com.cn.climax.wisdomparking.data.response.CommunityAuthListResponse;
import com.cn.climax.wisdomparking.ui.main.community.adapter.CommunityAuthedBindedAdapter;
import com.cn.climax.wisdomparking.ui.main.community.adapter.CommunityCarPortsAdapter;

import butterknife.BindView;

public class CommunityAuthedBindedActivity extends BaseSwipeBackActivity {

    @BindView(R.id.tvCommunityName)
    TextView tvCommunityName;
    @BindView(R.id.tvCommunityProvinceCity)
    TextView tvCommunityProvinceCity;
    @BindView(R.id.tvCommunityAddr)
    TextView tvCommunityAddr;
    @BindView(R.id.tvBindTotal)
    TextView tvBindTotal;
    @BindView(R.id.tvCommunityStatus)
    TextView tvCommunityStatus;
    @BindView(R.id.rvCarPortsListView)
    RecyclerView rvCarPortsListView;

    private CommunityAuthListResponse mCommunityDetail;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        mCommunityDetail = (CommunityAuthListResponse) getIntent().getSerializableExtra("community_detail");
        super.setToolBar(isShowNavBack, mCommunityDetail.getCommunityName());
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_community_authed_binded;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        mCommunityDetail = (CommunityAuthListResponse) getIntent().getSerializableExtra("community_detail");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        tvCommunityName.setText(mCommunityDetail.getCommunityName());
        tvCommunityProvinceCity.setText(mCommunityDetail.getProvince() + "　" + mCommunityDetail.getCity());
        tvCommunityAddr.setText(mCommunityDetail.getAddr());
        tvBindTotal.setText("该小区共绑定" + mCommunityDetail.getCarportList().size() + "个车位");

        dispatchStatus2List(tvCommunityStatus, mCommunityDetail);

        initRecyclerView();
    }

    private void initRecyclerView() {
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rvCarPortsListView.setLayoutManager(linearLayoutManager);
        CommunityCarPortsAdapter adapter = new CommunityCarPortsAdapter(this,mCommunityDetail, mCommunityDetail.getCarportList());
        rvCarPortsListView.setAdapter(adapter);
    }

    private void dispatchStatus2List(TextView tvCommunityStatus, final CommunityAuthListResponse authListResponse) {
        switch (authListResponse.getType()) {
            case 1: //审核中
                tvCommunityStatus.setText("认证中");
                tvCommunityStatus.setTextColor(ContextCompat.getColor(CommunityAuthedBindedActivity.this, R.color.colorPrimary));
                break;
            case 2: //小区审核通过
                tvCommunityStatus.setText("已认证");
                tvCommunityStatus.setTextColor(ContextCompat.getColor(CommunityAuthedBindedActivity.this, R.color.color_52c41a));
                break;
            case 3: //已拒绝
                tvCommunityStatus.setText("已拒绝");
                tvCommunityStatus.setTextColor(ContextCompat.getColor(CommunityAuthedBindedActivity.this, R.color.color_f5222d));
                break;
        }
    }

}
