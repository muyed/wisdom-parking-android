package com.cn.climax.i_carlib.platform.share_media;

/**
 * 文字分享 实体类
 */
public class ShareTextMedia implements IShareMedia {
    private String mText;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
