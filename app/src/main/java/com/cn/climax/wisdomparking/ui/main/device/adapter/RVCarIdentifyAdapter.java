package com.cn.climax.wisdomparking.ui.main.device.adapter;

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
 * author：leo on 2018/3/5 0005 20:34
 * email： leocheung4ever@gmail.com
 * description: 车辆识别仪 适配器
 * what & why is modified:
 */
public class RVCarIdentifyAdapter extends RecyclerView.Adapter<RVCarIdentifyAdapter.CarViewHolder> {

    private Context mContext;
    private int mInsertPos = 1;

    public RVCarIdentifyAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RVCarIdentifyAdapter.CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_car_identify_layout, parent, false);
        return new CarViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RVCarIdentifyAdapter.CarViewHolder holder, final int position) {
        holder.tvCarNumber.setText("车位" + Number2ChineseUtils.toChinese((position + 1) + ""));
        holder.ivAddCarSpace.setOnClickListener(new View.OnClickListener() {
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
            holder.ivAddCarSpace.setImageResource(R.drawable.icon_add_park);
        } else {
            holder.ivAddCarSpace.setImageResource(R.drawable.icon_delete_park);
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

    public class CarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCarNumber)
        TextView tvCarNumber;
        @BindView(R.id.ivAddCarSpace)
        ImageView ivAddCarSpace;

        public CarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickAddCarListener {
        void addItem();

        void deleteItem();
    }

    private OnClickAddCarListener mOnClickListener;

    public void setOnClickListener(OnClickAddCarListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}
