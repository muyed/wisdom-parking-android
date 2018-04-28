package com.cn.climax.wisdomparking.ui.main.share;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.uiframework.pickview.builder.TimePickerBuilder;
import com.cn.climax.i_carlib.uiframework.pickview.listener.CustomListener;
import com.cn.climax.i_carlib.uiframework.pickview.listener.OnTimeSelectListener;
import com.cn.climax.i_carlib.uiframework.pickview.view.TimePickerView;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.PublishShareOrder;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.ui.PeterMainActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class PublishShareParkingActivity extends BaseSwipeBackActivity {

    @BindView(R.id.tvSelectStartTime)
    TextView tvSelectStartTime;
    @BindView(R.id.tvSelectStopTime)
    TextView tvSelectStopTime;
    @BindView(R.id.etInputPrice)
    EditText etInputPrice;
    @BindView(R.id.btnPublishParking)
    Button btnPublishParking;

    private TimePickerView pvCustomTimeStart, pvCustomTimeStop;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "发布共享车位");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_publish_share_parking;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initCustomTimePicker();
        etInputPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                tvSelectStartTime.setTextColor(ContextCompat.getColor(PublishShareParkingActivity.this, R.color.text_darker_color));
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
                tvSelectStopTime.setTextColor(ContextCompat.getColor(PublishShareParkingActivity.this, R.color.text_darker_color));
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

        btnPublishParking.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                if (TextUtils.isEmpty(tvSelectStartTime.getText().toString())) {
                    ToastUtils.show("请选择起租时间");
                    return;
                } else if (TextUtils.isEmpty(tvSelectStopTime.getText().toString())) {
                    ToastUtils.show("请选择停租时间");
                    return;
                } else if (TextUtils.isEmpty(etInputPrice.getText().toString())) {
                    ToastUtils.show("请选择出租价格");
                    return;
                }
                publishParking();
            }
        });
    }

    private void publishParking() {
        HashMap<String, String> httpParams = new HashMap<>();
        httpParams.put(ApiParamsKey.CAR_PORT_ID, "2");
        httpParams.put(ApiParamsKey.START_TIME, tvSelectStartTime.getText() + ":00");
        httpParams.put(ApiParamsKey.STOP_TIME, tvSelectStopTime.getText() + ":00");
        httpParams.put(ApiParamsKey.SHARE_PRICE, etInputPrice.getText().toString());
        JSONObject json = new JSONObject(httpParams);

        ApiManage.post(ApiHost.getInstance().publishShare())
                .upJson(json.toString())
                .execute(new WrapJsonBeanCallback<PublishShareOrder>(PublishShareParkingActivity.this) {
                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {

                    }

                    @Override
                    protected void onExecuteSuccess(PublishShareOrder bean, Call call) {
                        if (bean.getCode() == 200) {
                            ToastUtils.show("发布共享单成功");
                            startActivity(new Intent(PublishShareParkingActivity.this, PeterMainActivity.class));
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

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @OnClick({R.id.tvSelectStartTime, R.id.tvSelectStopTime})
    void click(View view) {
        switch (view.getId()) {
            case R.id.tvSelectStartTime: //选择起租时间
                pvCustomTimeStart.show();
                break;
            case R.id.tvSelectStopTime: //选择停租时间
                pvCustomTimeStop.show();
                break;
        }
    }

}
