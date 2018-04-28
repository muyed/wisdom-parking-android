package com.cn.climax.wisdomparking.ui.main.order;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cn.climax.i_carlib.util.phone.ScreenUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.Constant;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.main.order.adapter.OrderMineAdapter;
import com.cn.climax.wisdomparking.widget.xrecyclerview.ProgressStyle;
import com.cn.climax.wisdomparking.widget.xrecyclerview.SpacesItemDecoration;
import com.cn.climax.wisdomparking.widget.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class OrderMineActivity extends BaseSwipeBackActivity {

    @BindView(R.id.xrvMineOrderListView)
    XRecyclerView xrvMineOrderListView;

    private OrderMineAdapter mAdapter;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "我的订单");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_order_mine;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initListView();
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
        xrvMineOrderListView.setLayoutManager(layoutManager);
        xrvMineOrderListView.addItemDecoration(new SpacesItemDecoration(0, ScreenUtil.dip2px(this, 5), 0, 0));
        xrvMineOrderListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrvMineOrderListView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xrvMineOrderListView.setArrowImageView(R.drawable.iconfont_downgrey);

        xrvMineOrderListView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        xrvMineOrderListView.getDefaultFootView().setLoadingDoneHint("我是有底线的");
        xrvMineOrderListView.setLimitNumberToCallLoadMore(2);
        xrvMineOrderListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                listOrders(Constant.REFRESH);
            }

            @Override
            public void onLoadMore() {
                listOrders(Constant.LOAD);
            }
        });
        xrvMineOrderListView.refresh();

        mAdapter = new OrderMineAdapter(this);
        xrvMineOrderListView.setAdapter(mAdapter);
    }

    private void listOrders(int tag) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xrvMineOrderListView.refreshComplete();
            }
        }, 1300);
    }

}
