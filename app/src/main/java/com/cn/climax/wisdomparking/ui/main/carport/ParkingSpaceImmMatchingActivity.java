package com.cn.climax.wisdomparking.ui.main.carport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;

import butterknife.BindView;

public class ParkingSpaceImmMatchingActivity extends BaseSwipeBackActivity {

    @BindView(R.id.atvToolBarMainSearch)
    EditText atvToolBarMainSearch;
    @BindView(R.id.atvLeftTitle)
    TextView atvLeftTitle;
    
    @BindView(R.id.btnImmediatelyMatching)
    Button btnImmediatelyMatching;

    @Override
    protected int initContentView() {
        return R.layout.activity_parking_space_immediately_matching;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        atvToolBarMainSearch.setHint("请搜索关键字");
        atvLeftTitle.setText("地图");
        
        initClick();
    }

    private void initClick() {
        btnImmediatelyMatching.setOnClickListener(new CommonClick());
    }

    private class CommonClick extends ForbidQuickClickListener {
        
        @Override
        protected void forbidClick(View view) {
            switch (view.getId()){
                case R.id.btnImmediatelyMatching:
                    startActivity(new Intent(ParkingSpaceImmMatchingActivity.this, ParkingSpaceMatchActivity.class));
                    break;
            }
        }
    }

}
