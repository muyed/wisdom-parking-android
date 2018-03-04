package com.cn.climax.wisdomparking.ui.main.home.city;

import android.content.Intent;
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
import butterknife.OnClick;

public class AddressSelectedActivity extends BaseActivity {

    private static final int ADDR_SELECT_TAG = 0;

    //~~~~~~~~~~~~~~~~~~~~~~~~~  地址搜索部分 ~~~~~~~~~~~~~~~~~~~~~~~~~~
    @BindView(R.id.atvToolBarMainSearch)
    EditText mEditTextSearchClear;
    @BindView(R.id.recycler_history_address)
    RecyclerView mHistoryRecycleView;

    private HistoryAddressAdapter mHistoryAdapter;

    @Override
    protected int initContentView() {
        return R.layout.activity_address_selected;
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
        initAddressSearch();
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

    private void initAddressSearch() {
        List<AddressBean> arrAddr = new ArrayList<>();
        AddressBean bean1 = new AddressBean();
        AddressBean bean2 = new AddressBean();
        AddressBean bean3 = new AddressBean();
        bean1.setAddressName("西溪湿地");
        bean1.setAddressLocation("杭州市西湖区天目山路518号");
        bean2.setAddressName("武林广场");
        bean2.setAddressLocation("下城区延安路");
        bean3.setAddressName("杭州城西银泰城");
        bean3.setAddressLocation("浙江省杭州市拱墅区丰潭路380号");

        arrAddr.add(bean1);
        arrAddr.add(bean2);
        arrAddr.add(bean3);

        mHistoryAdapter = new HistoryAddressAdapter(new WeakReference<>(getApplicationContext()), arrAddr, ADDR_SELECT_TAG);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mHistoryRecycleView.setLayoutManager(mLayoutManager);
        mHistoryRecycleView.setAdapter(mHistoryAdapter);
    }

    @OnClick({ R.id.allAddress4Home, R.id.allAddress4Company, R.id.bb_set_common_address})
    void click(View view) {
        switch (view.getId()) {
//            case R.id.allNavBack:
//                closeAddressSearchLayout();
//                break;
            case R.id.allAddress4Home:
                startActivity(new Intent(AddressSelectedActivity.this, AddressSettingsActivity.class));
                break;
            case R.id.allAddress4Company:
                startActivity(new Intent(AddressSelectedActivity.this, AddressSettingsActivity.class));
                break;
            case R.id.bb_set_common_address:
                startActivity(new Intent(AddressSelectedActivity.this, AddressSettingsActivity.class));
                break;
//            case R.id.tvConfirmPage:
//                closeAddressSearchLayout();
//                break;
        }
    }

    private void closeAddressSearchLayout() {
        finish();
        overridePendingTransition(R.anim.pop_no_anim, R.anim.pop_down_anim);
    }
}
