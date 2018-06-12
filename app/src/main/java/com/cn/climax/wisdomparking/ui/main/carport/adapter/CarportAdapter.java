package com.cn.climax.wisdomparking.ui.main.carport.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.Core;
import com.cn.climax.wisdomparking.data.local.CarLockParkingBean;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnDismissListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author：leo on 2018/6/9 0009 09:17
 * email： leocheung4ever@gmail.com
 * description: 我的车位锁适配器
 * what & why is modified:
 */
public class CarportAdapter extends PagerAdapter {

    private List<View> viewList;
    private ArrayList<CarLockParkingBean> mLockList;

    public CarportAdapter() {
        this.viewList = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View view = viewList.get(position);

        TextView tvCarSpaceLockNo = (TextView) view.findViewById(R.id.tvCarSpaceLockNo);
        TextView tvCarSpaceLockRemark = (TextView) view.findViewById(R.id.tvCarSpaceLockRemark);
        ImageView tvEditAliasName = (ImageView) view.findViewById(R.id.tvEditAliasName);
        tvCarSpaceLockNo.setText(mLockList.get(position).getLockName());
        tvCarSpaceLockRemark.setText(mLockList.get(position).getLockRemark());
        tvEditAliasName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        collection.addView(view);
        return view;
    }

    private void showEditDialog() {
        final View contentView = Core.getInstances().getCurrentActivity().getLayoutInflater().inflate(R.layout.dialog_edit_view, null);
        com.orhanobut.dialogplus.ViewHolder DialogViewHolder = new com.orhanobut.dialogplus.ViewHolder(contentView);
        final DialogPlus dialogPlus = DialogPlus.newDialog(Core.getInstances().getCurrentContext())
                .setContentHolder(DialogViewHolder)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogPlus dialog) {
                    }
                })
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {
                    }
                })
                .create();
        dialogPlus.show();

        contentView.findViewById(R.id.tvWithdrawPreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
            }
        });
        contentView.findViewById(R.id.tvConfirmPreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ApiManage.post(ApiHost.getInstance().changeCarAlias())
//                        .params(ApiParamsKey.)
//                        .params()
                dialogPlus.dismiss();
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setData(@Nullable List<View> list, ArrayList<CarLockParkingBean> lockList) {
        this.viewList.clear();
        this.mLockList = lockList;
        if (list != null && !list.isEmpty()) {
            this.viewList.addAll(list);
        }

        notifyDataSetChanged();
    }

    @NonNull
    public List<View> getData() {
        if (viewList == null) {
            viewList = new ArrayList<>();
        }
        return viewList;
    }
}
