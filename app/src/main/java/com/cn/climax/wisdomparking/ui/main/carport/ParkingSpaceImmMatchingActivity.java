package com.cn.climax.wisdomparking.ui.main.carport;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.uiframework.pickview.builder.TimePickerBuilder;
import com.cn.climax.i_carlib.uiframework.pickview.listener.CustomListener;
import com.cn.climax.i_carlib.uiframework.pickview.listener.OnTimeSelectListener;
import com.cn.climax.i_carlib.uiframework.pickview.view.TimePickerView;
import com.cn.climax.i_carlib.util.SharedUtil;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeCustomActivity;
import com.cn.climax.wisdomparking.data.response.CarLicenseMineBean;
import com.cn.climax.wisdomparking.data.response.NearbyParkingMineBean;
import com.cn.climax.wisdomparking.data.response.PublishShareOrder;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.ui.PeterMainActivity;
import com.cn.climax.wisdomparking.ui.main.community.NearbySearchActivity;
import com.cn.climax.wisdomparking.ui.main.device.AddLicensePlateActivity;
import com.cn.climax.wisdomparking.ui.main.device.LicenseManagerListActivity;
import com.cn.climax.wisdomparking.ui.main.home.city.CityPickerActivity;
import com.cn.climax.wisdomparking.ui.main.share.PublishShareParkingActivity;
import com.cn.climax.wisdomparking.util.GlobalVerificateUtils;
import com.cn.climax.wisdomparking.util.SharedPreferenceUtil;
import com.cn.climax.wisdomparking.util.StringUtil;
import com.cn.climax.wisdomparking.util.TimeUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ParkingSpaceImmMatchingActivity extends BaseSwipeCustomActivity {

    @BindView(R.id.atvToolBarMainSearch)
    EditText atvToolBarMainSearch;
    @BindView(R.id.atvLeftTitle)
    TextView atvLeftTitle;
    @BindView(R.id.btnImmediatelyMatching)
    Button btnImmediatelyMatching;
    @BindView(R.id.tvCarPortCode)
    TextView tvCarPortCode; //车位锁编号
    @BindView(R.id.tvStopParkingTime)
    TextView tvStopParkingTime;
    @BindView(R.id.tvStopParkingSpace)
    TextView tvStopParkingSpace;
    @BindView(R.id.tvParkingAddr)
    TextView tvParkingAddr;
    @BindView(R.id.etSelectPhoneNo)
    EditText etSelectPhoneNo;
    @BindView(R.id.tvSelectCarLicenseNo)
    TextView tvSelectCarLicenseNo;

    @BindView(R.id.llSkip2SelectCarLicense)
    LinearLayout llSkip2SelectCarLicense;
    @BindView(R.id.llSelectStartTime)
    LinearLayout llSelectStartTime;
    @BindView(R.id.llSelectEndTime)
    LinearLayout llSelectEndTime;

    @BindView(R.id.tvSelectStartTime)
    TextView tvSelectStartTime;
    @BindView(R.id.tvSelectStopTime)
    TextView tvSelectStopTime;
    @BindView(R.id.tvParkingUnitPrice)
    TextView tvParkingUnitPrice;

    private TimePickerView pvCustomTimeStart, pvCustomTimeStop;
    private NearbyParkingMineBean mDestNaviBean;
    private CarLicenseMineBean mCarLicenseBean;

    private String mSharePhone;
    private String mShareStartTime;
    private String mShareStopTime;

    @Override
    protected int initContentView() {
        return R.layout.activity_parking_space_immediately_matching;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        atvToolBarMainSearch.setHint("请搜索关键字");
        atvLeftTitle.setText("地图");
        mDestNaviBean = (NearbyParkingMineBean) getIntent().getSerializableExtra("destination_navi_info");

        initClick();
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        tvCarPortCode.setText(mDestNaviBean.getCarportNum());
        tvStopParkingTime.setText("可停至" + TimeUtils.getDateToString(TimeUtils.getTimeStamp(mDestNaviBean.getStopTime(), "yyyy-MM-dd"))
                + " " + mDestNaviBean.getStopTime().substring(mDestNaviBean.getStopTime().lastIndexOf(" "), mDestNaviBean.getStopTime().lastIndexOf(" ") + 6));
        tvStopParkingSpace.setText(mDestNaviBean.getCommunityName());
        tvParkingAddr.setText(mDestNaviBean.getAddr());
        tvParkingUnitPrice.setText(mDestNaviBean.getPrice() + "元/小时");

        final String regPhone = SharedUtil.getInstance(ParkingSpaceImmMatchingActivity.this).get(ApiParamsKey.PHONE);
        etSelectPhoneNo.setHint(TextUtils.isEmpty(regPhone) ? "请填写手机号码" : regPhone);
        etSelectPhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mSharePhone = s.toString();
                } else {
                    mSharePhone = regPhone;
                }
            }
        });

        initCustomTimePicker();
    }

    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();

        int mYear = startDate.get(Calendar.YEAR); // 获取当前年份
        int mMonth = startDate.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = startDate.get(Calendar.DAY_OF_MONTH);// 获取当日期
        int mWeek = startDate.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
        int mHour = startDate.get(Calendar.HOUR_OF_DAY);//时
        int mMinute = startDate.get(Calendar.MINUTE);//分
//        int mSecond = startDate.get(Calendar.SECOND);//秒

        startDate.set(mYear, mMonth, mDay, mHour, mMinute);
        Calendar endDate = Calendar.getInstance();
        endDate.set(mYear, mMonth, mDay + 1, mHour, mMinute);
        //时间选择器 ，自定义布局
        pvCustomTimeStart = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvSelectStartTime.setText(getTime(date));
                mShareStartTime = getTime(date);
                tvSelectStartTime.setTextColor(ContextCompat.getColor(ParkingSpaceImmMatchingActivity.this, R.color.text_darker_color));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTimeStart.returnData();
                                pvCustomTimeStart.dismiss();
                            }
                        });
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTimeStart.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(16)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 0, 0, 0)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
        pvCustomTimeStop = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvSelectStopTime.setText(getTime(date));
                mShareStopTime = getTime(date);
                tvSelectStopTime.setTextColor(ContextCompat.getColor(ParkingSpaceImmMatchingActivity.this, R.color.text_darker_color));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTimeStop.returnData();
                                pvCustomTimeStop.dismiss();
                            }
                        });
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTimeStop.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(16)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 0, 0, 0)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();

    }

    private void initClick() {
        llSkip2SelectCarLicense.setOnClickListener(new CommonClick());
        llSelectStartTime.setOnClickListener(new CommonClick());
        llSelectEndTime.setOnClickListener(new CommonClick());
        btnImmediatelyMatching.setOnClickListener(new CommonClick());
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.llSkip2SelectCarLicense:
                    if (!GlobalVerificateUtils.getInstance(ParkingSpaceImmMatchingActivity.this).isEnableOption(ParkingSpaceImmMatchingActivity.this))
                        return;
                    startActivityForResult(new Intent(ParkingSpaceImmMatchingActivity.this, LicenseManagerListActivity.class).putExtra("is_from_select", true), 99);
                    break;
                case R.id.llSelectStartTime: //选择起始时间
                    pvCustomTimeStart.show();
                    break;
                case R.id.llSelectEndTime: //选择结束时间
                    pvCustomTimeStop.show();
                    break;
                case R.id.btnImmediatelyMatching:
                    matchingShareParking();
                    break;
            }
        }
    }

    private void matchingShareParking() {
        if (mCarLicenseBean == null || TextUtils.isEmpty(mCarLicenseBean.getLicense())) {
            ToastUtils.show("请填写预约车牌");
            return;
        }
        if (TextUtils.isEmpty(mShareStartTime)) {
            ToastUtils.show("请选择预约开始时间");
            return;
        }
        if (TextUtils.isEmpty(mShareStopTime)) {
            ToastUtils.show("请选择预约结束时间");
            return;
        }
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ApiParamsKey.PARKING_SHARE_ID, mDestNaviBean.getId() + "");
        paramsMap.put(ApiParamsKey.APPOINTMENT_START_TIME, tvSelectStartTime.getText() + ":00");
        paramsMap.put(ApiParamsKey.APPOINTMENT_STOP_TIME, tvSelectStopTime.getText() + ":00");
        paramsMap.put(ApiParamsKey.CAR_LICENSE_NUM, mCarLicenseBean.getLicense());
        if (!TextUtils.isEmpty(mSharePhone)) {
            paramsMap.put(ApiParamsKey.PHONE, mSharePhone);
        }
        JSONObject json = new JSONObject(paramsMap);

        ApiManage.post(ApiHost.getInstance().matching())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<PublishShareOrder>(ParkingSpaceImmMatchingActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                    }

                    @Override
                    protected void onExecuteSuccess(PublishShareOrder bean, Call call) {
                        if (bean.getCode() == 200) {
                            startActivity(new Intent(ParkingSpaceImmMatchingActivity.this, ParkingSpaceMatchActivity.class));
                        }
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

    @OnClick({R.id.llNavBackToPre, R.id.allToLocation})
    void click(View view) {
        switch (view.getId()) {
            case R.id.llNavBackToPre:
                finish();
                break;
            case R.id.allToLocation: //地图跳转
//                startActivityForResult(new Intent(NearbySearchActivity.this, CityPickerActivity.class), 99);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            if (data != null) {
                mCarLicenseBean = (CarLicenseMineBean) data.getSerializableExtra("car_license_num");
                tvSelectCarLicenseNo.setText(mCarLicenseBean.getLicense());
                tvSelectCarLicenseNo.setTextColor(ContextCompat.getColor(ParkingSpaceImmMatchingActivity.this, R.color.text_color_dark));
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
