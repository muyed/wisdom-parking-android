package com.cn.climax.wisdomparking.ui.setting.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.response.BankCardMineBean;
import com.cn.climax.wisdomparking.ui.setting.utils.BankManager;
import com.cn.climax.wisdomparking.util.BankCardUtil;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;


/**
 * author：leo on 2018/5/6 0006 14:11
 * email： leocheung4ever@gmail.com
 * description: TODO
 * what & why is modified:
 */
public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.BankViewHolder> {

    private Context mContext;

    private List<BankCardMineBean> mListBean;

    public BankCardAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public BankCardAdapter.BankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_bank_card_layout, parent, false);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BankCardAdapter.BankViewHolder holder, int position) {
        holder.ivBankLogo.setImageResource(BankManager.getImageResId(mContext, mListBean.get(position).getBankName()));
        holder.tvBankName.setText(mListBean.get(position).getBankName());
        holder.tvBankCardNo.setText(BankCardUtil.hideCardNo(mListBean.get(position).getBankAccount()));
//        holder.cvBankCard.setCardBackgroundColor(BankManager.setBgColor(mListBean.get(position).getBankName()));
        holder.tvUnBindBank.setOnClickListener(new CommonClick(mListBean.get(position), position));
        holder.cvBankCard.setOnClickListener(new CommonClick(mListBean.get(position), position));

        // 用来提取颜色的Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), BankManager.getImageResId(mContext, mListBean.get(position).getBankName()));
        // Palette的部分 
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
                //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
                if (vibrant != null)
                    holder.cvBankCard.setBackgroundColor(vibrant.getRgb());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mListBean != null && mListBean.size() > 0 ? mListBean.size() : 0;
    }

    public void setDatas(List<BankCardMineBean> bankListBean) {
        mListBean = bankListBean;
        notifyDataSetChanged();
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

        @BindView(R.id.tvUnBindBank)
        TextView tvUnBindBank;

        public BankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class CommonClick extends ForbidQuickClickListener {

        private int mPosition;
        private BankCardMineBean mCardMineBean;

        public CommonClick(BankCardMineBean cardMineBean, int position) {
            this.mCardMineBean = cardMineBean;
            this.mPosition = position;
        }

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.tvUnBindBank:
                    ApiManage.get(ApiHost.getInstance().delBank() + mCardMineBean.getId())
                            .tag(this)// 请求的 tag, 主要用于取消对应的请求
                            .cacheKey("cacheKey")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    mListBean.remove(mPosition);
                                    notifyDataSetChanged();
                                }
                            });
                    break;
                case R.id.cvBankCard:
                    Intent intent = new Intent();
                    intent.putExtra("card_bean", mCardMineBean);
                    ((Activity) mContext).setResult(Activity.RESULT_OK, intent);
                    ((Activity) mContext).finish();
                    break;
            }
        }
    }
}
