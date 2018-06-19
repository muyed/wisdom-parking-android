package com.cn.climax.wisdomparking.ui.main.community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.response.CommunityAuthListResponse;
import com.cn.climax.wisdomparking.ui.main.community.CommunityAuthedActivity;
import com.cn.climax.wisdomparking.ui.main.community.CommunityDeniedActivity;
import com.cn.climax.wisdomparking.ui.main.carport.AddDeviceActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/4/7 0007 16:16
 * email： leocheung4ever@gmail.com
 * description: 我的小区适配器
 * what & why is modified:
 */
public class CommunityAuthDetailAdapter extends RecyclerView.Adapter<CommunityAuthDetailAdapter.OrderViewHolder> {

    private Context mContext;
    private List<CommunityAuthListResponse> mCommunityListBean = new ArrayList<>();
    private CommunityAuthListResponse mCommunityDetailBean;

    public CommunityAuthDetailAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public CommunityAuthDetailAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_community_auth_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CommunityAuthDetailAdapter.OrderViewHolder holder, final int position) {
        holder.llSkip2Detail.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                dispatchStatus2Detail(mCommunityListBean.get(position).getType());
            }
        });
        dispatchStatus2List(holder, mCommunityListBean.get(position));
        holder.tvParkingCommunityName.setText(mCommunityListBean.get(position).getCommunityName());
        holder.tvParkingCommunityAddress.setText(mCommunityListBean.get(position).getAddr()); //mCommunityListBean.get(position).getProvince()  + mCommunityListBean.get(position).getCity()  + mCommunityListBean.get(position).getArea() +

        if (mCommunityListBean.get(position).getCarportList() != null && mCommunityListBean.get(position).getCarportList().size() > 0) {
            holder.tvParkingInfo.setVisibility(View.VISIBLE);
            StringBuilder parkingInfoList = new StringBuilder();
            if (mCommunityListBean.get(position).getCarportList().size() > 1) {
                for (int i = 0; i < mCommunityListBean.get(position).getCarportList().size(); i++) {
                    parkingInfoList.append(mCommunityListBean.get(position).getCarportList().get(i).getCarportNum()).append("、");
                }
                holder.tvParkingInfo.setText("车位：" + parkingInfoList.substring(0, parkingInfoList.length() - 1));
            } else {
                holder.tvParkingInfo.setText("车位：" + mCommunityListBean.get(position));
            }
        } else {
            holder.tvParkingInfo.setVisibility(View.VISIBLE);
            holder.tvParkingInfo.setText("暂无车位信息");
        }
    }

    private void dispatchStatus2List(OrderViewHolder holder, CommunityAuthListResponse authListResponse) {
        if (authListResponse.getCarportList() != null && authListResponse.getCarportList().size() > 0) {
            holder.tvParkingStatus_authed.setVisibility(View.VISIBLE);
            holder.tvParkingStatus_denied.setVisibility(View.GONE);
            holder.tvParkingStatus_binded.setVisibility(View.VISIBLE);
            holder.tvParkingStatus_authing.setVisibility(View.GONE);
        } else
            switch (authListResponse.getType()) {
                case 1: //审核中
                    holder.tvParkingStatus_authed.setVisibility(View.GONE);
                    holder.tvParkingStatus_denied.setVisibility(View.GONE);
                    holder.tvParkingStatus_binded.setVisibility(View.GONE);
                    holder.tvParkingStatus_authing.setVisibility(View.VISIBLE);
                    break;
                case 2: //小区审核通过
                    holder.tvParkingStatus_authed.setVisibility(View.VISIBLE);
                    holder.tvParkingStatus_denied.setVisibility(View.GONE);
                    holder.tvParkingStatus_binded.setVisibility(View.GONE);
                    holder.tvParkingStatus_authing.setVisibility(View.GONE);
                    holder.llSkip2BindParkingSpace.setVisibility(View.VISIBLE);
                    holder.llSkip2BindParkingSpace.setOnClickListener(new ForbidQuickClickListener() {
                        @Override
                        protected void forbidClick(View view) {
                            mContext.startActivity(new Intent(mContext, AddDeviceActivity.class));
                        }
                    });
                    break;
                case 3: //已拒绝
                    holder.tvParkingStatus_authed.setVisibility(View.GONE);
                    holder.tvParkingStatus_denied.setVisibility(View.VISIBLE);
                    holder.tvParkingStatus_binded.setVisibility(View.GONE);
                    holder.tvParkingStatus_authing.setVisibility(View.GONE);
                    break;
            }
    }

    private void dispatchStatus2Detail(int type) {
        switch (type) {
            case 1:
                break;
            case 2: //小区审核通过
                mContext.startActivity(new Intent(mContext, CommunityAuthedActivity.class));
                break;
            case 3: //已拒绝
                mContext.startActivity(new Intent(mContext, CommunityDeniedActivity.class));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mCommunityListBean != null && mCommunityListBean.size() > 0 ? mCommunityListBean.size() : 0;
    }

    public void setDatas(List<CommunityAuthListResponse> communityListBean) {
        this.mCommunityListBean = communityListBean;
        notifyDataSetChanged();
    }

    public void addDatas(List<CommunityAuthListResponse> communityListBean) {
        this.mCommunityListBean.addAll(communityListBean);
        notifyDataSetChanged();
    }

    public void setDetailData(CommunityAuthListResponse communityDetail) {
        this.mCommunityDetailBean = communityDetail;
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llSkip2Detail)
        LinearLayout llSkip2Detail;
        @BindView(R.id.llSkip2BindParkingSpace)
        LinearLayout llSkip2BindParkingSpace;
        @BindView(R.id.tvParkingCommunityName)
        TextView tvParkingCommunityName;
        @BindView(R.id.tvParkingStatus_authed)
        TextView tvParkingStatus_authed;
        @BindView(R.id.tvParkingStatus_authing)
        TextView tvParkingStatus_authing;
        @BindView(R.id.tvParkingStatus_denied)
        TextView tvParkingStatus_denied;
        @BindView(R.id.tvParkingStatus_binded)
        TextView tvParkingStatus_binded;
        @BindView(R.id.tvParkingCommunityAddress)
        TextView tvParkingCommunityAddress;
        @BindView(R.id.tvParkingInfo)
        TextView tvParkingInfo;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
