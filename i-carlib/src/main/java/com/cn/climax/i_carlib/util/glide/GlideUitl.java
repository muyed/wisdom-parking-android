package com.cn.climax.i_carlib.util.glide;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.cn.climax.i_carlib.R;
import com.cn.climax.i_carlib.okgo.app.BaseApplication;

/**
 * author:xiongx 2017/9/15.
 */

public class GlideUitl {
    public static GlideUitl glideUitl;

    public static GlideUitl getInstance() {
        if (glideUitl == null) {
            glideUitl = new GlideUitl();
        }
        return glideUitl;
    }
    
    //加载圆形图片
    public void loadCenterCrop(final ImageView imageView, String url) {
        Glide.with(BaseApplication.getInstance()).load(url).asBitmap().centerCrop().error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(BaseApplication.getInstance().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    //普通加载
    public void load(ImageView imageView, String url) {
        Glide.with(BaseApplication.getInstance()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.ic_launcher).into(imageView);
    }
}
