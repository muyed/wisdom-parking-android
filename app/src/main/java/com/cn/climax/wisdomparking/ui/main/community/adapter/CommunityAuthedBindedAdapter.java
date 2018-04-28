package com.cn.climax.wisdomparking.ui.main.community.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.help.FullyLinearLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/4/18 0018 13:32
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */
public class CommunityAuthedBindedAdapter extends RecyclerView.Adapter<CommunityAuthedBindedAdapter.ViewHolder> {

    @Override
    public CommunityAuthedBindedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CommunityAuthedBindedAdapter.ViewHolder holder, int position) {
//        holder.tvCommunityName.setText(mCommunityDetail.getCommunityName());
//        holder.tvCommunityProvinceCity.setText(mCommunityDetail.getProvince() + "　" + mCommunityDetail.getCity());
//        holder.tvCommunityAddr.setText(mCommunityDetail.getAddr());
//
//
//        holder.tvBindTotal.setText("该小区共绑定" + mCommunityDetail.getCarportList().size() + "个车位");
//
//        holder.tvGoToBind.setOnClickListener(new ForbidQuickClickListener() {
//            @Override
//            protected void forbidClick(View view) {
//                startActivity(new Intent(CommunityAuthedBindedActivity.this, AddCommunityActivity.class));
//            }
//        });

        initRecyclerView();
    }

    private void initRecyclerView() {
//        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
//        rvCarPortsListView.setLayoutManager(linearLayoutManager);
//        CommunityCarPortsAdapter adapter = new CommunityCarPortsAdapter(this, mCommunityDetail.getCarportList());
//        rvCarPortsListView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCommunityName)
        TextView tvCommunityName;
        @BindView(R.id.tvCommunityProvinceCity)
        TextView tvCommunityProvinceCity;
        @BindView(R.id.tvCommunityAddr)
        TextView tvCommunityAddr;
        @BindView(R.id.tvGoToBind)
        TextView tvGoToBind;
        @BindView(R.id.tvBindTotal)
        TextView tvBindTotal;
        @BindView(R.id.rvCarPortsListView)
        RecyclerView rvCarPortsListView;
        
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
