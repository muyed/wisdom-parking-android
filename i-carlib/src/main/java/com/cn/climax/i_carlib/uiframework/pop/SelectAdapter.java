package com.cn.climax.i_carlib.uiframework.pop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.climax.i_carlib.R;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {
    private String[] data;
    private OnClick clickListen;
    private SelectPopwindow popwindow;
    public  interface  OnClick{
        void click(int position, String txt);
    }

    public SelectAdapter(String[] strings, OnClick clickListen, SelectPopwindow selectPopwindow) {
        this.data=strings;
        this.clickListen=clickListen;
        this.popwindow=selectPopwindow;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popwindow_select_item, parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.selectTV.setText(data[position]+"");
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListen!=null) {
                    clickListen.click(position ,data[position]);
                    popwindow.dismiss();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView selectTV;
        private  View v;
        public ViewHolder(View itemView) {
            super(itemView);
            this.v=itemView;
            this.selectTV=itemView.findViewById(R.id.selectTV);
        }
    }
}
