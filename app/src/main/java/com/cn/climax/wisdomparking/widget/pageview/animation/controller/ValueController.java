package com.cn.climax.wisdomparking.widget.pageview.animation.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cn.climax.wisdomparking.widget.pageview.animation.data.Value;
import com.cn.climax.wisdomparking.widget.pageview.animation.type.ColorAnimation;
import com.cn.climax.wisdomparking.widget.pageview.animation.type.DropAnimation;
import com.cn.climax.wisdomparking.widget.pageview.animation.type.FillAnimation;
import com.cn.climax.wisdomparking.widget.pageview.animation.type.ScaleAnimation;
import com.cn.climax.wisdomparking.widget.pageview.animation.type.ScaleDownAnimation;
import com.cn.climax.wisdomparking.widget.pageview.animation.type.SlideAnimation;
import com.cn.climax.wisdomparking.widget.pageview.animation.type.SwapAnimation;
import com.cn.climax.wisdomparking.widget.pageview.animation.type.ThinWormAnimation;
import com.cn.climax.wisdomparking.widget.pageview.animation.type.WormAnimation;

public class ValueController {

    private ColorAnimation colorAnimation;
    private ScaleAnimation scaleAnimation;
    private WormAnimation wormAnimation;
    private SlideAnimation slideAnimation;
    private FillAnimation fillAnimation;
    private ThinWormAnimation thinWormAnimation;
    private DropAnimation dropAnimation;
    private SwapAnimation swapAnimation;
    private ScaleDownAnimation scaleDownAnimation;

    private UpdateListener updateListener;

    public interface UpdateListener {
        void onValueUpdated(@Nullable Value value);
    }

    public ValueController(@Nullable UpdateListener listener) {
        updateListener = listener;
    }

    @NonNull
    public ColorAnimation color() {
        if (colorAnimation == null) {
            colorAnimation = new ColorAnimation(updateListener);
        }

        return colorAnimation;
    }

    @NonNull
    public ScaleAnimation scale() {
        if (scaleAnimation == null) {
            scaleAnimation = new ScaleAnimation(updateListener);
        }

        return scaleAnimation;
    }

    @NonNull
    public WormAnimation worm() {
        if (wormAnimation == null) {
            wormAnimation = new WormAnimation(updateListener);
        }

        return wormAnimation;
    }

    @NonNull
    public SlideAnimation slide() {
        if (slideAnimation == null) {
            slideAnimation = new SlideAnimation(updateListener);
        }

        return slideAnimation;
    }

    @NonNull
    public FillAnimation fill() {
        if (fillAnimation == null) {
            fillAnimation = new FillAnimation(updateListener);
        }

        return fillAnimation;
    }

    @NonNull
    public ThinWormAnimation thinWorm() {
        if (thinWormAnimation == null) {
            thinWormAnimation = new ThinWormAnimation(updateListener);
        }

        return thinWormAnimation;
    }

    @NonNull
    public DropAnimation drop() {
        if (dropAnimation == null) {
            dropAnimation = new DropAnimation(updateListener);
        }

        return dropAnimation;
    }

    @NonNull
    public SwapAnimation swap() {
        if (swapAnimation == null) {
            swapAnimation = new SwapAnimation(updateListener);
        }

        return swapAnimation;
    }

    @NonNull
    public ScaleDownAnimation scaleDown() {
        if (scaleDownAnimation == null) {
            scaleDownAnimation = new ScaleDownAnimation(updateListener);
        }

        return scaleDownAnimation;
    }
}
