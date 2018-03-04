package com.cn.climax.wisdomparking.ui.main.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.local.AddressBean;
import com.cn.climax.wisdomparking.ui.main.home.city.AddressSelectedActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2017/3/21 14:59
 * email： leocheung4ever@gmail.com
 * description: 设置地址列表适配器
 * what & why is modified:
 */

public class SettingsAddressAdapter extends RecyclerView.Adapter<SettingsAddressAdapter.AddressHistoryViewHolder> {

    private WeakReference<AddressSelectedActivity> ctx;
    private List<AddressBean> mData = new ArrayList<>();

    public SettingsAddressAdapter(WeakReference<AddressSelectedActivity> contextWeakReference, List<AddressBean> datas) {
        this.ctx = contextWeakReference;
        this.mData = datas;
    }

    @Override
    public AddressHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx.get()).inflate(R.layout.view_item_history_address, parent, false);
        return new AddressHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressHistoryViewHolder holder, int position) {
        bindViewData(holder, position);
        addOnItemClickEvent(holder.allAddressItem, mData, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class AddressHistoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.allAddressItem)
        LinearLayout allAddressItem;
        @BindView(R.id.tv_address_history_name)
        TextView tv_address_history_name;
        @BindView(R.id.tv_address_history_road)
        TextView tv_address_history_road;

        public AddressHistoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void bindViewData(AddressHistoryViewHolder holder, int position) {
        holder.tv_address_history_name.setText(mData.get(position).getAddressName());
        holder.tv_address_history_road.setText(mData.get(position).getAddressLocation());
    }

    private void addOnItemClickEvent(LinearLayout allAddressItem, List<AddressBean> mData, int position) {
        allAddressItem.setOnClickListener(new SkipToSetClickListener(mData.get(position)));
    }

    private class SkipToSetClickListener implements View.OnClickListener {

        private AddressBean dataBean;

        SkipToSetClickListener(AddressBean addressBean) {
            this.dataBean = addressBean;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.allClickToAddressSetting:
                    Toast.makeText(ctx.get(), dataBean.getAddressName() + " " + dataBean.getAddressLocation(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
