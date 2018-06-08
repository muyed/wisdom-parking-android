package com.cn.climax.wisdomparking.ui.main.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.response.MyOrderResponse;
import com.cn.climax.wisdomparking.ui.main.order.OrderMineActivity;
import com.cn.climax.wisdomparking.ui.main.order.OrderMineDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/4/7 0007 16:16
 * email： leocheung4ever@gmail.com
 * description: 我的订单适配器
 * what & why is modified:
 */
public class OrderMineAdapter extends RecyclerView.Adapter<OrderMineAdapter.OrderViewHolder> {

    private Context mContext;
    private List<MyOrderResponse> mDataBean;

    public OrderMineAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public OrderMineAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_order_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(OrderMineAdapter.OrderViewHolder holder, final int position) {
        holder.tvOrderCode.setText("解锁码：" + mDataBean.get(position).getOpenCode());
        holder.tvParkingAddr.setText("解锁码：" + mDataBean.get(position).getOpenCode());
        holder.tvOrderTime.setText("预约时间：" + mDataBean.get(position).getAppointmentStartTime());
        dispatchOrderStatus(holder.tvOrderStatus, mDataBean.get(position).getStatus());
        holder.rlSkip2Detail.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                mContext.startActivity(new Intent(mContext, OrderMineDetailActivity.class).putExtra("order_detail_bean", mDataBean.get(position)));
            }
        });
    }

    private void dispatchOrderStatus(TextView tvOrderStatus, int status) {
        switch (status) {
            case 0:
                tvOrderStatus.setText("待支付");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                break;
            case 1:
                tvOrderStatus.setText("已支付");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_lighter_color));
                break;
            case 2:
                tvOrderStatus.setText("停车中");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_43d28d));
                break;
            case 3:
                tvOrderStatus.setText("已逾期");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_f5222d));
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
                break;
            case 7:
                tvOrderStatus.setText("已取消");
                tvOrderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_lighter_color));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataBean != null && mDataBean.size() > 0 ? mDataBean.size() : 0;
    }

    public void setDatas(List<MyOrderResponse> myOrderResponses) {
        this.mDataBean = myOrderResponses;
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rlSkip2Detail)
        RelativeLayout rlSkip2Detail;
        @BindView(R.id.tvOrderCode)
        TextView tvOrderCode;
        @BindView(R.id.tvOrderStatus)
        TextView tvOrderStatus;
        @BindView(R.id.tvParkingAddr)
        TextView tvParkingAddr;
        @BindView(R.id.tvOrderTime)
        TextView tvOrderTime;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
