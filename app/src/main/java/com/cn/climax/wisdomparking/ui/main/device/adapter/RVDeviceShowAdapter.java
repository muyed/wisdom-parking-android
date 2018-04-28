package com.cn.climax.wisdomparking.ui.main.device.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.util.Number2ChineseUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/3/5 0005 00:40
 * email： leocheung4ever@gmail.com
 * description: 添加设备 展示 适配器
 * what & why is modified:
 */
public class RVDeviceShowAdapter extends RecyclerView.Adapter<RVDeviceShowAdapter.ParkSpaceViewHolder> {

    private Context mContext;

    public RVDeviceShowAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RVDeviceShowAdapter.ParkSpaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_device_park_layout, parent, false);
        return new ParkSpaceViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RVDeviceShowAdapter.ParkSpaceViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tvParkNumber.setText("车位" + Number2ChineseUtils.toChinese((position + 1) + ""));
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class ParkSpaceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvParkNumber)
        TextView tvParkNumber;

        public ParkSpaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
