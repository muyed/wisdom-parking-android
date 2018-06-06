package com.cn.climax.wisdomparking.ui.setting.bank;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.setting.adapter.BankListAdapter;

import butterknife.BindView;

public class BankCardSelectActivity extends BaseSwipeBackActivity {

    @BindView(R.id.rvBankList)
    RecyclerView rvBankList;

    @Override
    protected int initContentView() {
        return R.layout.activity_bank_card_select;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "选择银行");
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initList();
    }

    private void initList() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvBankList.setLayoutManager(manager);
        BankListAdapter adapter = new BankListAdapter(this);
        rvBankList.setAdapter(adapter);
    }

}
