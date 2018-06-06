package com.cn.climax.wisdomparking.widget.bulletinboard.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;

import java.util.List;

/**
 * SimpleBulletinAdapter 简单的垂直公告栏轮播适配器
 */
public class SimpleBulletinAdapter extends BulletinAdapter<String> {

    private int mImageDrawableID = R.drawable.icon_notice;

    public SimpleBulletinAdapter(Context context, List<String> dataList) {
        this(context, dataList, R.drawable.icon_notice);
    }

    public SimpleBulletinAdapter(Context context, List<String> dataList, int imageDrawableID) {
        super(context, dataList);
        mImageDrawableID = imageDrawableID;
    }

    @Override
    public View getView(int position) {

        View view = getRootView(R.layout.simple_item);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
        TextView textView = (TextView) view.findViewById(R.id.tv_content);

        String data = mData.get(position);
        imageView.setImageResource(mImageDrawableID);
        textView.setText(data);
        return view;
    }

}
