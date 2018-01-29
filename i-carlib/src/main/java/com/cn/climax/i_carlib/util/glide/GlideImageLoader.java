package com.cn.climax.i_carlib.util;

import android.content.Context;
import android.widget.ImageView;

import com.app.framework.utils.ScreenUtil;
import com.app.framework.utils.glide.GlideUitl;
import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
//        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        imageView.getLayoutParams().width = ScreenUtil.getScreenWidth(context) * 3 / 4;
//        imageView.getLayoutParams().height = ScreenUtil.getScreenWidth(context) * 3 / 4;
//        imageView.requestLayout();
        Glide.with(context.getApplicationContext())
                .load(path)
                .into(imageView);
    }

//    @Override
//    public ImageView createImageView(Context context) {
//        //圆角
//        return new RoundAngleImageView(context);
//    }
}
