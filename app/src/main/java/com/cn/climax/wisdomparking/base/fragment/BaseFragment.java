package com.cn.climax.wisdomparking.base.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cn.climax.i_carlib.util.res.ResourceUtil;
import com.cn.climax.wisdomparking.R;

import butterknife.ButterKnife;


/**
 * author：leo on 2016/11/25 15:44
 * email： leocheung4ever@gmail.com
 * description: Fragment基类
 * what & why is modified:
 */

public abstract class BaseFragment extends ProgressFragment {

    private TextView tvEmpty, tvError, tvLoading;
    private Button btnReload;

    /**
     * 获取Activity传递的值
     */
    protected abstract void getBundle(Bundle bundle);

    /**
     * 初始化控件
     */
    protected abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    protected abstract void initData();

    /**
     * 内容视图值
     */
    protected abstract int initContentView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getBundle(getArguments());
        bindUI(view, savedInstanceState);
//        if (isSetFilters())
//            getAllViews(getActivity());
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void bindUI(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        initUI(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected boolean isSetFilters() {
        return true;
    }

    //-----------------获取 activity中的所有view  禁止绝大部分EditText输入Emoji表情
//    private void getAllViews(Activity act) {
//        getAllChildViews(act.getWindow().getDecorView());
//    }
//
//    private void getAllChildViews(View view) {
//        EmojiInputFilter emojiFilter = new EmojiInputFilter();
//        InputFilter[] emojiFilters = {emojiFilter};
//        if (view instanceof ViewGroup) {
//            ViewGroup vp = (ViewGroup) view;
//            for (int i = 0; i < vp.getChildCount(); i++) {
//                View viewchild = vp.getChildAt(i);
//                if (viewchild instanceof EditText) {
//                    ((EditText) viewchild).setFilters(emojiFilters);
//                }
//                //再次 调用本身（递归）
//                getAllChildViews(viewchild);
//            }
//        }
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater) {
        return inflater.inflate(initContentView(), null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateContentEmptyView(LayoutInflater inflater) {
        View empty = inflater.inflate(R.layout.view_empty_layout, null);
        tvEmpty = empty.findViewById(R.id.tvEmpty);
        btnReload = empty.findViewById(R.id.btnReload);
        empty.findViewById(R.id.btnReload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReloadClicked();
            }
        });
        return empty;
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateContentErrorView(LayoutInflater inflater) {
        View error = inflater.inflate(R.layout.view_error_layout, null);
        tvError = error.findViewById(R.id.tvError);
        error.findViewById(R.id.btnReload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReloadClicked();
            }
        });
        return error;
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateProgressView(LayoutInflater inflater) {
        View loading = inflater.inflate(R.layout.view_loading_layout, null);
        tvLoading = loading.findViewById(R.id.tvLoading);
        ProgressBarCircularIndeterminate progressBar =
                loading.findViewById(R.id.progress_view);
        progressBar.setBackgroundColor(ResourceUtil.getThemeColor(getActivity()));
        return loading;
    }

    //Override this method to reload
    public void onReloadClicked() {
    }

    public void setEmptyText(String text) {
        this.tvEmpty.setText(text);
    }

    public void setEmptyText(int textResId) {
        this.tvEmpty.setText(getString(textResId));
    }

    public void setErrorText(String text) {
        this.tvError.setText(text);
    }

    public void setErrorText(int textResId) {
        this.tvError.setText(getString(textResId));
    }

    public void setLoadingText(String text) {
        this.tvLoading.setText(text);
    }

    public void setLoadingText(int textResId) {
        this.tvLoading.setText(getString(textResId));
    }

    public void setEmptyButtonVisible(int visible) {
        btnReload.setVisibility(visible);
    }
}
