package com.cn.climax.wisdomparking.ui.main.device;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.LoginResponse;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.util.StringUtil;
import com.cn.climax.wisdomparking.widget.car.LicensePlateView;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class AddLicensePlateActivity extends BaseSwipeBackActivity implements LicensePlateView.InputListener {

    @BindView(R.id.main_rl_container)
    RelativeLayout mContainer;
    @BindView(R.id.activity_lpv)
    LicensePlateView mPlateView;

    private String mInputContent;

    @Override
    protected int initContentView() {
        return R.layout.activity_license_plate_input;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "添加车牌");
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        mPlateView.setInputListener(this);

        mPlateView.setKeyboardContainerLayout(mContainer);
        mPlateView.showLastView();
        mPlateView.hideLastView();
        mPlateView.onSetTextColor(R.color.colorAccent);
    }

    @OnClick({R.id.tvAddCarPlateNo})
    void click(View view) {
        switch (view.getId()) {
            case R.id.tvAddCarPlateNo:
                if (TextUtils.isEmpty(mInputContent)) {
                    ToastUtils.show("请输入车牌号");
                    return;
                } else if (!StringUtil.isCarPlateNo(mInputContent)) {
                    ToastUtils.show("请正确输入车牌号");
                    return;
                }
                addCarPlateNo();
                break;
        }
    }

    private void addCarPlateNo() {
        HashMap<String, String> httpParams = new HashMap<>();
        httpParams.put(ApiParamsKey.CAR_LICENSE, mInputContent);
        JSONObject json = new JSONObject(httpParams);

        ApiManage.post(ApiHost.getInstance().addCarLicense())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<LoginResponse>(AddLicensePlateActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                        ToastUtils.show(msg);
                    }

                    @Override
                    protected void onExecuteSuccess(LoginResponse bean, Call call) {
                        startActivity(new Intent(AddLicensePlateActivity.this, ParkingSpacePayActivity.class));
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {
                        ToastUtils.show(response.message());
                    }
                });
    }

    @Override
    public void inputComplete(String content) {
        mInputContent = content;
    }

    @Override
    public void deleteContent() {

    }
}
