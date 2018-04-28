package com.cn.climax.wisdomparking.ui.main.device.adapter;

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
import com.cn.climax.wisdomparking.data.response.ParkingSpaceMineBean;
import com.cn.climax.wisdomparking.data.response.ParkingSpaceMineListBean;
import com.cn.climax.wisdomparking.ui.main.order.OrderMineDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/4/7 0007 16:16
 * email： leocheung4ever@gmail.com
 * description: 我的车位适配器
 * what & why is modified:
 */
public class ParkingSpaceMineAdapter extends RecyclerView.Adapter<ParkingSpaceMineAdapter.OrderViewHolder> {

    private Context mContext;
    private List<ParkingSpaceMineBean> mSpaceMineBeanList = new ArrayList<>();

    public ParkingSpaceMineAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ParkingSpaceMineAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_parking_space_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParkingSpaceMineAdapter.OrderViewHolder holder, int position) {
        holder.rlSkip2Detail.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                mContext.startActivity(new Intent(mContext, OrderMineDetailActivity.class));
            }
        });
        holder.tvParkingCode.setText(mSpaceMineBeanList.get(position).getCarportNum());
        holder.tvParkingValidity.setText(mSpaceMineBeanList.get(position).getParent());
        holder.tvParkingAddr.setText(mSpaceMineBeanList.get(position).getAddr());
        dispatchStatus(holder.tvParkingStatus, mSpaceMineBeanList.get(position).getStatus());
    }

    private void dispatchStatus(TextView tvParkingStatus, int status) {
        switch (status){
            case 0:
                
                break;
            case 1:
                
                break;
            case 2:
                
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mSpaceMineBeanList != null && mSpaceMineBeanList.size() > 0 ? mSpaceMineBeanList.size() : 0;
    }

    public void setDatas(List<ParkingSpaceMineBean> spaceMineBeanList) {
        this.mSpaceMineBeanList = spaceMineBeanList;
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rlSkip2Detail)
        RelativeLayout rlSkip2Detail;
        @BindView(R.id.tvParkingCode)
        TextView tvParkingCode;
        @BindView(R.id.tvParkingStatus)
        TextView tvParkingStatus;
        @BindView(R.id.tvParkingValidity)
        TextView tvParkingValidity;
        @BindView(R.id.tvParkingAddr)
        TextView tvParkingAddr;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
