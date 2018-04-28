package com.cn.climax.wisdomparking.ui.main.device;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.util.phone.ScreenUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.Constant;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.main.device.adapter.ParkingSpaceMineAdapter;
import com.cn.climax.wisdomparking.widget.xrecyclerview.ProgressStyle;
import com.cn.climax.wisdomparking.widget.xrecyclerview.SpacesItemDecoration;
import com.cn.climax.wisdomparking.widget.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class ParkingSpaceMineActivity extends BaseSwipeBackActivity {

    @BindView(R.id.xrvParkingListView)
    XRecyclerView xrvParkingListView;
    @BindView(R.id.llSkip2AuthParkingSpace)
    LinearLayout llSkip2AuthParkingSpace;

    private ParkingSpaceMineAdapter mAdapter;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "我的车位");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_parking_space_mine;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initListView();

        llSkip2AuthParkingSpace.setOnClickListener(new CommonClick());
    }

    private void initListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvParkingListView.setLayoutManager(layoutManager);
        xrvParkingListView.addItemDecoration(new SpacesItemDecoration(0, ScreenUtil.dip2px(this, 0.5f), 0, 0));
        xrvParkingListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrvParkingListView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xrvParkingListView.setArrowImageView(R.drawable.iconfont_downgrey);

        xrvParkingListView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        xrvParkingListView.getDefaultFootView().setLoadingDoneHint("我是有底线的");
        xrvParkingListView.setLimitNumberToCallLoadMore(2);
        xrvParkingListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                listParkings(Constant.REFRESH);
            }

            @Override
            public void onLoadMore() {
                listParkings(Constant.LOAD);
            }
        });
        xrvParkingListView.refresh();

        mAdapter = new ParkingSpaceMineAdapter(this);
        xrvParkingListView.setAdapter(mAdapter);
    }

    private void listParkings(int tag) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xrvParkingListView.refreshComplete();
            }
        }, 1300);
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.llSkip2AuthParkingSpace:
                    authParkingSpace();
                    break;
            }
        }
    }

    private void authParkingSpace() {
        startActivity(new Intent(ParkingSpaceMineActivity.this, IdentityParkingSpaceActivity.class));
    }
}
