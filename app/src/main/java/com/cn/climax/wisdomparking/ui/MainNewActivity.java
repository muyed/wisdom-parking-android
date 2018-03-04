package com.cn.climax.wisdomparking.ui;

import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.widget.slide.SlideLayout;
import com.pinger.swipeview.SwipeScrollView;

public class MainNewActivity extends AppCompatActivity  {

    private SlideLayout mSlideLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        mSlideLayout = (SlideLayout) findViewById(R.id.slideLayout);

        SwipeScrollView swipeScrollView = (SwipeScrollView) findViewById(R.id.swipeScrollView);
        View contentView = View.inflate(this, R.layout.layout_menu_content, null);
        initEvent(contentView);
        swipeScrollView.setContentView(contentView);
    }

    private void initEvent(View view) {
        view.findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainNewActivity.this, "消息中心", Toast.LENGTH_SHORT).show();
                mSlideLayout.close();
            }
        });
        view.findViewById(R.id.skin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainNewActivity.this, "皮肤", Toast.LENGTH_SHORT).show();
                mSlideLayout.close();
            }
        });
        view.findViewById(R.id.vip_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainNewActivity.this, "会员中心", Toast.LENGTH_SHORT).show();
                mSlideLayout.close();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (mSlideLayout.isOpened()) {
            mSlideLayout.close();
        } else {
            super.onBackPressed();
        }
    }
}
