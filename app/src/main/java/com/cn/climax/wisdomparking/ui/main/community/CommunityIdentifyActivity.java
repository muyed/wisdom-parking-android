package com.cn.climax.wisdomparking.ui.main.community;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiHost;
import com.cn.climax.i_carlib.okgo.app.apiUtils.ApiManage;
import com.cn.climax.i_carlib.util.ToastUtils;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.Constant;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.data.local.CarLockParkingBean;
import com.cn.climax.wisdomparking.data.response.ParkingSpaceMineBean;
import com.cn.climax.wisdomparking.ui.PeterMainActivity;
import com.cn.climax.wisdomparking.ui.main.device.ReleaseLockActivity;
import com.cn.climax.wisdomparking.ui.main.share.PublishShareParkingActivity;
import com.cn.climax.wisdomparking.util.GlobalVerificateUtils;
import com.cn.climax.wisdomparking.widget.cyclepager.view.CyclePager;
import com.lzy.okgo.callback.StringCallback;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.park.com.zxing.android.CaptureActivity;
import cn.park.com.zxing.bean.ZxingConfig;
import okhttp3.Call;
import okhttp3.Response;

public class CommunityIdentifyActivity extends BaseSwipeBackActivity {

    private int REQUEST_CODE_SCAN = 111;

    @BindView(R.id.lock_viewPager)
    CyclePager lockViewPager;
    @BindView(R.id.ll_point)
    LinearLayout ll_point;
    @BindView(R.id.llSkip2Share)
    LinearLayout llSkip2Share;
    @BindView(R.id.llReleaseLock)
    LinearLayout llReleaseLock;

    @BindView(R.id.tvGoCertParking)
    TextView tvGoCertParking;
    @BindView(R.id.llNoParkingSpace)
    LinearLayout llNoParkingSpace;

    private ArrayList<CarLockParkingBean> lockList = new ArrayList<>();
    private List<ParkingSpaceMineBean> mParkingSpaceMineBeanList = new ArrayList<>();
    private ParkingSpaceMineBean mCurParkingSpaceBean = new ParkingSpaceMineBean();

    private ZxingConfig config = new ZxingConfig();
    private boolean isClickVoice = true; //扫描是否开启声音 默认开启

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(isShowNavBack, "我的车位");
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_identify_community;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        initClick();
        getMyCarport();
    }

    private void getMyCarport() {
        ApiManage.get(ApiHost.getInstance().getMyCarport())
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (response.code() == 200) {
                            try {
                                JSONObject json = new JSONObject(s);
                                mParkingSpaceMineBeanList = com.alibaba.fastjson.JSONObject.parseArray(String.valueOf(json.get("data")), ParkingSpaceMineBean.class);
                                initLockPager();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.show(response.message());
                        }
                    }
                });
    }

    private void initClick() {
        llSkip2Share.setOnClickListener(new CommonClick());
        llReleaseLock.setOnClickListener(new CommonClick());
    }

    private void initLockPager() {
        for (int i = 0; i < mParkingSpaceMineBeanList.size(); i++) {
            CarLockParkingBean parkingBean = new CarLockParkingBean();
            parkingBean.setLockName(mParkingSpaceMineBeanList.get(i).getCarportNum());
            if (!TextUtils.isEmpty(mParkingSpaceMineBeanList.get(i).getAlias()))
                parkingBean.setLockRemark(mParkingSpaceMineBeanList.get(i).getAlias());
            else
                parkingBean.setLockRemark("无备注");
            lockList.add(parkingBean);
        }
        if (lockList == null || lockList.size() == 0) {
            lockViewPager.setVisibility(View.GONE);
            ll_point.setVisibility(View.INVISIBLE);
            llNoParkingSpace.setVisibility(View.VISIBLE);
        } else {
            lockViewPager.setVisibility(View.VISIBLE);
            ll_point.setVisibility(View.VISIBLE);
            llNoParkingSpace.setVisibility(View.GONE);
            if (lockList.size() == 1) {
                lockViewPager.setScrollble(false);
                mCurParkingSpaceBean = mParkingSpaceMineBeanList.get(0);
            } else {
                lockViewPager.setScrollble(true);
            }
            lockViewPager.addPoints(mContext, R.drawable.bg_pointer, ll_point, lockList.size());
            lockViewPager.setImages(mContext, lockList, R.layout.view_pager_lock_item_layout, new CyclePager.OnItemInitLisenter() {
                @Override
                public void initItemView(View view, int position) {
                    TextView tvCarSpaceLockNo = (TextView) view.findViewById(R.id.tvCarSpaceLockNo);
                    TextView tvCarSpaceLockRemark = (TextView) view.findViewById(R.id.tvCarSpaceLockRemark);
                    tvCarSpaceLockNo.setText(lockList.get(position).getLockName());
                    tvCarSpaceLockRemark.setText(lockList.get(position).getLockRemark());
                }

                @Override
                public void onItemClick(int position) {
                }

                @Override
                public void onItemVisible(int position) {
                }
            }, 6);
            lockViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.e("---> onPageScrolled: ", position + "");
                }

                @Override
                public void onPageSelected(int position) {
                    Log.e("---> onPageSelected: ", position + "");
                    mCurParkingSpaceBean = mParkingSpaceMineBeanList.get(position);
                }

                @SuppressLint("LongLogTag")
                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.e("---> onPageScrollState: ", state + "");
                }
            });
        }
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.llSkip2Share: //共享车位锁
                    startActivity(new Intent(CommunityIdentifyActivity.this, PublishShareParkingActivity.class)
                            .putExtra("is_publish_mine", mParkingSpaceMineBeanList != null && mParkingSpaceMineBeanList.size() > 0)
                            .putExtra("parking_mine_bean", mCurParkingSpaceBean));
                    break;
                case R.id.llReleaseLock: //解锁车位锁
                    startActivity(new Intent(CommunityIdentifyActivity.this, ReleaseLockActivity.class));
//                    if (!GlobalVerificateUtils.getInstance(CommunityIdentifyActivity.this).isEnableOption(CommunityIdentifyActivity.this))
//                        return;
//                    judgePermission(false);
                    break;
            }
        }
    }

    private void judgePermission(final boolean isCloseView) {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Intent intent = new Intent(CommunityIdentifyActivity.this, CaptureActivity.class);
                                /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                                 * 也可以不传这个参数
                                 * 不传的话  默认都为默认不震动  其他都为true
                                 * */
                        config.setShake(true);
                        config.setShowFlashLight(true);
                        intent.putExtra(cn.park.com.zxing.common.Constant.INTENT_ZXING_CONFIG, config);
                        startActivityForResult(intent, REQUEST_CODE_SCAN);

//                        if (isCloseView)
//                            ofoMenuKeyBoardLayout.closeKeyboard();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Uri packageURI = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(CommunityIdentifyActivity.this, "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
                    }
                }).start();
    }
}
