package com.cn.climax.wisdomparking.ui.main.licenseplate.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.util.Number2ChineseUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/3/5 0005 00:40
 * email： leocheung4ever@gmail.com
 * description: 添加设备 适配器
 * what & why is modified:
 */
public class RVDeviceAddAdapter extends RecyclerView.Adapter<RVDeviceAddAdapter.ParkSpaceViewHolder> {

    private Context mContext;
    private int mInsertPos = 1;

    public RVDeviceAddAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RVDeviceAddAdapter.ParkSpaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_device_park_layout, parent, false);
        return new ParkSpaceViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RVDeviceAddAdapter.ParkSpaceViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tvParkNumber.setText("车位" + Number2ChineseUtils.toChinese((position + 1) + ""));
        holder.ivAddParkSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    if (position == 0)
                        mOnClickListener.addItem();
                    else
                        mOnClickListener.deleteItem();
                }
            }
        });
        if (position == 0) {
            holder.ivAddParkSpace.setImageResource(R.drawable.icon_add_park);
        } else {
            holder.ivAddParkSpace.setImageResource(R.drawable.icon_delete_park);
        }
    }

    @Override
    public int getItemCount() {
        return mInsertPos;
    }

    public void setNotifyData(int insertPos) {
        this.mInsertPos = insertPos;
        notifyDataSetChanged();
    }

    public class ParkSpaceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvParkNumber)
        TextView tvParkNumber;
        @BindView(R.id.ivAddParkSpace)
        ImageView ivAddParkSpace;

        public ParkSpaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickAddParkListener {
        void addItem();

        void deleteItem();
    }

    private OnClickAddParkListener mOnClickListener;

    public void setOnClickListener(OnClickAddParkListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}
