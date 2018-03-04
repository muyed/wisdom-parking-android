package com.cn.climax.wisdomparking.ui.setting;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cn.climax.i_carlib.uiframework.bootstrap.AwesomeTextView;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.MenuSwipeBackActivity;
import com.cn.climax.wisdomparking.ui.setting.adapter.RvCarGarageAdapter;
import com.cn.climax.wisdomparking.widget.recyclerview.LinearLayoutManagerWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MineGarageActivity extends MenuSwipeBackActivity {

    @BindView(R.id.atvToolBarMenu)
    AwesomeTextView atvToolBarMenu;
    @BindView(R.id.rvCarList)
    RecyclerView rvCarList;

    private RecyclerView.ItemDecoration itemDecoration;
    private RvCarGarageAdapter rvAdapter;
    private List<Integer> datas;

    @Override
    protected int initContentView() {
        return R.layout.activity_mine_garage;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        setToolBar(true, "我的车库");
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            datas.add(i);
        }
        /**第一步：设置布局管理器**/
        rvCarList.setLayoutManager(new LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, false));
        /**第二步：添加分割线**/
        itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvCarList.addItemDecoration(itemDecoration);
        /**第三步：设置适配器**/
        rvAdapter = new RvCarGarageAdapter(this);
        rvCarList.setAdapter(rvAdapter);
        rvAdapter.setOnItemClickListener(new RvCarGarageAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, Integer data) {
                Toast.makeText(MineGarageActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.atvToolBarMenu})
    void click(View view) {
        switch (view.getId()) {
            case R.id.atvToolBarMenu:
                rvAdapter.notifyItemInserted(1);
                break;
        }
    }
}
