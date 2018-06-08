package com.cn.climax.wisdomparking.ui.main.community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.response.CommunityAuthListResponse;
import com.cn.climax.wisdomparking.ui.main.device.AddDeviceActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CommunityCarPortsAdapter.CarViewHolder holder, final int position) {
        holder.tvParkingCode.setText(mCarportList.get(position).getCarportNum());

        if (TextUtils.isEmpty(mCarportList.get(position).getModifyTime())) {
            holder.tvParkingValidity.setText("有效期：长期有效");
        } else {
            Calendar calendar = new GregorianCalendar();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long time = Long.parseLong(mCarportList.get(position).getModifyTime());
            String d = format.format(time);
            Date date;
            try {
                date = format.parse(d);
                calendar.setTime(date);
                calendar.add(calendar.YEAR, 1);//把日期往后增加一年.整数往后推,负数往前移动
                date = calendar.getTime();   //这个时间就是日期往后推一天的结果 
                holder.tvParkingValidity.setText("有效期：" + format.format(date.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        dispatchCarportStatus(holder.tvParkingStatus, holder.tvGoToBind, mCarportList.get(position));
    }

    private void dispatchCarportStatus(TextView tvParkingStatus, TextView tvGoToBind, final CommunityAuthListResponse.CarportListBean carportListBean) {
        if (carportListBean.isBind()) {
            tvParkingStatus.setText("已绑定");
            tvParkingStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_31C27C));
            tvParkingStatus.setBackgroundResource(R.drawable.common_blue_oval_frame);
            tvGoToBind.setVisibility(View.GONE);
        } else {
            tvParkingStatus.setText("未绑定");
            tvParkingStatus.setBackgroundResource(R.drawable.common_red_oval_frame);
            tvParkingStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_f5222d));
            tvGoToBind.setVisibility(View.VISIBLE);
            tvGoToBind.setOnClickListener(new ForbidQuickClickListener() {
                @Override
                protected void forbidClick(View view) {
                    mContext.startActivity(new Intent(mContext, AddDeviceActivity.class).putExtra("carports_address", mCommunityDetail.getAddr()).putExtra("carports_info", carportListBean));
                }
            });
        }
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
