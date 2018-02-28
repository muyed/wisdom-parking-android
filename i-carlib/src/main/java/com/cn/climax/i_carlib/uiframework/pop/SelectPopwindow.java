package com.app.framework.widget.popwindow;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.framework.R;
import com.app.framework.app.BaseApplication;

/**
 * author:xiongx 2017/9/14.
 */

public class SelectPopwindow extends BasePopwindow {
    private static final String TAG = "SelectPopwindow";

    private String[] strings;

    private SelectAdapter.OnClick click;
    public SelectPopwindow(String[] strings, SelectAdapter.OnClick click){
        this.click=click;
        this.strings=strings;
        bottomPopup();
        init();
    }



    @Override
    public int initLayoutId() {
        return R.layout.popwindow_select;
    }

    @Override
    public void initView(View view) {
        RecyclerView rv=view.findViewById(R.id.selectRV);
        rv.setLayoutManager(new LinearLayoutManager(BaseApplication.getInstance().getContext()));
        rv.setAdapter(new SelectAdapter(strings,click,this));
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
