package com.cn.climax.wisdomparking.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cn.climax.wisdomparking.R;

/**
 * 启动页面
 */

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        findViewById(R.id.convex_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //凹进去
//                startActivity(new Intent(LaunchActivity.this, OfoConvcaveMenuActivity.class));
            }
        });

        findViewById(R.id.concave_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(LaunchActivity.this, OfoMenuActivity.class));
            }
        });
    }
}
