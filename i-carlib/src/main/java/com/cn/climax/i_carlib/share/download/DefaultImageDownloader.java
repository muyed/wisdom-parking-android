package com.cn.smart.i_carlib.share.download;

public class DefaultImageDownloader extends AbsImageDownloader {

    @Override
    protected void downloadDirectly(String imageUrl, String filePath, OnImageDownloadListener listener) {
        new DefaultImageDownLoadTask(imageUrl, filePath, listener).start();
    }
}
