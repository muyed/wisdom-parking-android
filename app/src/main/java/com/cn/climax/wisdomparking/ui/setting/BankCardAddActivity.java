package com.cn.climax.wisdomparking.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

import butterknife.BindView;

public class BankCardAddActivity extends BaseSwipeBackActivity {

    @BindView(R.id.etCardNo)
    EditText etCardNo;
    @BindView(R.id.btnBindCardNext)
    Button btnBindCardNext;

    private String mBankCardNo;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "添加银行卡");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_bank_card_add;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        btnBindCardNext.setOnClickListener(new CommonClick());

        etCardNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mBankCardNo = s.toString();
                } else {
                    mBankCardNo = null;
                }
            }
        });
    }

    private class CommonClick extends ForbidQuickClickListener {
        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.btnBindCardNext:
                  
                    break;
            }
        }
    }
}