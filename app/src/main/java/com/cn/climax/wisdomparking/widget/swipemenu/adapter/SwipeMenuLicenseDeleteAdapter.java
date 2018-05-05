package com.cn.climax.wisdomparking.widget.swipemenu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.response.CarLicenseMineBean;
import com.cn.climax.wisdomparking.widget.swipemenu.SwipeMenuItemLayout;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * author：leo on 2017/9/25 0025 19:26
 * email： leocheung4ever@gmail.com
 * description: 侧滑菜单 删除车牌适配器
 * what & why is modified:
 */
public class SwipeMenuLicenseDeleteAdapter extends RecyclerView.Adapter<SwipeMenuLicenseDeleteAdapter.ViewHolder> {

    private SwipeMenuItemLayout mOpenView = null;
    private Context mContext;
    private List<Boolean> menuStatusList = new ArrayList<>();
    private List<CarLicenseMineBean> mCarLicenseList = new ArrayList<>();
    private boolean isFromSelect = false;

    public SwipeMenuLicenseDeleteAdapter(Context context) {
        this.mContext = context;
        this.menuStatusList = new ArrayList<>();
    }

    @Override
    public SwipeMenuLicenseDeleteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_license_delete_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SwipeMenuLicenseDeleteAdapter.ViewHolder holder, final int position) {
        holder.tvCarLicenseNo.setText(mCarLicenseList.get(position).getLicense());
        if (isFromSelect) {
            holder.llSalesContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("car_license_num", mCarLicenseList.get(position));
                    ((Activity) mContext).setResult(Activity.RESULT_OK, intent);
                    ((Activity) mContext).finish();
                }
            });
            holder.smilRoot.setMenuOpen(false);
            holder.smilRoot.setMenuOpenByNoScroll(false);
            holder.smilRoot.setMenuStatusChangerListener(null);
            holder.tvDeleteSalesman.setVisibility(View.GONE);
        } else {
            final boolean menuOpen = menuStatusList.get(position);
            holder.smilRoot.setMenuStatusChangerListener(new SwipeMenuItemLayout.IMenuStatusChangerListener() {
                @Override
                public void onMenuStatusChangeListener(boolean isOpen) {
                    menuStatusList.set(position, isOpen);
                    if (isOpen) {
                        mOpenView = holder.smilRoot;
                    }
                }
            });
            holder.smilRoot.setMenuOpenByNoScroll(menuOpen);
            holder.llSalesContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.smilRoot.setMenuOpen(false);

                }
            });
            holder.tvDeleteSalesman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.smilRoot.setMenuOpen(false);
                    ApiManage.get(ApiHost.getInstance().deleteCarLicense() + mCarLicenseList.get(position).getId())
                            .tag(this)// 请求的 tag, 主要用于取消对应的请求
                            .cacheKey("cacheKey")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    if (response.code() == 200) {
                                        try {
                                            JSONObject json = new JSONObject(s);
                                            if (Integer.parseInt(String.valueOf(json.get("code"))) == 200) {
                                                mCarLicenseList.remove(position);
                                                notifyDataSetChanged();
                                            } else {
                                                ToastUtils.show(String.valueOf(json.get("errMsg")));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        ToastUtils.show(response.message());
                                    }
                                }
                            });
                }
            });
        }
    }

    /**
     * 获取打开菜单的菜单项索引
     */
    public int getMenuOpenItemIndex() {
        for (int i = 0; i < menuStatusList.size(); i++) {
            if (menuStatusList.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public void closeMenus() {
        mOpenView.setMenuOpen(false);
    }

    @Override
    public int getItemCount() {
        return mCarLicenseList != null ? mCarLicenseList.size() : 0;
    }

    public void setDatas(List<CarLicenseMineBean> licenseMineBeen, boolean isFromSelect) {
        this.mCarLicenseList = licenseMineBeen;
        this.isFromSelect = isFromSelect;
        this.menuStatusList = new ArrayList<>();
        menuStatusList.clear();
        for (int i = 0; i < mCarLicenseList.size(); i++) {
            menuStatusList.add(false);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llSalesItem)
        LinearLayout llSalesItem;
        @BindView(R.id.llSalesContent)
        LinearLayout llSalesContent;
        @BindView(R.id.smil_root)
        SwipeMenuItemLayout smilRoot;
        @BindView(R.id.tvCarLicenseNo)
        TextView tvCarLicenseNo;
        @BindView(R.id.llDeleteSales)
        LinearLayout llDeleteSales;
        @BindView(R.id.tvDeleteSalesman)
        TextView tvDeleteSalesman;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
