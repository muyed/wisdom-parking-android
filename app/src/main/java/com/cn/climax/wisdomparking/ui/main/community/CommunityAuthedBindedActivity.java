package com.cn.climax.wisdomparking.ui.main.community;

import android.content.Intent;
import android.os.Bundle;
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

        initRecyclerView();
    }

    private void initRecyclerView() {
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rvCarPortsListView.setLayoutManager(linearLayoutManager);
        CommunityCarPortsAdapter adapter = new CommunityCarPortsAdapter(this,mCommunityDetail, mCommunityDetail.getCarportList());
        rvCarPortsListView.setAdapter(adapter);
    }

}
