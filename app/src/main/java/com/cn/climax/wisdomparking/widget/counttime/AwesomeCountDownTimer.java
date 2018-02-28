package com.cn.climax.wisdomparking.widget.counttime;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.cn.climax.i_carlib.uiframework.bootstrap.BootstrapButton;
import com.cn.climax.wisdomparking.R;

/**
 * author：leo on 2018/2/28 22:44
 * email： leocheung4ever@gmail.com
 * description: 实现倒计时功能
 * what & why is modified:
 */

public class AwesomeCountDownTimer extends BootstrapButton {

    private final String TAG = AwesomeCountDownTimer.class.getSimpleName();
    private final int STATE_STARTCOUNT = 0;
    private final int STATE_STOPCOUNT = 1;
    private String startCountDownStateColor;
    private String stopCountDownStateColor;
    private String startCountDownText;
    private String stopCountDownText;
    private TimerUtil mTimerUtil;
    private CountDownStateChangeListener mCountDownStateChangeListener;

    public interface CountDownStateChangeListener {
        void onStartCount(long millsUtilFinished);

        void onFinishCount();
    }

    public AwesomeCountDownTimer(Context context) {
        this(context, null);
    }

    public AwesomeCountDownTimer(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public AwesomeCountDownTimer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        changeBackGroundColor(STATE_STOPCOUNT);
    }

    public void onDestroy() {
        Log.d(TAG, "run in onDestroy,currentTimeMillis: " + System.currentTimeMillis());
        if (mTimerUtil != null) {
            mTimerUtil.cancel();
            mTimerUtil = null;
        }
        initState();
    }

    private void initState() {
        changeBackGroundColor(STATE_STOPCOUNT);
        setClickable(true);
        setText(stopCountDownText);
    }

    public void setCountDownStateChangeListener(CountDownStateChangeListener listener) {
        mCountDownStateChangeListener = listener;
    }

    public void setStartCountDownStateColor(String color) {
        startCountDownStateColor = color;
    }

    public void setStopCountDownColor(String color) {
        stopCountDownStateColor = color;
    }

    public void setStartCountDownText(String startCountDownText) {
        this.startCountDownText = startCountDownText;
    }

    private void setStopCountDownText(String stopCountDownText) {
        this.stopCountDownText = stopCountDownText;
    }

    public void startCountDownTimer(final long millisInFuture, final long countDownInterval) {
        if (mTimerUtil != null) {
            mTimerUtil.cancel();
            mTimerUtil = null;
        }
        setStopCountDownText(getText().toString());
        mTimerUtil = new TimerUtil(millisInFuture, countDownInterval);
        mTimerUtil.setCountDownTimerListener(new TimerUtil.CountDownTimerListener() {
            @Override
            public void startCount(long millsUtilFinished) {
                if (mCountDownStateChangeListener != null) {
                    mCountDownStateChangeListener.onStartCount(millsUtilFinished);
                    return;
                }
                changeBackGroundColor(STATE_STARTCOUNT);
                setClickable(false);
                setText(startCountDownText + "(" + millsUtilFinished / 1000 + ")");
            }

            @Override
            public void finishCount() {
                if (mCountDownStateChangeListener != null) {
                    mCountDownStateChangeListener.onFinishCount();
                    return;
                }
                setText(stopCountDownText);
                setClickable(true);
                changeBackGroundColor(STATE_STOPCOUNT);
            }
        });
        mTimerUtil.start();
    }

    private void changeBackGroundColor(int state) {
        try {
            GradientDrawable drawable = (GradientDrawable) getBackground();
            switch (state) {
                case STATE_STARTCOUNT:
                    if (TextUtils.isEmpty(startCountDownStateColor)) {
                        drawable.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    } else {
                        drawable.setColor(Color.parseColor(startCountDownStateColor));
                    }
                    break;
                case STATE_STOPCOUNT:
                    if (TextUtils.isEmpty(stopCountDownStateColor)) {
                        drawable.setColor(ContextCompat.getColor(getContext(), R.color.white));
                    } else {
                        drawable.setColor(Color.parseColor(stopCountDownStateColor));
                    }
                    break;
                default:
                    break;
            }
        } catch (ClassCastException e) {
            e.fillInStackTrace();
        }
    }
}
