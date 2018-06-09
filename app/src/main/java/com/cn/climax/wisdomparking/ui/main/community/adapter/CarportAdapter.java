package com.cn.climax.wisdomparking.ui.main.community.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.local.CarLockParkingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author：leo on 2018/6/9 0009 09:17
 * email： leocheung4ever@gmail.com
 * description: 我的车位锁适配器
 * what & why is modified:
 */
public class CarportAdapter extends PagerAdapter {

    private List<View> viewList;
    private ArrayList<CarLockParkingBean> mLockList;

    public CarportAdapter() {
        this.viewList = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = viewList.get(position);

        TextView tvCarSpaceLockNo = (TextView) view.findViewById(R.id.tvCarSpaceLockNo);
        TextView tvCarSpaceLockRemark = (TextView) view.findViewById(R.id.tvCarSpaceLockRemark);
        tvCarSpaceLockNo.setText(mLockList.get(position).getLockName());
        tvCarSpaceLockRemark.setText(mLockList.get(position).getLockRemark());

        collection.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setData(@Nullable List<View> list, ArrayList<CarLockParkingBean> lockList) {
        this.viewList.clear();
        this.mLockList = lockList;
        if (list != null && !list.isEmpty()) {
            this.viewList.addAll(list);
        }

        notifyDataSetChanged();
    }

    @NonNull
    public List<View> getData() {
        if (viewList == null) {
            viewList = new ArrayList<>();
        }
        return viewList;
    }
}
