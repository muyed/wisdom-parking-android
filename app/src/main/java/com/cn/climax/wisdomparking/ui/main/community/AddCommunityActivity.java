package com.cn.climax.wisdomparking.ui.main.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.uiframework.citypicker.listener.OnCityWheelComfirmListener;
import com.cn.climax.i_carlib.uiframework.citypicker.ppw.CityWheelPickerPopupWindow;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.AuthCommunityResponse;
import com.cn.climax.wisdomparking.data.response.CommunityAuthListResponse;
import com.cn.climax.wisdomparking.data.response.CommunityListResponse;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class AddCommunityActivity extends BaseSwipeBackActivity {

    @BindView(R.id.llSelectedAddress)
    LinearLayout llSelectedAddress; //选择小区
    @BindView(R.id.tvDisplayCommunityAddress)
    TextView tvDisplayCommunityAddress; //小区地址显示区域
    @BindView(R.id.etDisplayFloorNo)
    EditText etDisplayFloorNo; //楼号
    @BindView(R.id.etDisplayUnitNo)
    EditText etDisplayUnitNo; //单元号
    @BindView(R.id.etDisplayRoomNo)
    EditText etDisplayRoomNo; //房间号

    private String mCommunityId;
    private String mFloorNo;
    private String mUnitNo;
    private String mHouseNo;

    private CityWheelPickerPopupWindow wheelPickerPopupWindow;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "小区认证");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_add_community;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
//        initAddrPicker();
        initCommunityInfo();
    }

    private void initCommunityInfo() {
        etDisplayFloorNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mFloorNo = s.toString();
            }
        });
        etDisplayUnitNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mUnitNo = s.toString();
            }
        });
        etDisplayRoomNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mHouseNo = s.toString();
            }
        });
    }

    private void initAddrPicker() {
        wheelPickerPopupWindow = new CityWheelPickerPopupWindow(getActivity());
        wheelPickerPopupWindow.setListener(new OnCityWheelComfirmListener() {
            @Override
            public void onSelected(String Province, String City, String District, String PostCode) {
                tvDisplayCommunityAddress.setText(Province + City + District);
                tvDisplayCommunityAddress.setTextColor(ContextCompat.getColor(AddCommunityActivity.this, R.color.text_darker_color));
            }
        });
    }

    @OnClick({R.id.llSelectedAddress, R.id.btnConfirmAndSubmit})
    void click(View view) {
        switch (view.getId()) {
            case R.id.llSelectedAddress: //选择小区地址
//                wheelPickerPopupWindow.show();
                startActivityForResult(new Intent(AddCommunityActivity.this, CommunityListActivity.class), 99);
                break;
            case R.id.btnConfirmAndSubmit: //确认并提交
                if (TextUtils.isEmpty(mCommunityId)) {
                    ToastUtils.show("请先选择小区");
                } else if (TextUtils.isEmpty(mFloorNo)) {
                    ToastUtils.show("请输入小区楼号");
                } else if (TextUtils.isEmpty(mUnitNo)) {
                    ToastUtils.show("请输入小区单元号");
                } else if (TextUtils.isEmpty(mHouseNo)) {
                    ToastUtils.show("请输入门牌号");
                } else {
                    submitAndBindCommunity();
                }
                break;
        }
    }

    private void submitAndBindCommunity() {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiParamsKey.COMMUNITY_ID, mCommunityId);
        paramsMap.put(ApiParamsKey.FLOOR_NO, mFloorNo);
        paramsMap.put(ApiParamsKey.UNIT_NO, mUnitNo);
        paramsMap.put(ApiParamsKey.HOUSE_NO, mHouseNo);
        JSONObject json = new JSONObject(paramsMap);

        ApiManage.post(ApiHost.getInstance().authCommunity())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<AuthCommunityResponse>(this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                    }

                    @Override
                    protected void onExecuteSuccess(AuthCommunityResponse bean, Call call) {
                        if (bean.getCode() == 200) {
                            startActivity(new Intent(AddCommunityActivity.this, AddCommunitySuccessActivity.class));
                            SharedUtil.getInstance(AddCommunityActivity.this).put(ApiParamsKey.IS_AUTH_COMMUNITY, true);
                        } else {
                            ToastUtils.show(bean.getErrMsg());
                        }
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {
                    }
                });

    }

    private CommunityListResponse mCommunityBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            if (data != null) {
                mCommunityBean = (CommunityListResponse) data.getSerializableExtra(ApiParamsKey.COMMUNITY_BEAN);
                mCommunityId = mCommunityBean.getId() + "";
                tvDisplayCommunityAddress.setText(mCommunityBean.getCommunityName());
                tvDisplayCommunityAddress.setTextColor(ContextCompat.getColor(AddCommunityActivity.this, R.color.text_darker_color));
            }
        }
    }
}
