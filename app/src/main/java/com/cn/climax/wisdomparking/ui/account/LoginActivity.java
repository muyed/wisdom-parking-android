package com.cn.climax.wisdomparking.ui.account;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.platform.PlatformConfig;
import com.cn.climax.i_carlib.platform.PlatformType;
import com.cn.climax.i_carlib.platform.SocialApi;
import com.cn.climax.i_carlib.platform.listener.AuthListener;
import com.cn.climax.i_carlib.uiframework.bootstrap.AwesomeTextView;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.StringUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.i_carlib.util.secret.SHAUtils;
import com.cn.climax.wisdomparking.data.response.CarLicenseMineBean;
import com.cn.climax.wisdomparking.ui.HomeActivity;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.LoginResponse;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.ui.PeterMainActivity;
import com.cn.climax.wisdomparking.util.HelperFromPermission;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseSwipeBackActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.flReplaceLoginType)
    FrameLayout flReplaceLoginType;
    @BindView(R.id.atvSignInWithSnsOrPwd)
    AwesomeTextView atvSignInWithSnsOrPwd;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.ivWeChatLogin)
    ImageView ivWeChatLogin;
    @BindView(R.id.ivQQLogin)
    ImageView ivQQLogin;
    @BindView(R.id.ivSinaLogin)
    ImageView ivSinaLogin;
    @BindView(R.id.btnRegister2New)
    Button btnRegister2New;

    private long exitTime = 0;

    private static final int REQUEST_LOCATION = 1;
    private static final String WX_APPID = "your wx appid";    //申请的wx appid
    private static final String QQ_APPID = "your qq appid";    //申请的qq appid
    private static final String SINA_WB_APPKEY = "your sina wb appkey";       //申请的新浪微博 appkey

    private boolean isClickSwitch = false; //是否点击短信验证码登录 默认没有
    private FragmentTransaction transaction;
    private LoginPWDFragment firstFragment;
    private LoginSNSFragment secondFragment;
    private String mAccount;
    private String mPassword;
    private String mMobileNo;
    private String mSnsCode;
    private SocialApi mSocialApi;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(false, "登录");
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
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(false);
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        Log.e("onCreate: SHAUtils", SHAUtils.getSHA1(LoginActivity.this));

        FragmentManager manager = getFragmentManager();
        transaction = manager.beginTransaction();
        firstFragment = new LoginPWDFragment();
        transaction.replace(R.id.flReplaceLoginType, firstFragment);
        transaction.commit();

        initThirdPlatform();
        initClick();
    }

    private void initThirdPlatform() {
        PlatformConfig.setWeixin(WX_APPID);
        PlatformConfig.setQQ(QQ_APPID);
//        PlatformConfig.setSinaWB(SINA_WB_APPKEY);

        mSocialApi = SocialApi.get(getApplicationContext());
    }

    private void initClick() {
        atvSignInWithSnsOrPwd.setOnClickListener(this);
        btnLogin.setOnClickListener(new ForbidClick());
        btnRegister2New.setOnClickListener(new ForbidClick());

        ivWeChatLogin.setOnClickListener(new ForbidClick());
        ivQQLogin.setOnClickListener(new ForbidClick());
        ivSinaLogin.setOnClickListener(new ForbidClick());
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
                    atvSignInWithSnsOrPwd.setText("切换至密码登录");
                } else {
                    isClickSwitch = false;
                    firstFragment = new LoginPWDFragment();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.animator_enter, R.animator.animator_exit)
                            .replace(R.id.flReplaceLoginType, firstFragment).commit();
                    atvSignInWithSnsOrPwd.setText("切换至手机登录");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //申请权限
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "need access external storage", REQUEST_LOCATION, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(getApplicationContext(), "前往设置开启访问存储空间权限", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public class MyAuthListener implements AuthListener {
        @Override
        public void onComplete(PlatformType platform_type, Map<String, String> map) {
            Toast.makeText(LoginActivity.this, platform_type + " login onComplete", Toast.LENGTH_SHORT).show();
            Log.i("tsy", "login onComplete:" + map);
        }

        @Override
        public void onError(PlatformType platform_type, String err_msg) {
            Toast.makeText(LoginActivity.this, platform_type + " login onError:" + err_msg, Toast.LENGTH_SHORT).show();
            Log.i("tsy", "login onError:" + err_msg);
        }

        @Override
        public void onCancel(PlatformType platform_type) {
            Toast.makeText(LoginActivity.this, platform_type + " login onCancel", Toast.LENGTH_SHORT).show();
            Log.i("tsy", "login onCancel");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSocialApi.onActivityResult(requestCode, resultCode, data);
    }

    private class ForbidClick extends ForbidQuickClickListener {
        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.btnLogin: //登录
                    if (secondFragment != null && isClickSwitch) {
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
                case R.id.btnRegister2New://注册新用户
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    break;
                case R.id.ivWeChatLogin: //微信登录
                    mSocialApi.doOauthVerify(LoginActivity.this, PlatformType.WEIXIN, new MyAuthListener());
                    break;
                case R.id.ivQQLogin: //QQ登录
                    mSocialApi.doOauthVerify(LoginActivity.this, PlatformType.QQ, new MyAuthListener());
                    break;
                case R.id.ivSinaLogin: //新浪登录
//                    mSocialApi.doOauthVerify(LoginActivity.this, PlatformType.SINA_WB, new MyAuthListener());
                    break;
            }
        }
    }

    private void loginEvent() {
        HashMap<String, String> httpParams = new HashMap<>();
        httpParams.put(ApiParamsKey.USER_NAME, isClickSwitch ? mMobileNo : mAccount);
        httpParams.put(ApiParamsKey.PASSWORD, isClickSwitch ? mSnsCode : mPassword);
        httpParams.put(ApiParamsKey.TYPE, "1");
        JSONObject json = new JSONObject(httpParams);

        ApiManage.post(ApiHost.getInstance().login())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<LoginResponse>(LoginActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                        ToastUtils.show(msg);
                    }

                    @Override
                    protected void onExecuteSuccess(LoginResponse bean, Call call) {
                        SharedUtil.getInstance(LoginActivity.this).put("is_login_success", true);
                        SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.USER_NAME, isClickSwitch ? mMobileNo : mAccount);
                        SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.REAL_NAME, bean.getRealName());
                        SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.PASSWORD, isClickSwitch ? mSnsCode : mPassword);
                        SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.IS_AUTH, !TextUtils.isEmpty(bean.getRealName()));
                        SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.IS_AUTH_COMMUNITY, bean != null && bean.getCommunityList() != null && bean.getCommunityList().size() > 0);
                        SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.IS_AUTH_PARKING_SPACE, bean != null && bean.getUserCarportList() != null && bean.getUserCarportList().size() > 0);
                        SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.ACCOUNT_DEPOSIT_AMOUNT, String.valueOf(bean.getAccountCashConf()));
                        SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.CARPORT_DEPOSIT_AMOUNT, String.valueOf(bean.getCarportCashConf()));
                        judgeUserIsAddCarLicense(bean);
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {
                        ToastUtils.show(response.message());
                    }
                });
    }

    private void judgeUserIsAddCarLicense(final LoginResponse bean) {
        ApiManage.get(ApiHost.getInstance().getMyCarLicenseList())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                List<CarLicenseMineBean> carLicenseMineBeen = com.alibaba.fastjson.JSONObject.parseArray(String.valueOf(json.get("data")), CarLicenseMineBean.class);

                                if (carLicenseMineBeen != null && carLicenseMineBeen.size() > 0) {
                                    SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.IS_ADD_CAR_LICENSE, true);
                                } else {
                                    SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.IS_ADD_CAR_LICENSE, false);
                                }
                                startActivity(new Intent(LoginActivity.this, PeterMainActivity.class).putExtra("user_info_bean", bean));
                                finish();
                            } catch (JSONException e) {
                                SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.IS_ADD_CAR_LICENSE, false);
                                e.printStackTrace();
                            }
                        } else {
                            SharedUtil.getInstance(LoginActivity.this).put(ApiParamsKey.IS_ADD_CAR_LICENSE, false);
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 1000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                SharedUtil.getInstance(getApplicationContext()).put("last_launch", System.currentTimeMillis());
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

