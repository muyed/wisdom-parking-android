package com.cn.climax.wisdomparking.ui.main.order;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.response.MyOrderResponse;
import com.cn.climax.wisdomparking.ui.main.device.ParkingSpacePayActivity;
import com.cn.climax.wisdomparking.widget.TimerDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderMineDetailActivity extends BaseSwipeBackActivity {

    @BindView(R.id.tvReleaseLockCode)
    TextView tvReleaseLockCode;
    @BindView(R.id.tvOrderStatus)
    TextView tvOrderStatus;
    @BindView(R.id.tvParkingLocation)
    TextView tvParkingLocation;
    @BindView(R.id.tvCardLicenseNo)
    TextView tvCardLicenseNo;

    @BindView(R.id.tvOrderNo)
    TextView tvOrderNo;
    @BindView(R.id.tvOrderPhone)
    TextView tvOrderPhone;
    @BindView(R.id.tvOrderStartTime)
    TextView tvOrderStartTime;
    @BindView(R.id.tvStatusStartTime)
    TextView tvStatusStartTime;
    @BindView(R.id.tvStatusEndTime)
    TextView tvStatusEndTime;
    @BindView(R.id.tvOrderCancleTime)
    TextView tvOrderCancleTime;
    @BindView(R.id.tvPayAmount)
    TextView tvPayAmount;

    @BindView(R.id.btnPayMoney)
    Button btnPayMoney;

    private MyOrderResponse mOrderDetail;

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "订单详情");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_order_mine_detail;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        mOrderDetail = (MyOrderResponse) getIntent().getSerializableExtra("order_detail_bean");
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        tvReleaseLockCode.setText("解锁码：" + mOrderDetail.getOpenCode());
        dispatchOrderStatus(mOrderDetail.getStatus(), tvOrderStatus);
        tvCardLicenseNo.setText(mOrderDetail.getCarLicense());

        tvOrderNo.setText(mOrderDetail.getTicketNum());
        tvOrderPhone.setText(mOrderDetail.getPhone());
        tvOrderStartTime.setText(mOrderDetail.getStartTime());
        tvOrderCancleTime.setText(mOrderDetail.getPayDeadlineTime());

        tvPayAmount.setText("￥" + mOrderDetail.getParkingFee());
    }

    private void dispatchOrderStatus(int status, TextView tvOrderStatus) {
        switch (status) {
            case 0:
                tvOrderStatus.setText("待支付");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                tvStatusStartTime.setText("预约开始时间");
                tvStatusEndTime.setText("预约结束时间");
                break;
            case 1:
                tvOrderStatus.setText("已支付");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_lighter_color));
                tvStatusStartTime.setText("下单时间");
                tvStatusEndTime.setText("结束时间");
                break;
            case 2:
                tvOrderStatus.setText("停车中");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_43d28d));
                tvStatusStartTime.setText("下单时间");
                tvStatusEndTime.setText("结束时间");
                break;
            case 3:
                tvOrderStatus.setText("已逾期");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_f5222d));
                tvStatusStartTime.setText("下单时间");
                tvStatusEndTime.setText("结束时间");
                tvOrderCancleTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_f5222d));
                break;
            case 4:
                tvOrderStatus.setText("已结束");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_lighter_color));
                break;
            case 5:
                tvOrderStatus.setText("逾期待支付");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_f5222d));
                break;
            case 6:
                tvOrderStatus.setText("逾期已支付");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                tvStatusStartTime.setText("下单时间");
                tvStatusEndTime.setText("结束时间");
                break;
            case 7:
                tvOrderStatus.setText("已取消");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_lighter_color));
                break;
        }
    }

    @OnClick({R.id.btnPayMoney})
    void click(View view) {
        switch (view.getId()) {
            case R.id.btnPayMoney:
                payMoney();
                break;
        }
    }

    private void payMoney() {
        startActivity(new Intent(OrderMineDetailActivity.this, ParkingSpacePayActivity.class)
                .putExtra("pay_amount", String.valueOf(mOrderDetail.getParkingFee()))
                .putExtra("order_no", mOrderDetail.getTicketNum())
                .putExtra("order_id", mOrderDetail.getId()));
//        showTimerDialog();
    }

    //显示倒计时对话框
    private void showTimerDialog() {
        TimerDialog dialog = new TimerDialog(OrderMineDetailActivity.this);
        dialog.setTitle("提示");
        dialog.setPositiveButton("即将跳转支付界面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(OrderMineDetailActivity.this, ParkingSpacePayActivity.class));
            }
        }, 3);
        dialog.show();
        dialog.setButtonType(Dialog.BUTTON_POSITIVE, 3, true);
    }

}
