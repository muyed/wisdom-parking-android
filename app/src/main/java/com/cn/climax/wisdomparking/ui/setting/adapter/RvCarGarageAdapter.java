package com.cn.climax.wisdomparking.ui.setting.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;

import java.util.List;

/**
 * author：leo on 2017/8/2 10:08
 * email： leocheung4ever@gmail.com
 * description: 我的车库列表适配器
 * what & why is modified:
 */

public class RvCarGarageAdapter extends RecyclerView.Adapter<RvCarGarageAdapter.MyViewHolder> {

    private Context context;
    private List<Integer> datas;

    /**
     * item的点击事件的长按事件接口
     */
    private OnItemClickListener onItemClickListener;

    public RvCarGarageAdapter(Context context) {
        this.context = context;
    }

    /**
     * 设置点击事件
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.list_item_car_garage, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(contentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        RecyclerView.LayoutParams layoutParams;
        layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.itemView.setLayoutParams(layoutParams);
//        holder.imageView.setImageResource(datas.get(position));
//        holder.tv.setText("分类" + position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    /**
     * 用于缓存的ViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        public TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
//            imageView = (ImageView) itemView.findViewById(R.id.iv);
//            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    /**
     * 设置item监听的接口
     */
    public interface OnItemClickListener {
        void onItemClickListener(int position, Integer data);
    }
}
