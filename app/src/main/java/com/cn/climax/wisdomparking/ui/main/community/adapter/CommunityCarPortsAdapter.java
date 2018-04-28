package com.cn.climax.wisdomparking.ui.main.community.adapter;

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
import com.cn.climax.wisdomparking.data.response.CommunityAuthListResponse;
import com.cn.climax.wisdomparking.ui.main.device.AddDeviceActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/4/18 0018 03:12
 * email： leocheung4ever@gmail.com
 * description: 我的认证小区详情 车位适配器
 * what & why is modified:
 */
public class CommunityCarPortsAdapter extends RecyclerView.Adapter<CommunityCarPortsAdapter.CarViewHolder> {

    private CommunityAuthListResponse mCommunityDetail;
    private Context mContext;
    private List<CommunityAuthListResponse.CarportListBean> mCarportList;

    public CommunityCarPortsAdapter(Context context, CommunityAuthListResponse communityDetail, List<CommunityAuthListResponse.CarportListBean> carportList) {
        this.mContext = context;
        this.mCommunityDetail = communityDetail;
        this.mCarportList = carportList;
    }

    @Override
    public CommunityCarPortsAdapter.CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_community_car_ports_layout, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommunityCarPortsAdapter.CarViewHolder holder, final int position) {
        holder.tvGoToBind.setOnClickListener(new ForbidQuickClickListener() {
            @Override
            protected void forbidClick(View view) {
                mContext.startActivity(new Intent(mContext, AddDeviceActivity.class).putExtra("carports_address", mCommunityDetail.getAddr()).putExtra("carports_info", mCarportList.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCarportList != null && mCarportList.size() > 0 ? mCarportList.size() : 0;
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rlSkip2Detail)
        RelativeLayout rlSkip2Detail;
        @BindView(R.id.tvParkingCode)
        TextView tvParkingCode;
        @BindView(R.id.tvParkingStatus)
        TextView tvParkingStatus;
        @BindView(R.id.tvParkingValidity)
        TextView tvParkingValidity;
        @BindView(R.id.tvGoToBind)
        TextView tvGoToBind;

        public CarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
