package com.cn.climax.wisdomparking.widget.pageview;

import android.support.annotation.Nullable;

import com.cn.climax.wisdomparking.widget.pageview.animation.AnimationManager;
import com.cn.climax.wisdomparking.widget.pageview.animation.controller.ValueController;
import com.cn.climax.wisdomparking.widget.pageview.animation.data.Value;
import com.cn.climax.wisdomparking.widget.pageview.draw.DrawManager;
import com.cn.climax.wisdomparking.widget.pageview.draw.data.Indicator;

public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    interface Listener {
        void onIndicatorUpdated();
    }

    IndicatorManager(@Nullable Listener listener) {
        this.listener = listener;
        this.drawManager = new DrawManager();
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public Indicator indicator() {
        return drawManager.indicator();
    }

    public DrawManager drawer() {
        return drawManager;
    }

    @Override
    public void onValueUpdated(@Nullable Value value) {
        drawManager.updateValue(value);
        if (listener != null) {
            listener.onIndicatorUpdated();
        }
    }
}
