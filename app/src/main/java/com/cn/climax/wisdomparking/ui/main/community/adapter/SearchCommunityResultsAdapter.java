package com.cn.climax.wisdomparking.ui.main.community.adapter;

import android.content.Context;
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

import com.amap.api.services.core.PoiItem;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.local.PoiAddressBean;
import com.cn.climax.wisdomparking.ui.main.community.NearbySearchActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/4/13 0013 09:22
 * email： leocheung4ever@gmail.com
 * description: 搜索结果适配器
 * what & why is modified:
 */
public class SearchAddressResultsAdapter extends RecyclerView.Adapter<SearchAddressResultsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<PoiAddressBean> mPoiList = new ArrayList<>();
    private String mKeyword;

    public SearchAddressResultsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public SearchAddressResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_poi_keyword_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAddressResultsAdapter.ViewHolder holder, int position) {
        final PoiAddressBean poiAddressBean = mPoiList.get(position);

        SpannableStringBuilder spannable = new SpannableStringBuilder(poiAddressBean.getDetailAddress());
        Pattern p = Pattern.compile(mKeyword);
        Matcher m = p.matcher(spannable);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#38AD83")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.tv_detailAddress.setText(spannable);

        holder.tv_content.setText(poiAddressBean.getText());
        holder.ll_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NearbySearchActivity) mContext).setDetailAddress(poiAddressBean.getDetailAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPoiList != null && mPoiList.size() > 0 ? mPoiList.size() : 0;
    }

    public void setDatas(String keyword, ArrayList<PoiAddressBean> poiItems) {
        this.mKeyword = keyword;
        this.mPoiList = poiItems;
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
