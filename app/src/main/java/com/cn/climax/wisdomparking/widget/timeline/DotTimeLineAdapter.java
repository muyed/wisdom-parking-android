package com.cn.climax.wisdomparking.widget.timeline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.widget.timeline.util.Util;

import java.util.List;


public class DotTimeLineAdapter extends RecyclerView.Adapter<DotTimeLineAdapter.ViewHolder> {
    Context mContext;
    List<Event> mList;
    int[] colors = {0xffFFAD6C, 0xff62f434, 0xffdeda78, 0xff7EDCFF, 0xff58fdea, 0xfffdc75f};//颜色组

    public void setList(List<Event> list) {
        mList = list;
    }

    public DotTimeLineAdapter(Context context) {
        mContext = context;
    }

    public DotTimeLineAdapter(Context context, List<Event> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.time.setText(Util.LongtoStringFormat(1000 * mList.get(position).getTime()));
        holder.textView.setText(mList.get(position).getEvent());
        holder.time.setTextColor(colors[position % colors.length]);
        addItemClickListener(holder.textView, position);
    }

    private void addItemClickListener(TextView textView, int position) {
        textView.setOnClickListener(new MyClickListener(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.time);
            textView = (TextView) view.findViewById(R.id.text);
        }
    }

    private class MyClickListener implements View.OnClickListener {

        private int mPos;

        public MyClickListener(int position) {
            this.mPos = position;
        }

        @Override
        public void onClick(View view) {
//            Intent travelIntent = new Intent(mContext, TravelDetailActivity.class);
//            mContext.startActivity(travelIntent);
//            ((Activity) mContext).overridePendingTransition(R.anim.pop_up_anim, R.anim.pop_no_anim);
        }
    }
}
