package com.cn.climax.wisdomparking.ui.setting.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.ui.setting.utils.BankManager;
import com.cn.climax.wisdomparking.widget.CircleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/6/5 0005 16:25
 * email： leocheung4ever@gmail.com
 * description: 银行列表
 * what & why is modified:
 */
public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.ViewHolder> {

    private Context mContext;

    public BankListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public BankListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bank_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BankListAdapter.ViewHolder holder, final int position) {
        final String iconName = "icon_bank_" + BankManager.bankPinYinArr[position];
        final int iconId = mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName());
        holder.cvBankLogo.setImageResource(iconId);
        holder.tvBankName.setText(BankManager.bankNameArr[position]);
        holder.llClickBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("icon_bank_logo", iconId);
                intent.putExtra("icon_bank_name", BankManager.bankNameArr[position]);
                intent.putExtra("icon_bank_code", BankManager.bankCodeArr[position]);
                ((Activity) mContext).setResult(((Activity) mContext).RESULT_OK, intent);
                ((Activity) mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return BankManager.bankNameArr.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llClickBank)
        LinearLayout llClickBank;
        @BindView(R.id.cvBankLogo)
        CircleView cvBankLogo;
        @BindView(R.id.tvBankName)
        TextView tvBankName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
