package com.cn.climax.wisdomparking.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.data.BaseBean;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.PeterMainActivity;
import com.cn.climax.wisdomparking.widget.counttime.AwesomeCountDownTimer;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class PasswordSetActivity extends BaseSwipeBackActivity {

    @BindView(R.id.btnVerify)
    AwesomeCountDownTimer btnVerify;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etVerifyCode)
    EditText etVerifyCode;
    @BindView(R.id.etPassword)
    EditText etPassword;

    private String mMobileNo;
    private String mUserName;
    private String mVerifyCode;
    private String mPassword;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "设置密码");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_password_set;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
//        btnVerify.setStartCountDownText("再次获取");
//        btnVerify.startCountDownTimer(60000, 1000);

        mVerifyCode = getIntent().getStringExtra("regist_code");
        mMobileNo = SharedUtil.getInstance(PasswordSetActivity.this).get(ApiParamsKey.PHONE);
    }

    @OnClick({R.id.btnVerify, R.id.btnRegister})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btnVerify: //重新获取验证码
                getVerifyCode();
                break;
            case R.id.btnRegister: //注册
                mUserName = etUserName.getText().toString();
                mPassword = etPassword.getText().toString();
                if (TextUtils.isEmpty(mUserName)) {
                    ToastUtils.show("请输入用户名");
                } else if (TextUtils.isEmpty(mPassword)) {
                    ToastUtils.show("请输入密码");
                } else {
                    registerApp();
                }
                break;
        }
    }

    private void getVerifyCode() {
        ApiManage.get(ApiHost.getInstance().getVerifyCodeR() + mMobileNo)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ToastUtils.show("验证码已发送至您的手机，请注意查收");
                        btnVerify.setStartCountDownText("再次获取");
                        btnVerify.startCountDownTimer(60000, 1000);
                    }
                });
    }

    private void registerApp() {

        HashMap<String, String> httpParams = new HashMap<>();
        httpParams.put(ApiParamsKey.PHONE, mMobileNo);
        httpParams.put(ApiParamsKey.USER_NAME, mUserName);
        httpParams.put(ApiParamsKey.VERIFY_CODE, mVerifyCode);
        httpParams.put(ApiParamsKey.PASSWORD, mPassword);
        JSONObject json = new JSONObject(httpParams);
        
        ApiManage.post(ApiHost.getInstance().register())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<BaseBean>(PasswordSetActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                        ToastUtils.show(msg);
                    }

                    @Override
                    protected void onExecuteSuccess(BaseBean bean, Call call) {
                        ToastUtils.show("注册成功");
                        startActivity(new Intent(PasswordSetActivity.this, PeterMainActivity.class));
//                        startActivity(new Intent(PasswordSetActivity.this, ModifyUserInfoActivity.class));
                        finish();
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {

                    }
                });
    }

}
