package com.cn.climax.wisdomparking.ui.setting.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.i_carlib.util.phone.ScreenUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.BankCardMineBean;
import com.cn.climax.wisdomparking.ui.setting.adapter.BankCardAdapter;
import com.cn.climax.wisdomparking.widget.xrecyclerview.ProgressStyle;
import com.cn.climax.wisdomparking.widget.xrecyclerview.SpacesItemDecoration;
import com.cn.climax.wisdomparking.widget.xrecyclerview.XRecyclerView;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class BankCardListActivity extends BaseSwipeBackActivity {

    @BindView(R.id.ivSwitchOption)
    ImageView ivSwitchOption;
    @BindView(R.id.rvMineBankCardList)
    XRecyclerView rvMineBankCardList;

    private BankCardAdapter mAdapter;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "我的银行卡");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_bank_card_list;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        ivSwitchOption.setVisibility(View.VISIBLE);
        ivSwitchOption.setImageResource(R.drawable.icon_main_publish_share);
        ivSwitchOption.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                startActivityForResult(new Intent(BankCardListActivity.this, BankCardAddActivity.class), 199);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMineBankCardList.setLayoutManager(layoutManager);
        rvMineBankCardList.addItemDecoration(new SpacesItemDecoration(0, ScreenUtil.dip2px(this, 5), 0, 0));
        rvMineBankCardList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rvMineBankCardList.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        rvMineBankCardList.setArrowImageView(R.drawable.iconfont_downgrey);

        rvMineBankCardList.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        rvMineBankCardList.getDefaultFootView().setLoadingDoneHint("我是有底线的");
        rvMineBankCardList.setLimitNumberToCallLoadMore(2);
        rvMineBankCardList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getMyBankList();
            }

            @Override
            public void onLoadMore() {
                rvMineBankCardList.setLoadingMoreEnabled(false);
            }
        });
        rvMineBankCardList.refresh();
        mAdapter = new BankCardAdapter(this);
        rvMineBankCardList.setAdapter(mAdapter);
    }

    private void getMyBankList() {
        ApiManage.get(ApiHost.getInstance().myBankList())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                List<BankCardMineBean> carLicenseMineBeen = com.alibaba.fastjson.JSONObject.parseArray(String.valueOf(json.get("data")), BankCardMineBean.class);
                                mAdapter.setDatas(carLicenseMineBeen);
                                rvMineBankCardList.refreshComplete();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 199 && resultCode == RESULT_OK) {
            rvMineBankCardList.refresh();
        }
    }
}
