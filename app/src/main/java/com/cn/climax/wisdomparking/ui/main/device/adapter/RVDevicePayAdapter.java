package com.cn.climax.wisdomparking.ui.main.device.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.local.BaseLocalBean;
import com.cn.climax.wisdomparking.util.Number2ChineseUtils;
import com.cn.climax.wisdomparking.widget.ACheckBox;
import com.sina.weibo.sdk.api.share.Base;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：leo on 2018/3/5 0005 00:40
 * email： leocheung4ever@gmail.com
 * description: 添加设备 支付 适配器
 * what & why is modified:
 */
public class RVDevicePayAdapter extends RecyclerView.Adapter<RVDevicePayAdapter.ParkSpaceViewHolder> {

    private Context mContext;
    private List<BaseLocalBean> mBaseBeanList = new ArrayList<>();

    public RVDevicePayAdapter(Context context) {
        this.mContext = context;
        initData();
    }

    private void initData() {
        BaseLocalBean localBean1 = new BaseLocalBean();
        localBean1.setPayWay(0);
        localBean1.setChecked(true);
        localBean1.setPayIconId(R.drawable.icon_pay_zhifubao);
        localBean1.setPayName("支付宝支付");
        localBean1.setPaySubtitle("推荐有支付宝账户的用户使用");
        mBaseBeanList.add(localBean1);

        BaseLocalBean localBean2 = new BaseLocalBean();
        localBean2.setPayWay(1);
        localBean2.setChecked(false);
        localBean2.setPayIconId(R.drawable.icon_pay_weixin);
        localBean2.setPayName("微信支付");
        localBean2.setPaySubtitle("推荐有微信账户的用户使用");
        mBaseBeanList.add(localBean2);
    }

    @Override
    public RVDevicePayAdapter.ParkSpaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pay_device_park_layout, parent, false);
        return new ParkSpaceViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RVDevicePayAdapter.ParkSpaceViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.ivPayIcon.setImageResource(mBaseBeanList.get(position).getPayIconId());
        holder.tvPayName.setText(mBaseBeanList.get(position).getPayName());
        holder.tvPaySubName.setText(mBaseBeanList.get(position).getPaySubtitle());
        if (mBaseBeanList.get(position).isChecked())
            holder.acbCheckWay.setVisibility(View.VISIBLE);
        holder.acbCheckWay.setChecked(mBaseBeanList.get(position).isChecked());
        holder.acbCheckWay.setClickable(false);
        holder.rlCheckPayWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mBaseBeanList.size(); i++) {
                    if (i == position) {
                        mBaseBeanList.get(i).setChecked(true);
                        if (checkPayWayListener != null) {
                            checkPayWayListener.pay(mBaseBeanList.get(i));
                        }
                    } else {
                        mBaseBeanList.get(i).setChecked(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBaseBeanList.size();
    }


    public class ParkSpaceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPayIcon)
        ImageView ivPayIcon;
        @BindView(R.id.tvPayName)
        TextView tvPayName;
        @BindView(R.id.tvPaySubName)
        TextView tvPaySubName;
        @BindView(R.id.acbCheckWay)
        ACheckBox acbCheckWay;
        @BindView(R.id.rlCheckPayWay)
        RelativeLayout rlCheckPayWay;

        public ParkSpaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnCheckPayWayListener {
        void pay(BaseLocalBean payBean);
    }

    private OnCheckPayWayListener checkPayWayListener;

    public OnCheckPayWayListener getCheckPayWayListener() {
        return checkPayWayListener;
    }

    public void setCheckPayWayListener(OnCheckPayWayListener checkPayWayListener) {
        this.checkPayWayListener = checkPayWayListener;
    }
}
