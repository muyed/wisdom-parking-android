package com.cn.climax.wisdomparking.ui.main.community.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.local.PoiAddressBean;
import com.cn.climax.wisdomparking.data.response.CommunityListResponse;
import com.cn.climax.wisdomparking.ui.main.community.NearbySearchActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/4/13 0013 09:22
 * email： leocheung4ever@gmail.com
 * description: 搜索小区结果适配器
 * what & why is modified:
 */
public class SearchCommunityResultsAdapter extends RecyclerView.Adapter<SearchCommunityResultsAdapter.ViewHolder> {

    private Context mContext;
    private List<CommunityListResponse> mCommunityList = new ArrayList<>();

    public SearchCommunityResultsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public SearchCommunityResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_poi_keyword_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchCommunityResultsAdapter.ViewHolder holder, int position) {
        final CommunityListResponse commAddressBean = mCommunityList.get(position);

        holder.tv_detailAddress.setText(commAddressBean.getCommunityName());
        holder.tv_content.setText(commAddressBean.getAddr());
        holder.ll_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(ApiParamsKey.COMMUNITY_BEAN, commAddressBean);
                ((Activity) mContext).setResult(Activity.RESULT_OK, intent);
                ((Activity) mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCommunityList != null && mCommunityList.size() > 0 ? mCommunityList.size() : 0;
    }

    public void setDatas(List<CommunityListResponse> communityList) {
        mCommunityList = new ArrayList<>();
        this.mCommunityList = communityList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_detailAddress)
        TextView tv_detailAddress;
        @BindView(R.id.ll_item_layout)
        LinearLayout ll_item_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
