package com.app.framework.utils.glide;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.app.framework.R;
import com.app.framework.app.BaseApplication;
import com.app.framework.loger.Loger;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

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
        Glide.with(BaseApplication.getInstance()).load(url).asBitmap().centerCrop().error(R.drawable.icon_icon).diskCacheStrategy(DiskCacheStrategy.ALL).into(new BitmapImageViewTarget(imageView) {
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
//        if (!TextUtils.isEmpty(url)) {
//            if (!url.equals("https://p.jzyb2b.com/z_images/")) {
//                Glide.with(BaseApplication.getInstance()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.drawable.icon_icon).into(imageView);
//            }
//        }
        Glide.with(BaseApplication.getInstance()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.drawable.icon_icon).into(imageView);
    }
}
