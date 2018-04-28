package com.cn.climax.wisdomparking.ui.main.order.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.ui.main.order.OrderMineActivity;
import com.cn.climax.wisdomparking.ui.main.order.OrderMineDetailActivity;

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

    public OrderMineAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public OrderMineAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_order_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderMineAdapter.OrderViewHolder holder, int position) {
        holder.rlSkip2Detail.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                mContext.startActivity(new Intent(mContext, OrderMineDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
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
