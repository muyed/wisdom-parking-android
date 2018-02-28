package com.cn.climax.wisdomparking.widget.counttime;


/**
 * author：leo on 2017/9/14 0014 15:52
 * email： leocheung4ever@gmail.com
 * description: 复写计时器
 * what & why is modified:
 */
public class TimerUtil extends CountDownTimer {

    private CountDownTimerListener mCountDownTimerListener;

    public TimerUtil(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mCountDownTimerListener != null) {
            mCountDownTimerListener.startCount(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if (mCountDownTimerListener != null) {
            mCountDownTimerListener.finishCount();
        }
    }

    public void setCountDownTimerListener(CountDownTimerListener listener) {
        mCountDownTimerListener = listener;
    }

    public interface CountDownTimerListener {
        void startCount(long millsUtilFinished);

        void finishCount();
    }
}
