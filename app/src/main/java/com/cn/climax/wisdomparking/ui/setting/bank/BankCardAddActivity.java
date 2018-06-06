package com.cn.climax.wisdomparking.ui.setting.bank;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.BindBankResponse;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.util.BankCardUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class BankCardAddActivity extends BaseSwipeBackActivity {

    @BindView(R.id.etCardNo)
    EditText etCardNo;
    @BindView(R.id.tvCardOwner)
    TextView tvCardOwner;
    @BindView(R.id.etSelectBank)
    TextView etSelectBank;
    @BindView(R.id.btnBindCardNext)
    Button btnBindCardNext;
    @BindView(R.id.ivSkip2SelectBank)
    LinearLayout ivSkip2SelectBank;

    private String mBankCardNo;
    private String mBankName;
    private String mRealName;
    private String mBankCode;

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
        initClick();
        initView();
    }

    private void initView() {
        String hideName;
        mRealName = SharedUtil.getInstance(this).get(ApiParamsKey.REAL_NAME);
        if (mRealName.length() == 2)
            hideName = mRealName.substring(0, 1) + "*";
        else if (mRealName.length() == 3)
            hideName = mRealName.substring(0, 1) + "**";
        else
            hideName = mRealName.substring(0, 2) + "**";
        tvCardOwner.setText(hideName);
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

    private void initClick() {
        btnBindCardNext.setOnClickListener(new CommonClick());
        ivSkip2SelectBank.setOnClickListener(new CommonClick());
    }

    private class CommonClick extends ForbidQuickClickListener {
        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.ivSkip2SelectBank:
                    startActivityForResult(new Intent(BankCardAddActivity.this, BankCardSelectActivity.class), 99);
                    break;
                case R.id.btnBindCardNext:
                    if (TextUtils.isEmpty(mBankName)) {
                        ToastUtils.show("请选择银行类型");
                        return;
                    }
                    if (TextUtils.isEmpty(mBankCardNo)) {
                        ToastUtils.show("请填写银行卡号");
                        return;
                    }
                    if (!BankCardUtil.checkBankCard(mBankCardNo)){
                        ToastUtils.show("请填写合法的银行卡号");
                        return;
                    }
                    addBankCard();
                    break;
            }
        }
    }

    private void addBankCard() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiParamsKey.BANK_ACCOUNT, mBankCardNo);
        paramsMap.put(ApiParamsKey.BANK_ACCOUNT_NAME, mRealName);
        paramsMap.put(ApiParamsKey.BANK_ACCOUNT_ADDR, "中国");
        paramsMap.put(ApiParamsKey.BANK_CODE, mBankCode);
        JSONObject json = new JSONObject(paramsMap);

        ApiManage.post(ApiHost.getInstance().myBankAdd())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<BindBankResponse>(BankCardAddActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                        ToastUtils.show(msg);
                    }

                    @Override
                    protected void onExecuteSuccess(BindBankResponse bean, Call call) {
                        if (bean.getCode() == 200) {
                            Intent intent = new Intent();
                            intent.putExtra("icon_bank_name", mBankName);
                            intent.putExtra("icon_bank_code", mBankCode);
                            intent.putExtra("icon_bank_no", mBankCardNo);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            ToastUtils.show(bean.getData().toString());
                        }
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {
                        ToastUtils.show(response.message());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 99 && resultCode == RESULT_OK) {
            if (data != null) {
                mBankName = data.getStringExtra("icon_bank_name");
                mBankCode = data.getStringExtra("icon_bank_code");
                etSelectBank.setText(mBankName);
            }
        }
    }
}