package cn.hs.com.wovencloud.base.me.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.app.framework.loger.Loger;

import cn.hs.com.wovencloud.R;
import cn.hs.com.wovencloud.base.me.state.CollectState;
import cn.hs.com.wovencloud.base.me.state.ContentState;
import cn.hs.com.wovencloud.base.me.state.EmptyState;
import cn.hs.com.wovencloud.base.me.state.ErrorState;
import cn.hs.com.wovencloud.base.me.state.LoginState;
import cn.hs.com.wovencloud.base.me.state.NonState;
import cn.hs.com.wovencloud.base.me.state.ProgressState;
import cn.hs.com.wovencloud.base.me.state.ShowState;


/**
 * author：leo on 2016/11/25 15:46
 * email： leocheung4ever@gmail.com
 * description: 带有进度显示的Fragment
 * what & why is modified:
 */

public class ProgressFragment extends Fragment {

    private ViewGroup mContentView;
    private Animation mAnimIn;
    private Animation mAnimOut;
    private ShowState mContentState, mEmptyState, mErrorState, mProgressState, mLoginState, mCollectState;
    private ShowState mLastState = new NonState();

    public boolean isPrepare = false;

    //for overriding to change content view
    public View onCreateContentView(LayoutInflater inflater) {
        return null;
    }

    //for overriding to change error view
    public View onCreateContentErrorView(LayoutInflater inflater) {
        return null;
    }

    //for overriding to change empty view
    public View onCreateContentEmptyView(LayoutInflater inflater) {
        return null;
    }

    //for overriding to change progress view
    public View onCreateProgressView(LayoutInflater inflater) {
        return null;
    }

    //for overriding to change login view
    public View onCreateContentLoginView(LayoutInflater inflater) {
        return null;
    }

    //for overriding to change collect view
    public View onCreateContentCollectView(LayoutInflater inflater) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup main = (ViewGroup) inflater.inflate(R.layout.fragment_empty_layout, container, false);

        View content = onCreateContentView(inflater);
        View error = onCreateContentErrorView(inflater);
        View empty = onCreateContentEmptyView(inflater);
        View progress = onCreateProgressView(inflater);

        View not_login = onCreateContentLoginView(inflater);
        View not_collection = onCreateContentCollectView(inflater);

        replaceViewById(main, R.id.epf_content, content);
        replaceViewById(main, R.id.epf_error, error);
        replaceViewById(main, R.id.epf_empty, empty);
        replaceViewById(main, R.id.epf_progress, progress);

        replaceViewById(main, R.id.epf_login, not_login);
        replaceViewById(main, R.id.epf_collect, not_collection);

        mContentView = main;

        mAnimIn = onCreateAnimationIn();
        mAnimOut = onCreateAnimationOut();

        initStates();
        isPrepare = true;
        return main;
    }

    public Animation onCreateAnimationOut() {
        return AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
    }

    public Animation onCreateAnimationIn() {
        return AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
    }

    private void replaceViewById(ViewGroup container, int viewId, View newView) {
        if (newView == null)
            return;
        newView.setId(viewId);
        View oldView = container.findViewById(viewId);
        int index = container.indexOfChild(oldView);
        container.removeView(oldView);
        container.addView(newView, index);

        newView.setVisibility(View.GONE);
    }

    private void initStates() {
        mContentState = new ContentState();
        mEmptyState = new EmptyState();
        mErrorState = new ErrorState();
        mProgressState = new ProgressState();

        mLoginState = new LoginState();
        mCollectState = new CollectState();

        initState(mContentState);
        initState(mEmptyState);
        initState(mErrorState);
        initState(mProgressState);

        initState(mLoginState);
        initState(mCollectState);
    }

    private void initState(ShowState state) {
        state.setAnimIn(mAnimIn);
        state.setAnimOut(mAnimOut);
        state.setFragmentView(mContentView);
    }

    public void showContent(boolean animate) {
        if (mLastState == mContentState)
            return;
        mContentState.show(animate);
        mLastState.dismiss(animate);
        mLastState = mContentState;
    }

    public void showEmpty(boolean animate) {
        if (mLastState == mEmptyState) {
            return;
        }
        mEmptyState.show(animate);
        mLastState.dismiss(animate);
        mLastState = mEmptyState;
    }

    public void showError(boolean animate) {
        if (mLastState == mErrorState) {
            return;
        }
        mErrorState.show(animate);
        mLastState.dismiss(animate);
        mLastState = mErrorState;
    }

    public void showProgress(boolean animate) {
        if (mLastState == mProgressState) {
            return;
        }
        mProgressState.show(animate);
        mLastState.dismiss(animate);
        mLastState = mProgressState;
    }

    public void showLogin(boolean animate) {
        if (mLastState == mLoginState) {
            return;
        }
        mLoginState.show(animate);
        mLastState.dismiss(animate);
        mLastState = mLoginState;
    }

    public void showCollect(boolean animate) {
        if (mLastState == mCollectState) {
            return;
        }
        mCollectState.show(animate);
        mLastState.dismiss(animate);
        mLastState = mCollectState;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
