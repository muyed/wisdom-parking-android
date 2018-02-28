package com.cn.climax.wisdomparking.ui.account;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.uiframework.bootstrap.AwesomeTextView;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.StringUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.i_carlib.util.secret.SHAUtils;
import com.cn.climax.wisdomparking.MainActivity;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.LoginResponse;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseSwipeBackActivity implements View.OnClickListener {

    @BindView(R.id.flReplaceLoginType)
    FrameLayout flReplaceLoginType;
    @BindView(R.id.atvSignInWithSnsOrPwd)
    AwesomeTextView atvSignInWithSnsOrPwd;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    private boolean isClickSwitch = false; //是否点击短信验证码登录 默认没有
    private LoginPWDFragment firstFragment;
    private FragmentTransaction transaction;
    private LoginSNSFragment secondFragment;
    private String mAccount;
    private String mPassword;
    private String mMobileNo;
    private String mSnsCode;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle, String rightTitle) {
        super.setToolBar(true, "登录", rightTitle);
    }

    @Override
    protected String isSHowRightTitle() {
        return "注册";
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_login;
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
        Log.e("onCreate: SHAUtils", SHAUtils.getSHA1(LoginActivity.this));

        FragmentManager manager = getFragmentManager();
        transaction = manager.beginTransaction();
        firstFragment = new LoginPWDFragment();
        transaction.replace(R.id.flReplaceLoginType, firstFragment);
        transaction.commit();

        setOnClickRightListener(new OnClickRightBarListener() {
            @Override
            public void click() {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        initClick();
    }

    private void initClick() {
        atvSignInWithSnsOrPwd.setOnClickListener(this);
        btnLogin.setOnClickListener(new ForbidClick());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.atvSignInWithSnsOrPwd:
                if (!isClickSwitch) {
                    isClickSwitch = true;
                    secondFragment = new LoginSNSFragment();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.animator_enter, R.animator.animator_exit)
                            .replace(R.id.flReplaceLoginType, secondFragment)
                            .commit();
                    atvSignInWithSnsOrPwd.setText("服务密码登录");
                } else {
                    isClickSwitch = false;
                    firstFragment = new LoginPWDFragment();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.animator_enter, R.animator.animator_exit)
                            .replace(R.id.flReplaceLoginType, firstFragment).commit();
                    atvSignInWithSnsOrPwd.setText("短信验证码登录");
                }
                break;
        }
    }

    private class ForbidClick extends ForbidQuickClickListener {
        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.btnLogin: //登录
                    if (secondFragment != null) {
                        mMobileNo = ((EditText) secondFragment.getView().findViewById(R.id.etAccount)).getText().toString();
                        mSnsCode = ((EditText) secondFragment.getView().findViewById(R.id.etSnsCode)).getText().toString();
                        if (TextUtils.isEmpty(mMobileNo)) {
                            ToastUtils.show("请输入手机号码");
                        } else if (!StringUtil.isMobileNo(mMobileNo)) {
                            ToastUtils.show("请输入正确手机号码");
                        } else if (TextUtils.isEmpty(mSnsCode)) {
                            ToastUtils.show("请输入验证码");
                        } else {
                            loginEvent();
                        }
                    } else {
                        mAccount = ((EditText) firstFragment.getView().findViewById(R.id.etAccount)).getText().toString();
                        mPassword = ((EditText) firstFragment.getView().findViewById(R.id.etPassword)).getText().toString();
                        if (TextUtils.isEmpty(mAccount)) {
                            ToastUtils.show("请输入用户名");
                        } else if (StringUtil.isNumeric(mAccount)) {
                            ToastUtils.show("用户名不能是全数字");
                        } else if (TextUtils.isEmpty(mPassword)) {
                            ToastUtils.show("请输入密码");
                        } else if (!StringUtil.isValidateOr(mPassword)) {
                            ToastUtils.show("请确保密码为6~20位字母或数字的组成");
                        } else {
                            loginEvent();
                        }
                    }
                    break;
            }
        }
    }

    private void loginEvent() {
        ApiManage.post(ApiHost.getInstance().login())
                .params(isClickSwitch ? "phone" : "username", isClickSwitch ? mMobileNo : mAccount)
                .params(isClickSwitch ? "code" : "password", isClickSwitch ? mSnsCode : mPassword)
                .execute(new WrapJsonBeanCallback<LoginResponse>(LoginActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                    }

                    @Override
                    protected void onExecuteSuccess(LoginResponse bean, Call call) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {

                    }
                });
    }
}

