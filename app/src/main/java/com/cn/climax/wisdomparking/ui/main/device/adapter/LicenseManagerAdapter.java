package com.cn.climax.wisdomparking.ui.main.device.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.response.CarLicenseMineBean;
import com.cn.climax.wisdomparking.data.response.LoginResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/4/28 0028 18:29
 * email： leocheung4ever@gmail.com
 * description: 车牌管理适配器
 * what & why is modified:
 */
public class LicenseManagerAdapter extends RecyclerView.Adapter<LicenseManagerAdapter.ViewHolder> {

    private Context mContext;
    private List<CarLicenseMineBean> mCarLicenseList = new ArrayList<>();

    public LicenseManagerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public LicenseManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_manager_license_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LicenseManagerAdapter.ViewHolder holder, int position) {
        holder.tvCarLicenseNo.setText(mCarLicenseList.get(position).getLicense());
    }

    @Override
    public int getItemCount() {
        return mCarLicenseList != null && mCarLicenseList.size() > 0 ? mCarLicenseList.size() : 0;
    }

    public void setDatas(List<CarLicenseMineBean> licenseMineBeen) {
        this.mCarLicenseList = licenseMineBeen;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCarLicenseNo)
        TextView tvCarLicenseNo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
