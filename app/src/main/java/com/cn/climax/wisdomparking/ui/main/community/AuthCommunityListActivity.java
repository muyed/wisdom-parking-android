package com.cn.climax.wisdomparking.ui.main.community;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.phone.ScreenUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.Constant;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.CommunityAuthListResponse;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.ui.main.community.adapter.CommunityAuthAdapter;
import com.cn.climax.wisdomparking.widget.xrecyclerview.ProgressStyle;
import com.cn.climax.wisdomparking.widget.xrecyclerview.SpacesItemDecoration;
import com.cn.climax.wisdomparking.widget.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class AuthCommunityListActivity extends BaseSwipeBackActivity {

    @BindView(R.id.xrvAuthCommunityListView)
    XRecyclerView xrvAuthCommunityListView;

    private List<CommunityAuthListResponse> mCommunityListBean;
    private CommunityAuthAdapter mAdapter;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "我的小区");
    }
    
    @Override
    protected int initContentView() {
        return R.layout.activity_auth_community_list;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initListView();
    }

    private void initListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvAuthCommunityListView.setLayoutManager(layoutManager);
        xrvAuthCommunityListView.addItemDecoration(new SpacesItemDecoration(0, ScreenUtil.dip2px(this, 0.5f), 0, 0));
        xrvAuthCommunityListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrvAuthCommunityListView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xrvAuthCommunityListView.setArrowImageView(R.drawable.iconfont_downgrey);

        xrvAuthCommunityListView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        xrvAuthCommunityListView.getDefaultFootView().setLoadingDoneHint("我是有底线的");
        xrvAuthCommunityListView.setLimitNumberToCallLoadMore(2);
        xrvAuthCommunityListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                listCommunity(Constant.REFRESH);
            }

            @Override
            public void onLoadMore() {
                listCommunity(Constant.LOAD);
            }
        });
        xrvAuthCommunityListView.refresh();

        mAdapter = new CommunityAuthAdapter(this);
        xrvAuthCommunityListView.setAdapter(mAdapter);

    }

    private void listCommunity(final int tag) {
        if (tag == Constant.REFRESH) {
            mPageIndex = 1;
        } else {
            mPageIndex++;
        }
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiParamsKey.PAGE_INDEX, mPageIndex + "");
        paramsMap.put(ApiParamsKey.PAGE_SIZE, mPageSize + "");

        ApiManage.get(ApiHost.getInstance().getMineCommunityList())
                .params(paramsMap)
                .execute(new WrapJsonBeanCallback<List<CommunityAuthListResponse>>(this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                        
                    }

                    @Override
                    protected void onExecuteSuccess(List<CommunityAuthListResponse> bean, Call call) {
                        mCommunityListBean = bean;
                        if (tag == Constant.REFRESH) {
                            mAdapter.setDatas(mCommunityListBean);
                            xrvAuthCommunityListView.refreshComplete();
                        } else {
                            mAdapter.addDatas(mCommunityListBean);
                            xrvAuthCommunityListView.loadMoreComplete();
                        }
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {

                    }

                    @Override
                    protected boolean setDialogShow() {
                        return false;
                    }
                });
    }
}
