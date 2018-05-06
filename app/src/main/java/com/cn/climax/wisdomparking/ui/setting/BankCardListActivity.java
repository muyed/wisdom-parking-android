package com.cn.climax.wisdomparking.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.setting.adapter.BankCardAdapter;

import butterknife.BindView;

public class BankCardListActivity extends BaseSwipeBackActivity {

    @BindView(R.id.ivSwitchOption)
    ImageView ivSwitchOption;
    @BindView(R.id.rvMineBankCardList)
    RecyclerView rvMineBankCardList;

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
                startActivity(new Intent(BankCardListActivity.this, BankCardAddActivity.class));
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMineBankCardList.setLayoutManager(layoutManager);
        BankCardAdapter adapter = new BankCardAdapter(this);
        rvMineBankCardList.setAdapter(adapter);
    }

}
