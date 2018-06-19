package com.cn.climax.wisdomparking.ui.main.share;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.LoginResponse;
import com.cn.climax.wisdomparking.data.response.ParkingSpaceMineBean;
import com.cn.climax.wisdomparking.data.response.PublishShareOrder;
import com.cn.climax.wisdomparking.http.WrapJsonBeanCallback;
import com.cn.climax.wisdomparking.ui.main.carport.ParkingSpaceMineActivity;
import com.cn.climax.wisdomparking.ui.main.carport.ParkingSpaceAddActivity;
import com.cn.climax.wisdomparking.util.TimeUtils;
import com.cn.climax.wisdomparking.widget.NoUnderlineSpan;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class PublishShareParkingActivity extends BaseSwipeBackActivity {

    @BindView(R.id.tvParkingAddress)
    TextView tvParkingAddress;
    @BindView(R.id.tvParkingSpaceLocation)
    TextView tvParkingSpaceLocation;
    @BindView(R.id.tvCarLicenseNo)
    TextView tvCarLicenseNo;
    @BindView(R.id.tvSelectStartTime)
    TextView tvSelectStartTime;
    @BindView(R.id.tvSelectStopTime)
    TextView tvSelectStopTime;
    @BindView(R.id.etInputPrice)
    EditText etInputPrice;
    @BindView(R.id.etInputPublishRemark)
    EditText etInputPublishRemark;
    @BindView(R.id.btnPublishParking)
    Button btnPublishParking;
    @BindView(R.id.llSkip2ChoseCarport)
    LinearLayout llSkip2ChoseCarport;

    @BindView(R.id.llShowCarPortArea)
    LinearLayout llShowCarPortArea;
    @BindView(R.id.llNoCarPortArea)
    LinearLayout llNoCarPortArea;
    @BindView(R.id.tvNoCarSpaceHint)
    TextView tvNoCarSpaceHint;
    @BindView(R.id.ivBindIcon)
    ImageView ivBindIcon;

    private TimePickerView pvCustomTimeStart, pvCustomTimeStop;
    private String mInputRentPrice;
    private String mInputRentRemark;
    private boolean isFromMineParking; //是否是从我的车位跳转
    private ParkingSpaceMineBean mParkingSpaceBean;
    private List<LoginResponse.CommunityListBean> mCommunityListBean = new ArrayList<>();
    private int unBindCount = 0;
    private int unCertCommunityCount = 0; //未认证小区计数
    private int authedCommunityCount = 0; //已认证小区计数
    private int deniededCommunityCount = 0; //被驳回小区计数
    private List<Integer> unBindListCount = new ArrayList<>();

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
        isFromMineParking = getIntent().getBooleanExtra("is_publish_mine", false);
        mCommunityListBean = (List<LoginResponse.CommunityListBean>) getIntent().getSerializableExtra("community_bean");
        mParkingSpaceBean = (ParkingSpaceMineBean) getIntent().getSerializableExtra("parking_mine_bean");
        initView();
        initClick();
    }

    private void initView() {
        etInputPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mInputRentPrice = s.toString();
                } else {
                    mInputRentPrice = null;
                }
            }
        });
        etInputPublishRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mInputRentRemark = s.toString();
                } else {
                    mInputRentRemark = null;
                }
            }
        });
        if (mParkingSpaceBean != null) {
            llShowCarPortArea.setVisibility(View.VISIBLE);
            llNoCarPortArea.setVisibility(View.GONE);
            initData();
        } else {
            if (mCommunityListBean != null && mCommunityListBean.size() > 0) {
                llShowCarPortArea.setVisibility(View.GONE);
                llNoCarPortArea.setVisibility(View.VISIBLE);
                for (int i = 0; i < mCommunityListBean.size(); i++) {
                    if (mCommunityListBean.get(i).getCarportList() != null && mCommunityListBean.get(i).getCarportList().size() > 0) {
                        for (int j = 0; j < mCommunityListBean.get(i).getCarportList().size(); j++) {
                            if (mCommunityListBean.get(i).getCarportList().get(j).isBind()) {
                                llShowCarPortArea.setVisibility(View.VISIBLE);
                                llNoCarPortArea.setVisibility(View.GONE);

                                tvParkingAddress.setText(mCommunityListBean.get(i).getCommunityName());
                                tvParkingSpaceLocation.setText(mCommunityListBean.get(i).getAddr());
                                tvCarLicenseNo.setText(mCommunityListBean.get(i).getCarportList().get(j).getCarportNum());
                            } else {
                                unBindCount++;
                            }
                        }

                        if (unBindCount == mCommunityListBean.get(i).getCarportList().size()) {
                            unBindListCount.add(unBindCount);
                        }
                    }
                }
                for (int i = 0; i < mCommunityListBean.size(); i++) {
                    if (mCommunityListBean.get(i).getType() == 1) { //审核中
                        unCertCommunityCount++;
                    } else if (mCommunityListBean.get(i).getType() == 2) { //已认证
                        authedCommunityCount++;
                    } else { //被驳回
                        deniededCommunityCount++;
                    }
                }

                if (unCertCommunityCount == mCommunityListBean.size()) {
                    ivBindIcon.setImageResource(R.drawable.icon_identifing);
                    tvNoCarSpaceHint.setText("小区认证中，请耐心等待审核");
                } else if (deniededCommunityCount == mCommunityListBean.size()) {
                    ivBindIcon.setImageResource(R.drawable.icon_denied);
                    tvNoCarSpaceHint.setText("小区认证已驳回，如需操作请修改重新提交");
                } else if (unCertCommunityCount + deniededCommunityCount == mCommunityListBean.size()) {
                    ivBindIcon.setImageResource(R.drawable.icon_community);
                    tvNoCarSpaceHint.setText("还没有认证合格的小区");
                    tvNoCarSpaceHint.setTextColor(ContextCompat.getColor(PublishShareParkingActivity.this, R.color.white));
                    llNoCarPortArea.setBackgroundColor(ContextCompat.getColor(PublishShareParkingActivity.this, R.color.colorPrimary));
                } else {
                    if (unBindListCount.size() == mCommunityListBean.size()) { //如果每个小区下的全是未绑定的车位
                        ivBindIcon.setImageResource(R.drawable.icon_bind_car);
                        tvNoCarSpaceHint.setText("还没有绑定车位锁，快去绑定吧");

                        SpannableStringBuilder regTipBuilder = new SpannableStringBuilder(tvNoCarSpaceHint.getText().toString());
                        ForegroundColorSpan blueSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
                        regTipBuilder.setSpan(blueSpan, 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        BackgroundColorSpan whiteSpan = new BackgroundColorSpan(ContextCompat.getColor(this, R.color.white));
                        regTipBuilder.setSpan(whiteSpan, 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        regTipBuilder.setSpan(new AbsoluteSizeSpan(32), 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        NoUnderlineSpan clickSpan = new NoUnderlineSpan() {
                            @Override
                            public void onClick(View widget) {
                                startActivityForResult(new Intent(PublishShareParkingActivity.this, ParkingSpaceMineActivity.class).putExtra("is_enable_select", true), 99);
                            }
                        };
                        regTipBuilder.setSpan(clickSpan, 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvNoCarSpaceHint.setText(regTipBuilder);
                        tvNoCarSpaceHint.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                }
            } else {
                llShowCarPortArea.setVisibility(View.GONE);
                llNoCarPortArea.setVisibility(View.VISIBLE);

                SpannableStringBuilder regTipBuilder = new SpannableStringBuilder(tvNoCarSpaceHint.getText().toString());
                ForegroundColorSpan blueSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
                regTipBuilder.setSpan(blueSpan, 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                BackgroundColorSpan whiteSpan = new BackgroundColorSpan(ContextCompat.getColor(this, R.color.white));
                regTipBuilder.setSpan(whiteSpan, 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                regTipBuilder.setSpan(new AbsoluteSizeSpan(32), 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                NoUnderlineSpan clickSpan = new NoUnderlineSpan() {
                    @Override
                    public void onClick(View widget) {
                        startActivityForResult(new Intent(PublishShareParkingActivity.this, ParkingSpaceAddActivity.class), 199);
                    }
                };
                regTipBuilder.setSpan(clickSpan, 11, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvNoCarSpaceHint.setText(regTipBuilder);
                tvNoCarSpaceHint.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
        initCustomTimePicker();
    }

    private void initData() {
        tvParkingAddress.setText(mParkingSpaceBean.getCommunityName());
        tvParkingSpaceLocation.setText(mParkingSpaceBean.getAddr());
        tvCarLicenseNo.setText(mParkingSpaceBean.getCarportNum());
    }

    private void initClick() {
        llSkip2ChoseCarport.setOnClickListener(new CommonClick());
        btnPublishParking.setOnClickListener(new CommonClick());
    }

    private void publishParking() {
        HashMap<String, String> httpParams = new HashMap<>();
        if ( mParkingSpaceBean != null)
            httpParams.put(ApiParamsKey.CAR_PORT_ID, mParkingSpaceBean.getCarportId() + "");
        else {
            if (mCommunityListBean != null && mCommunityListBean.size() > 0) {
                for (int i = 0; i < mCommunityListBean.size(); i++) {
                    if (mCommunityListBean.get(i).getCarportList() != null && mCommunityListBean.get(i).getCarportList().size() > 0) {
                        for (int j = 0; j < mCommunityListBean.get(i).getCarportList().size(); j++) {
                            if (mCommunityListBean.get(i).getCarportList().get(j).isBind()) {
                                httpParams.put(ApiParamsKey.CAR_PORT_ID, mCommunityListBean.get(i).getCarportList().get(j).getId() + "");
                                return;
                            }
                        }
                    }
                }
            }
        }
        httpParams.put(ApiParamsKey.START_TIME, tvSelectStartTime.getText() + ":00");
        httpParams.put(ApiParamsKey.STOP_TIME, tvSelectStopTime.getText() + ":00");
        httpParams.put(ApiParamsKey.SHARE_PRICE, mInputRentPrice);
        if (!TextUtils.isEmpty(mInputRentRemark))
            httpParams.put(ApiParamsKey.SHARE_PRICE, mInputRentPrice);
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
                            finish();
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

    private class CommonClick extends ForbidQuickClickListener {
        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.llSkip2ChoseCarport:
                    startActivityForResult(new Intent(PublishShareParkingActivity.this, ParkingSpaceMineActivity.class).putExtra("is_enable_select", true), 99);
                    break;
                case R.id.btnPublishParking:
                    if (TextUtils.isEmpty(tvParkingAddress.getText().toString())) {
                        ToastUtils.show("请先添加车位锁");
                        return;
                    }
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
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            if (data != null) {
                mParkingSpaceBean = (ParkingSpaceMineBean)data.getSerializableExtra("parking_mine_bean");
                initView();
                initClick();
            }
        }
        if (requestCode == 199 && resultCode == RESULT_OK) {
            if (data != null) {

            }
        }
    }

    private void initCustomTimePicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();

        int mYear = startDate.get(Calendar.YEAR); // 获取当前年份
        int mMonth = startDate.get(Calendar.MONTH);// 获取当前月份
        int mDay = startDate.get(Calendar.DAY_OF_MONTH);// 获取当日期
        int mWeek = startDate.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
        int mHour = startDate.get(Calendar.HOUR_OF_DAY);//时
        int mMinute = startDate.get(Calendar.MINUTE);//分
//        int mSecond = startDate.get(Calendar.SECOND);//秒

        startDate.set(mYear, mMonth, mDay, mHour, mMinute);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2999, 12, mDay, mHour, mMinute);
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
                                try {
                                    if (TimeUtils.compareEndAndStart(getTime(pvCustomTimeStart.getSelectedDate()) + ":00", getTime(new Date()) + ":00") > 0) {
                                        ToastUtils.show("选择时间请大于当前时间");
                                    } else if (!TextUtils.isEmpty(tvSelectStopTime.getText().toString()) && TimeUtils.compareEndAndStart(getTime(pvCustomTimeStart.getSelectedDate()) + ":00", getTime(pvCustomTimeStop.getSelectedDate()) + ":00") < 0) {
                                        ToastUtils.show("起始时间请小于结束时间");
                                    } else {
                                        pvCustomTimeStart.returnData();
                                        pvCustomTimeStart.dismiss();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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
                .setLineSpacingMultiplier(1.8f)
                .setTextXOffset(0, 0, 0, 0, 0, 0)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFFF4F4F4)
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
                                try {
                                    if (TimeUtils.compareEndAndStart(getTime(pvCustomTimeStop.getSelectedDate()) + ":00", getTime(new Date()) + ":00") > 0) {
                                        ToastUtils.show("选择时间请大于当前时间");
                                    } else if (!TextUtils.isEmpty(tvSelectStartTime.getText().toString()) && TimeUtils.compareEndAndStart(getTime(pvCustomTimeStop.getSelectedDate()) + ":00", getTime(pvCustomTimeStart.getSelectedDate()) + ":00") >= 0) {
                                        ToastUtils.show("结束时间请大于起始时间");
                                    } else {
                                        pvCustomTimeStop.returnData();
                                        pvCustomTimeStop.dismiss();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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
                .setLineSpacingMultiplier(1.8f)
                .setTextXOffset(0, 0, 0, 0, 0, 0)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFFF4F4F4)
                .build();
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

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
