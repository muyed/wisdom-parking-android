package com.cn.climax.wisdomparking.ui.main.carport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.climax.wisdomparking.R;

import butterknife.ButterKnife;

/**
 * author：leo on 2018/4/28 0028 18:29
 * email： leocheung4ever@gmail.com
 * description: 车位匹配适配器
 * what & why is modified:
 */
public class ParkingSpaceMatchAdapter extends RecyclerView.Adapter<ParkingSpaceMatchAdapter.ViewHolder> {

    private Context mContext;

    public ParkingSpaceMatchAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ParkingSpaceMatchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_match_parking_space_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParkingSpaceMatchAdapter.ViewHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
