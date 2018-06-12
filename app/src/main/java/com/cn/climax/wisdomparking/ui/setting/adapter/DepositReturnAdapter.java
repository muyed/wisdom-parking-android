package com.cn.climax.wisdomparking.ui.setting.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiParamsKey;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.Core;
import com.cn.climax.wisdomparking.data.response.LoginResponse;
import com.cn.climax.wisdomparking.data.response.ParkingSpaceMineBean;
import com.cn.climax.wisdomparking.ui.setting.DepositMineActivity;
import com.cn.climax.wisdomparking.ui.setting.DepositReturnActivity;
import com.cn.climax.wisdomparking.ui.setting.bank.WithDrawalActivity;
import com.lzy.okgo.callback.StringCallback;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * author：leo on 2018/6/11 0011 10:00
 * email： leocheung4ever@gmail.com
 * description: 退回押金 适配器
 * what & why is modified:
 */
public class DepositReturnAdapter extends RecyclerView.Adapter<DepositReturnAdapter.ViewHolder> {

    private String mDepositAmount;
    private List<ParkingSpaceMineBean> mUserCarportList;
    private Context mContext;

    public DepositReturnAdapter(Context context, String depositAmount) {
        this.mContext = context;
        this.mDepositAmount = depositAmount;
    }

    @Override
    public DepositReturnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_return_deposit_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(DepositReturnAdapter.ViewHolder holder, final int position) {
        holder.tvParkingInfo.setText("车位：" + mUserCarportList.get(position).getCarportNum());
        if (String.valueOf(mUserCarportList.get(position).getDeposit()).equals("0.00")
                || mUserCarportList.get(position).getDeposit() == 0.00d
                || mUserCarportList.get(position).getDeposit() == 0.0d
                || mUserCarportList.get(position).getDeposit() == 0d) {
            holder.tvGoToDeposit.setVisibility(View.VISIBLE);
            holder.llHasDepositedArea.setVisibility(View.GONE);
            holder.tvGoToDeposit.setOnClickListener(new ForbidQuickClickListener() {
                @Override
                protected void forbidClick(View view) {
                    ((Activity) mContext).startActivityForResult(new Intent(mContext, DepositMineActivity.class)
                            .putExtra("is_pay_account", false)
                            .putExtra("pay_carport_deposit", mDepositAmount), 199);
                }
            });
        } else {
            holder.tvGoToDeposit.setVisibility(View.VISIBLE);
            holder.llHasDepositedArea.setVisibility(View.VISIBLE);
            holder.tvGoToDeposit.setText("退押金");
            holder.tvGoToDeposit.setBackgroundResource(R.drawable.activity_button_red_border);
            holder.tvGoToDeposit.setOnClickListener(new ForbidQuickClickListener() {
                @Override
                protected void forbidClick(View view) {
                    showDialog4Withdraw(mUserCarportList.get(position));
                }
            });
        }
    }

    private void showDialog4Withdraw(final ParkingSpaceMineBean parkingSpaceMineBean) {
        final View contentView = Core.getInstances().getCurrentActivity().getLayoutInflater().inflate(R.layout.dialog_hint_withdraw, null);
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

        contentView.findViewById(R.id.tvConfirmPreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
            }
        });
        contentView.findViewById(R.id.tvWithdrawPreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, WithDrawalActivity.class)
                        .putExtra("is_withdraw_carport", true)
                        .putExtra("deposit_carport_amount", mDepositAmount)
                        .putExtra("deposit_carport_bean", parkingSpaceMineBean));
                dialogPlus.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserCarportList != null && mUserCarportList.size() > 0 ? mUserCarportList.size() : 0;
    }

    public void setData(List<ParkingSpaceMineBean> userCarportList) {
        mUserCarportList = new ArrayList<>();
        this.mUserCarportList = userCarportList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvParkingInfo)
        TextView tvParkingInfo;
        @BindView(R.id.llHasDepositedArea)
        LinearLayout llHasDepositedArea;
        @BindView(R.id.tvGoToDeposit)
        TextView tvGoToDeposit;
        @BindView(R.id.tvCarportDeposit)
        TextView tvCarportDeposit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
