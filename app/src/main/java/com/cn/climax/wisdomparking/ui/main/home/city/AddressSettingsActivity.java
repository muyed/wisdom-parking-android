package com.cn.climax.wisdomparking.ui.main.home.city;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;


import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseActivity;
import com.cn.climax.wisdomparking.data.local.AddressBean;
import com.cn.climax.wisdomparking.ui.main.home.adapter.HistoryAddressAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddressSettingsActivity extends BaseActivity {

    private static final int ADDR_SETTING_TAG = 1;

    @BindView(R.id.atvToolBarMainSearch)
    EditText mEditTextSearchClear;
    @BindView(R.id.recycler_address_settings)
    RecyclerView mAddressSettings;

    private HistoryAddressAdapter mResultAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_address_settings;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initAddressSearchResult();
        initView();
    }

    private void initView() {
        mEditTextSearchClear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = mEditTextSearchClear.getCompoundDrawables()[2]; //得到一个长度为4的数组 分别表示左右上下四张图片
                // 如果右边没有图片  不再处理
                if (drawable == null)
                    return false;
                // 如果不是按下事件 不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > mEditTextSearchClear.getWidth() - mEditTextSearchClear.getPaddingRight() - drawable.getIntrinsicWidth()) {
                    mEditTextSearchClear.setText("");
                }
                return false;
            }
        });
    }

    private void initAddressSearchResult() {
        List<AddressBean> arrAddr = new ArrayList<>();
        AddressBean bean1 = new AddressBean();
        AddressBean bean2 = new AddressBean();
        AddressBean bean3 = new AddressBean();
        bean1.setAddressName("西溪湿地");
        bean1.setAddressLocation("杭州市西湖区天目山路518号");
        bean2.setAddressName("西溪湿地");
        bean2.setAddressLocation("杭州市西湖区天目山路528号");
        bean3.setAddressName("西溪湿地");
        bean3.setAddressLocation("杭州市西湖区天目山路538号");

        arrAddr.add(bean1);
        arrAddr.add(bean2);
        arrAddr.add(bean3);

        mResultAdapter = new HistoryAddressAdapter(new WeakReference<>(getApplicationContext()), arrAddr, ADDR_SETTING_TAG);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAddressSettings.setLayoutManager(mLayoutManager);
        mAddressSettings.setAdapter(mResultAdapter);
    }
}
