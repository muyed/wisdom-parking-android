package com.cn.climax.wisdomparking.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.StringUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.i_carlib.util.VerificationUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.widget.SmoothCheckBox;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends BaseSwipeBackActivity {

    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.checkBox)
    SmoothCheckBox checkBox;
    @BindView(R.id.btnRegisterNext)
    Button btnRegisterNext;
    private String mAccount;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "注册");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        checkBox.setChecked(true);
        checkBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (!isChecked) {
                    btnRegisterNext.setBackgroundResource(R.drawable.activity_button_gray_border);
                }else{
                    btnRegisterNext.setBackgroundResource(R.drawable.activity_button_blue_border);
                }
            }
        });
        initClick();
    }

    private void initClick() {
        btnRegisterNext.setOnClickListener(new ForbidClick());
    }

    private class ForbidClick extends ForbidQuickClickListener {
        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.btnRegisterNext:
                    mAccount = etAccount.getText().toString();
                    if (TextUtils.isEmpty(mAccount)) {
                        ToastUtils.show("请输入手机号码");
                    } else if (!StringUtil.isMobileNo(mAccount)) {
                        ToastUtils.show("手机号码不正确");
                    } else {
                        next2Code();
                    }
                    break;
            }
        }
    }

    private void next2Code() {
        ApiManage.get(ApiHost.getInstance().getVerifyCodeR() + mAccount)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            ToastUtils.show("验证码已发送至您的手机，请注意查收");
                            SharedUtil.getInstance(RegisterActivity.this).put(ApiParamsKey.PHONE, mAccount);
                            startActivity(new Intent(RegisterActivity.this, PasswordSetActivity.class));
                        }else{
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

}
