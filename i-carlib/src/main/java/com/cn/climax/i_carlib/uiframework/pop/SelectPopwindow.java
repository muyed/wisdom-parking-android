package com.cn.climax.i_carlib.uiframework.pop;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cn.climax.i_carlib.R;
import com.cn.climax.i_carlib.okgo.app.BaseApplication;

public class SelectPopwindow extends BasePopwindow {
    private static final String TAG = "SelectPopwindow";

    private String[] strings;

    private SelectAdapter.OnClick click;

    public SelectPopwindow(String[] strings, SelectAdapter.OnClick click) {
        this.click = click;
        this.strings = strings;
        bottomPopup();
        init();
    }


    @Override
    public int initLayoutId() {
        return R.layout.popwindow_select;
    }

    @Override
    public void initView(View view) {
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.selectRV);
        rv.setLayoutManager(new LinearLayoutManager(BaseApplication.getInstance().getContext()));
        rv.setAdapter(new SelectAdapter(strings, click, this));
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
