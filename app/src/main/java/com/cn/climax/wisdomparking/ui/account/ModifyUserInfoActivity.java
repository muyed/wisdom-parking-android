package cn.hs.com.wovencloud.ui.common.account;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.framework.app.ForbidQuickClickListener;
import com.app.framework.utils.SharedUtil;
import com.app.framework.utils.StringUtils;
import com.app.framework.utils.glide.GlideUitl;
import com.app.framework.widget.edit.BytesTextWatcher;
import com.app.framework.widget.photoPicker.PhotoPickerActivity;
import com.app.framework.widget.photoPicker.camera.CameraUtil;
import com.app.framework.widget.popwindow.SelectAdapter;
import com.app.framework.widget.popwindow.SelectPopwindow;
import com.lzy.okgo.model.HttpParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hs.com.wovencloud.Core;
import cn.hs.com.wovencloud.R;
import cn.hs.com.wovencloud.base.OnCheckPermissionSucceedListent;
import cn.hs.com.wovencloud.base.global.Constant;
import cn.hs.com.wovencloud.base.me.BaseSwipeBackActivity;
import cn.hs.com.wovencloud.data.apiUtils.ApiHost;
import cn.hs.com.wovencloud.data.apiUtils.ApiManage;
import cn.hs.com.wovencloud.data.apiUtils.ApiParamsKey;
import cn.hs.com.wovencloud.data.apiUtils.UploadUtil;
import cn.hs.com.wovencloud.data.apiUtils.WrapJsonBeanCallback;
import cn.hs.com.wovencloud.data.remote.response.ModifyInfoBean;
import cn.hs.com.wovencloud.ui.MainActivity;
import cn.hs.com.wovencloud.util.ContextHolderUtil;
import cn.hs.com.wovencloud.util.SoftInputUtil;
import cn.hs.com.wovencloud.util.TT;
import okhttp3.Call;
import okhttp3.Response;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import pub.devrel.easypermissions.AppSettingsDialog;

@RuntimePermissions
public class ModifyUserInfoActivity extends BaseSwipeBackActivity {

    private static final String TAG = ModifyUserInfoActivity.class.getSimpleName();

    @BindView(R.id.ivUserAvatar)
    ImageView ivUserAvatar;
    @BindView(R.id.etNickName) EditText etNickName;
    @BindView(R.id.btnCommitUserInfo)
    Button btnCommitUserInfo;

    private String mNickName;
    private String logoUrl = null;
    private boolean isModifySuccessTag = false; //是否个人信息完善成功 默认是不成功,跳转首页之后即不用进行完善操作

    @Override
    protected int initContentView() {
        return R.layout.activity_modify_user_info_new;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected void setToolBar(boolean isShowNavBack, String headerTitle) {
        super.setToolBar(false, "完善个人资料");
    }

    @Override
    protected boolean isNeedGotUserInfo() {
        return false;
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(false);
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        etNickName.addTextChangedListener(new BytesTextWatcher(16));
        initClickForbid();
    }

    private void initClickForbid() {
        ivUserAvatar.setOnClickListener(new ClickQuickForbid());
        btnCommitUserInfo.setOnClickListener(new ClickQuickForbid());
    }

    public class ClickQuickForbid extends ForbidQuickClickListener {
        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.ivUserAvatar: //上传头像
                    ModifyUserInfoActivityPermissionsDispatcher.clickPhotoWithCheck(ModifyUserInfoActivity.this, view);
                    break;
                case R.id.btnCommitUserInfo:
                    mNickName = etNickName.getText().toString();
                    if (mNickName.equals("")) {
                        TT.showShortWarn("请输入昵称");
                    } else {
                        if (StringUtils.getChartLength(mNickName) < 4) {
                            TT.showShortWarn("昵称长度最少四位");
                        } else if (StringUtils.getChartLength(mNickName) > 16) {
                            TT.showShortWarn("昵称长度最长16位");
                        } else {
                            modifyUserInfo(logoUrl);
                        }
                        SoftInputUtil.hideSoftInput(ModifyUserInfoActivity.this, view);
                    }
                    break;
            }
        }
    }

    private void modifyUserInfo(final String logoUrl) {
        HttpParams avatarHttpParams = new HttpParams();
        if (TextUtils.isEmpty(logoUrl)) {
            avatarHttpParams.put(ApiParamsKey.LOGO_URL, "https://p.jzyb2b.com/z_images/60a7eaefe8257f272156ed625b77f4b5");
        } else {
            avatarHttpParams.put(ApiParamsKey.LOGO_URL, logoUrl);
        }
        ApiManage.postEx(ApiHost.getInstance().modifyUserInfo())
                .params(ApiParamsKey.USER_ID, SharedUtil.getInstance(ContextHolderUtil.getContext()).get(ApiParamsKey.USER_ID))
                .params(avatarHttpParams)
                .params(ApiParamsKey.USER_ALIAS_NAME, mNickName)
                .execute(new WrapJsonBeanCallback<ModifyInfoBean>(ModifyUserInfoActivity.this) {
                    @Override
                    protected void onExecuteSuccess(ModifyInfoBean bean, Call call) {
                        if (bean != null) {
                            isModifySuccessTag = true;
                            SharedUtil.getInstance(ModifyUserInfoActivity.this).put(ApiParamsKey.USER_ALIAS_NAME, mNickName);
                            SharedUtil.getInstance(ModifyUserInfoActivity.this).put(ApiParamsKey.LOGO_URL, logoUrl);
                            SharedUtil.getInstance(ModifyUserInfoActivity.this).put(ApiParamsKey.USER_ID, bean.getUser_id());
                            SharedUtil.getInstance(ModifyUserInfoActivity.this).put(ApiParamsKey.MODIFY_TAG, mNickName);
                            judgeToSkip();
                        }
                    }

                    @Override
                    protected void onJsonParseException(int code, String msg, Call call) {
                    }

                    @Override
                    protected void onExecuteError(Call call, Response response, Exception e) {
                    }
                });
    }

    private void judgeToSkip() {
        startActivity(new Intent(ModifyUserInfoActivity.this, MainActivity.class).putExtra("is_modify_success", true).putExtra(ApiParamsKey.IS_TOURIST, false));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相册选择
        String url;
        if (requestCode == Constant.PERSONAGE_REQUEST_CODE && resultCode == PhotoPickerActivity.PHONT_PICKER_RESULT) {
            Bundle bundle = data.getExtras();
            url = bundle.getString(PhotoPickerActivity.URL);
            Log.i(TAG, "onActivityResult: " + url);
            uploadFile(url);
        }
        //拍照
        if (requestCode == Constant.REQUEST_TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            url = CameraUtil.getInstance().getPhotoPath();
            if (!TextUtils.isEmpty(url)) {
                uploadFile(url);
            }
        }
    }

    //上传图片
    private void uploadFile(final String url) {
        List<File> listPath = new ArrayList();
        listPath.add(new File(url));
        UploadUtil.getInstance()
                .setShowDialog(false).UploadFiles(new UploadUtil.OnUploadSucceedListen() {
            @Override
            public void succeed(List<String> list) {
                if (list.size() == 1) {
                    logoUrl = list.get(0);
                    GlideUitl.getInstance().load(ivUserAvatar, url);
                }
            }
        }, listPath);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void clickPhoto(View view) {
        new SelectPopwindow(new String[]{"拍照", "从手机相册中选择"}, new SelectAdapter.OnClick() {
            @Override
            public void click(int position, String txt) {
                if (position == 1) {
                    Intent intent = new Intent(ModifyUserInfoActivity.this, PhotoPickerActivity.class);
                    intent.putExtra(PhotoPickerActivity.TAG, "tag");
                    startActivityForResult(intent, Constant.PERSONAGE_REQUEST_CODE);
                } else {
                    CameraUtil.getInstance().takePhotoByMethod(Constant.REQUEST_TAKE_PHOTO_CODE);
                }
            }
        }).showBottom(view);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ModifyUserInfoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void showRationaleForCamera(final PermissionRequest request) {
        showRationaleDialog("使用此功能需要打开相机和存储权限", request);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void onCameraDenied() {
        new AppSettingsDialog.Builder(this, "当前App需要申请相机和存储权限,需要打开设置页面么?")
                .setTitle("权限申请")
                .setPositiveButton("确认")
                .setNegativeButton("取消", null)
                .setRequestCode(Constant.REQUEST_CAMERA_PERM)
                .build()
                .show();
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void onCameraNeverAskAgain() {
        new AppSettingsDialog.Builder(this, "当前App需要申请相机及存储权限,需要打开设置页面么?")
                .setTitle("权限申请")
                .setPositiveButton("确认")
                .setNegativeButton("取消", null)
                .setRequestCode(Constant.REQUEST_CAMERA_PERM)
                .build()
                .show();
    }

    private void showRationaleDialog(String messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }
}
