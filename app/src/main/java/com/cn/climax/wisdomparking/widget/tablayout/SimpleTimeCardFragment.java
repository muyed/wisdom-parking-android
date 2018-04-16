package com.cn.smart.cxzh_android.widget.tablayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.smart.cxzh_android.R;

@SuppressLint("ValidFragment")
public class SimpleTimeCardFragment extends Fragment {
    private String mTitle;

    public static SimpleTimeCardFragment getInstance(String title) {
        SimpleTimeCardFragment sf = new SimpleTimeCardFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_time_tab_card, null);
//        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
//        card_title_tv.setText(mTitle);

        return v;
    }
}