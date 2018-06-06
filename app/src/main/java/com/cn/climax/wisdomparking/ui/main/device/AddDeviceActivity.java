package com.cn.climax.wisdomparking.ui.main.device;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.okgo.http.GsonConvert;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.CarPortBindBean;
import com.cn.climax.wisdomparking.data.response.CommunityAuthListResponse;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.google.gson.JsonObject;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class AddDeviceActivity extends BaseSwipeBackActivity {

    @BindView(R.id.tvCarPortCode)
    TextView tvCarPortCode;
    @BindView(R.id.tvCarPortAddr)
    TextView tvCarPortAddr;
    @BindView(R.id.btnBindCarPort)
    Button btnBindCarPort;
    @BindView(R.id.etInputBindCode)
    EditText etInputBindCode;

    private CommunityAuthListResponse.CarportListBean mCarPortBean;
    private String mCarPortAddress;
    private String mCarPortBindCode;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "添加设备");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_add_device;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        mCarPortBean = (CommunityAuthListResponse.CarportListBean) getIntent().getSerializableExtra("carports_info");
        mCarPortAddress = getIntent().getStringExtra("carports_address");
        if (mCarPortBean != null) {
            tvCarPortCode.setText(mCarPortBean.getCarportNum());
            tvCarPortAddr.setText(mCarPortAddress);
        }
        mCarPortBindCode = etInputBindCode.getText().toString();
        etInputBindCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCarPortBindCode = s.toString();
            }
        });
        btnBindCarPort.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                if (TextUtils.isEmpty(mCarPortBindCode)) {
                    ToastUtils.show("请填写绑定码");
                    return;
                }
//                getAliPayOrder("CD20180418234539356044");
                bindCarPort();
            }
        });
    }

    private void bindCarPort() {
        HashMap<String, String> httpParams = new HashMap<>();
        httpParams.put(ApiParamsKey.CAR_PORT_ID, "3");
        httpParams.put(ApiParamsKey.CAR_PORT_BIND_CODE, "222222");
        JSONObject json = new JSONObject(httpParams);

        ApiManage.post(ApiHost.getInstance().bindCarPort())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<CarPortBindBean>(AddDeviceActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {

                    }

                    @Override
                    protected void onExecuteSuccess(CarPortBindBean bean, Call call) {
                        getAliPayOrder(GsonConvert.toJSONString(bean.getData()));
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {

                    }

                    @Override
                    protected boolean setDialogShow() {
                        return false;
                    }
                });
    }

    private void getAliPayOrder(String orderNo) {
        ApiManage.get(ApiHost.getInstance().getAliPayOrder() + orderNo)
                .tag(this)
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            SharedUtil.getInstance(AddDeviceActivity.this).put(ApiParamsKey.IS_AUTH_PARKING_SPACE,true);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String mDataJson = (String) jsonObject.get("data");
                                startActivity(new Intent(AddDeviceActivity.this, ParkingSpacePayActivity.class).putExtra("order_no", mDataJson));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

}
