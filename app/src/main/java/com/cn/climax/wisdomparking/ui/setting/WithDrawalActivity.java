package com.cn.climax.wisdomparking.ui.setting;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseSwipeBackActivity;
import com.cn.climax.wisdomparking.widget.pay.Keyboard;
import com.cn.climax.wisdomparking.widget.pay.PayEditText;

import java.lang.reflect.Method;

import butterknife.BindView;

public class WithDrawalActivity extends BaseSwipeBackActivity {

    @BindView(R.id.PayEditText_pay)
    PayEditText payEditText;
    @BindView(R.id.KeyboardView_pay)
    Keyboard keyboard;
    @BindView(R.id.etInputWithDrawalAmount)
    EditText etInputWithDrawalAmount;
    @BindView(R.id.btnBindCardConfirm)
    Button btnBindCardConfirm; //确认提现

    private static final String[] KEY = new String[]{
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "", "0", "<<"
    };

    @Override
    protected int initContentView() {
        return R.layout.activity_with_drawal;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
        hideSystemSofeKeyboard(WithDrawalActivity.this, etInputWithDrawalAmount);
        initView();
        setSubView();
        initEvent();
    }

    private void initEvent() {
        keyboard.setOnClickKeyboardListener(new Keyboard.OnClickKeyboardListener() {
            @Override
            public void onKeyClick(int position, String value) {
                if (position < 11 && position != 9) {
                    payEditText.add(value);
                } else if (position == 9) {
                    payEditText.remove();
                } else if (position == 11) {
                    //当点击完成的时候，也可以通过payEditText.getText()获取密码，此时不应该注册OnInputFinishedListener接口
//                    Toast.makeText(getApplication(), "您的密码是：" + payEditText.getText(), Toast.LENGTH_SHORT).show();
//                    finish();
                }
            }
        });

        /**
         * 当密码输入完成时的回调
         */
        payEditText.setOnInputFinishedListener(new PayEditText.OnInputFinishedListener() {
            @Override
            public void onInputFinished(String password) {
                Toast.makeText(getApplication(), "您的密码是：" + password, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSubView() {
        //设置键盘
        keyboard.setKeyboardKeys(KEY);
    }

    private void initView() {
        btnBindCardConfirm.setOnClickListener(new CommonClick());
    }

    private class CommonClick extends ForbidQuickClickListener {

        @Override
        protected void forbidClick(View view) {
            switch (view.getId()) {
                case R.id.btnBindCardConfirm:
                    toastPayDialog();
                    break;
            }
        }
    }

    private void toastPayDialog() {

    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public static void hideSystemSofeKeyboard(Context context, EditText editText) {
        Log.i(">>>>>", "hide");
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
        // 如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
