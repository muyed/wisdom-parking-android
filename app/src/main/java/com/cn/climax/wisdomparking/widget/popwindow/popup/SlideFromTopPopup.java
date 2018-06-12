package com.cn.climax.wisdomparking.widget.popwindow.popup;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cn.climax.i_carlib.util.ContextHolderUtil;
import com.cn.climax.i_carlib.util.phone.ScreenUtil;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.widget.popwindow.me.BasePopupWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 从顶部下滑的Popup
 */

public class SlideFromTopPopup extends BasePopupWindow {

    private final List<String> searchCondition;
    private List<CheckKeyBean> checkListBeen = new ArrayList<>();
    private CheckKeyBean checkKeyBean;
    private int mPosition = 0;
    private final InnerPopupAdapter mAdapter;

    public SlideFromTopPopup(Activity context, final List<String> searchCondition) {
        super(context);
        setBackPressEnable(false);
        setDismissWhenTouchOuside(true);
        this.searchCondition = searchCondition;

        for (int i = 0; i < searchCondition.size(); i++) {
            checkKeyBean = new CheckKeyBean();
            checkKeyBean.setCheckText(searchCondition.get(i));
            checkKeyBean.setCheck(0 == i);
            checkListBeen.add(checkKeyBean);
        }
        ListView listView = (ListView) findViewById(R.id.popup_list);
        mAdapter = new InnerPopupAdapter(context, searchCondition);
        listView.setAdapter(mAdapter);
        mAdapter.setData(checkListBeen);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (int k = 0; k < checkListBeen.size(); k++) {
                            checkListBeen.get(k).setCheck(false);
                        }
                        checkListBeen.get(position).setCheck(true);
                        checkListBeen.get(position).setCheckText(searchCondition.get(position));
                        mAdapter.notifyDataSetChanged();
                        if (selectListener != null)
                            selectListener.selectedKey(searchCondition.get(position));
                        if (listener != null) {
                            listener.selectedKey(position, searchCondition.get(position));
                        }
                    }
                });
    }

    @Override
    protected Animation initShowAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, -ScreenUtil.dip2px(getContext(), 350f), 0);
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new OvershootInterpolator(1));
        return translateAnimation;
    }

    @Override
    protected Animation initExitAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0, -ScreenUtil.dip2px(getContext(), 350f));
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new OvershootInterpolator(-4));
        return translateAnimation;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_select_from_top);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

    //=============================================================adapter
    public class InnerPopupAdapter extends BaseAdapter {

        private List<CheckKeyBean> mCheckBean;
        private LayoutInflater mInflater;
        private Context mContext;
        private List<String> mItemList;

        public InnerPopupAdapter(Context context, @NonNull List<String> itemList) {
            mContext = context;
            mItemList = itemList;
            mInflater = LayoutInflater.from(context);
        }
        
        public void setData(List<CheckKeyBean> checkListBeen){
            this.mCheckBean = checkListBeen;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mItemList.size();
        }

        @Override
        public String getItem(int position) {
            return mItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_popup_list, parent, false);
                vh.mSearchLayout = (LinearLayout) convertView.findViewById(R.id.llSearchLine);
                vh.mTextView = (TextView) convertView.findViewById(R.id.item_tx);
                vh.mSearchIcon = (ImageView) convertView.findViewById(R.id.ivSearchIcon);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            if (mCheckBean.get(position).isCheck()) {
                vh.mSearchIcon.setVisibility(View.VISIBLE);
                vh.mTextView.setTextColor(ContextCompat.getColor(ContextHolderUtil.getContext(), R.color.colorPrimary));
            } else {
                vh.mSearchIcon.setVisibility(View.GONE);
                vh.mTextView.setTextColor(ContextCompat.getColor(ContextHolderUtil.getContext(), R.color.text_common_color));
            }
            vh.mTextView.setText(getItem(position));

            return convertView;
        }

        class ViewHolder {
            public TextView mTextView;
            public ImageView mSearchIcon;
            public LinearLayout mSearchLayout;
        }
    }

    public interface ISearchSelectListener {
        void selectedKey(String textKey);
    }

    private ISearchSelectListener selectListener;

    public ISearchSelectListener getSelectListener() {
        return selectListener;
    }

    public void setSelectListener(ISearchSelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public void setSelectItem(int position) {
        this.mPosition = position;
        checkListBeen.clear();
        for (int i = 0; i < searchCondition.size(); i++) {
            checkKeyBean = new CheckKeyBean();
            checkKeyBean.setCheckText(searchCondition.get(i));
            checkKeyBean.setCheck(mPosition == i);
            checkListBeen.add(checkKeyBean);
        }
        mAdapter.setData(checkListBeen);
    }

    public interface ISelectListener {
        void selectedKey(int index, String text);
    }

    private ISelectListener listener;

    public ISelectListener getListener() {
        return listener;
    }

    public void setListener(ISelectListener listener) {
        this.listener = listener;
    }

    private static class CheckKeyBean implements Serializable {

        private String checkText;
        private boolean isCheck;

        public String getCheckText() {
            return checkText;
        }

        public void setCheckText(String checkText) {
            this.checkText = checkText;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }
}
