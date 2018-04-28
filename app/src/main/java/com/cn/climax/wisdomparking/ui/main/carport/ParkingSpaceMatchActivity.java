package com.cn.climax.wisdomparking.ui.main.carport;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cn.climax.i_carlib.util.phone.ScreenUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.Constant;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.main.carport.adapter.ParkingSpaceMatchAdapter;
import com.cn.climax.wisdomparking.widget.xrecyclerview.ProgressStyle;
import com.cn.climax.wisdomparking.widget.xrecyclerview.SpacesItemDecoration;
import com.cn.climax.wisdomparking.widget.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class ParkingSpaceMatchActivity extends BaseSwipeBackActivity {

    @BindView(R.id.xrvMatchListView)
    XRecyclerView xrvMatchListView;

    private ParkingSpaceMatchAdapter mAdapter;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "共享车位");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_parking_space_match;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvMatchListView.setLayoutManager(layoutManager);
        xrvMatchListView.addItemDecoration(new SpacesItemDecoration(0, ScreenUtil.dip2px(this, 0.5f), 0, 0));
        xrvMatchListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrvMatchListView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xrvMatchListView.setArrowImageView(R.drawable.iconfont_downgrey);

        xrvMatchListView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        xrvMatchListView.getDefaultFootView().setLoadingDoneHint("我是有底线的");
        xrvMatchListView.setLimitNumberToCallLoadMore(2);
        xrvMatchListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                listMatchParking(Constant.REFRESH);
            }

            @Override
            public void onLoadMore() {
                listMatchParking(Constant.LOAD);
            }
        });
        xrvMatchListView.refresh();

        mAdapter = new ParkingSpaceMatchAdapter(this);
        xrvMatchListView.setAdapter(mAdapter);
    }

    private void listMatchParking(int tag) {
        xrvMatchListView.refreshComplete();
    }
}

