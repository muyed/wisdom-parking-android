package com.cn.climax.i_carlib.uiframework.banner;

import android.annotation.TargetApi;
import android.os.Build;

import com.cn.climax.i_carlib.uiframework.banner.transformer.ABaseTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.AccordionTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.BackgroundToForegroundTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.CubeInTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.CubeOutTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.DefaultTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.DepthPageTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.FlipHorizontalTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.FlipVerticalTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.ForegroundToBackgroundTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.RotateDownTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.RotateUpTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.StackTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.ZoomInTransformer;
import com.cn.climax.i_carlib.uiframework.banner.transformer.ZoomOutTranformer;

/**
 * author: leo on 2016/6/23 0023/13:37
 * email : leocheung4ever@gmail.com
 * description: 自定义翻页旋转特效
 * what & why is modified:
 */
public class CustomPageTransformer {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static ABaseTransformer setPageTransFormer(int type, XBanner xBanner) {
        //通过映射
        ABaseTransformer transformer = null;
        try {
            Class cls = Class.forName("com.cn.smart.i_carlib.uiframework.banner.transformer." + transformerNames[type]);
            transformer = (ABaseTransformer) cls.newInstance();

            //部分3D特效需要调整滑动速度
            if (transformerNames[type].equals("StackTransformer")) {
                xBanner.setScrollDuration(1200);
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return transformer;
    }

    public static final String[] transformerNames = {
            DefaultTransformer.class.getSimpleName(),
            AccordionTransformer.class.getSimpleName(),
            BackgroundToForegroundTransformer.class.getSimpleName(),
            ForegroundToBackgroundTransformer.class.getSimpleName(),
            CubeInTransformer.class.getSimpleName(),
            CubeOutTransformer.class.getSimpleName(),
            DepthPageTransformer.class.getSimpleName(),
            FlipHorizontalTransformer.class.getSimpleName(),
            FlipVerticalTransformer.class.getSimpleName(),
            RotateDownTransformer.class.getSimpleName(),
            RotateUpTransformer.class.getSimpleName(),
            StackTransformer.class.getSimpleName(),
            ZoomInTransformer.class.getSimpleName(),
            ZoomOutTranformer.class.getSimpleName(),
    };
}
