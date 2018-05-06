package com.cn.climax.wisdomparking.ui.setting.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.climax.i_carlib.util.glide.GlideUitl;
import com.cn.climax.wisdomparking.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * author：leo on 2018/5/6 0006 14:11
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */
public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.BankViewHolder> {

    private Context mContext;
    private String[] bankPinYinArr = new String[]{"beijing", "gonghang", "guangda", "hebei", "huaxia", "jianhang", "jiaohang", "minsheng", "nonghang", "renmin", "shanghai", "xingye", "youzheng", "zhaohang", "zhengzhou", "zhonghang", "zhongxin"};
    private String[] bankNameArr = new String[]{"北京银行", "中国工商银行", "中国光大银行", "河北银行", "华夏银行", "中国建设银行", "中国交通银行", "中国民生银行", "中国农业银行", "中国人民银行", "上海银行", "兴业银行", "中国邮政", "招商银行", "郑州银行", "中国银行", "中信银行"};

    public BankCardAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public BankCardAdapter.BankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_bank_card_layout, parent, false);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BankCardAdapter.BankViewHolder holder, int position) {
        String iconName = "icon_bank_" + bankPinYinArr[position];
        int iconId = mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName());
        holder.ivBankLogo.setImageResource(iconId);
        holder.tvBankName.setText(bankNameArr[position]);

        if (position % 3 == 0)
            holder.cvBankCard.setCardBackgroundColor(R.color.basic_color_primary);
        else if (position % 3 == 1)
            holder.cvBankCard.setCardBackgroundColor(Color.parseColor("#495AA2"));
        else if (position % 3 == 2)
            holder.cvBankCard.setCardBackgroundColor(Color.parseColor("#F59484"));
        else
            holder.cvBankCard.setCardBackgroundColor(Color.parseColor("#26ca8a"));
    }

    @Override
    public int getItemCount() {
        return bankPinYinArr.length;
    }

    public class BankViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cvBankCard)
        CardView cvBankCard;
        @BindView(R.id.ivBankLogo)
        ImageView ivBankLogo;
        @BindView(R.id.tvBankName)
        TextView tvBankName;
        @BindView(R.id.tvBankCardNo)
        TextView tvBankCardNo;

        public BankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
