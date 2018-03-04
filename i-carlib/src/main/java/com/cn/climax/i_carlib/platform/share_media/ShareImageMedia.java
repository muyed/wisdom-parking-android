package com.cn.climax.i_carlib.platform.share_media;

import android.graphics.Bitmap;

/**
 * 图片分享 实体类
 */
public class ShareImageMedia implements IShareMedia {
    private Bitmap mImage;

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }
}
