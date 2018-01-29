package cn.hs.com.wovencloud.data.apiUtils;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

import com.app.framework.data.callback.StringDialogCallback;
import com.app.framework.loger.Loger;
import com.app.framework.utils.DipToPx;
import com.app.framework.utils.SharedUtil;
import com.app.framework.utils.toast.ToastUtils;
import com.lzy.okgo.OkGo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.hs.com.wovencloud.Core;
import cn.hs.com.wovencloud.base.global.Constant;
import cn.hs.com.wovencloud.util.BitmapUtil;
import cn.hs.com.wovencloud.util.ContextHolderUtil;
import okhttp3.Call;
import okhttp3.Response;

/**
 * author:xiongx 2017/9/16.
 */

public class UploadUtil {
    private static final String TAG = "UploadUtil";
    public static final String DCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    //    public static final String DCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    public static final String DIRECTORY = DCIM + "/Camera";
    public final String SAVED_IMAGE_PATH = DIRECTORY;
    //    public final String SAVED_IMAGE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    private ProgressDialog dialog;

    private boolean isShowDialog = true; //是否展示进度条  默认打开
    private String fromServeUrl; //从服务端获取的URL  不进行编辑 不需要压缩
    private List<String> urlList = new ArrayList<>(); //从服务端获取的URL集合

    public interface OnUploadSucceedListen {
        void succeed(List<String> list);
    }

    private UploadUtil() {
    }

    private static UploadUtil mInstance;

    public static UploadUtil getInstance() {
        if (mInstance == null) {
            mInstance = new UploadUtil();
        }
        return mInstance;
    }

    private OnUploadSucceedListen listen;

    private String getApiUrl() {
        String id = SharedUtil.getInstance(ContextHolderUtil.getContext()).get(ApiParamsKey.PASSORT_ID, ApiParamsKey.PASSORT_ID_DEFAULT);
        int passort_id = Integer.parseInt(id);
        String url;
        if (passort_id <= 1000) {
            url = "https://dp.jzyb2b.com/z_images/upload";
        } else if (passort_id >= 5000) {
            url = "https://app.jzyb2b.com/z_images/upload";
        } else {
            url = "https://app.jzyb2b.com/z_images/upload";
        }
        return url;
    }

    private String getApiPreUrl() {
        String id = SharedUtil.getInstance(ContextHolderUtil.getContext()).get(ApiParamsKey.PASSORT_ID, ApiParamsKey.PASSORT_ID_DEFAULT);
        int passort_id = Integer.parseInt(id);
        String url;
        if (passort_id <= 1000) {
            url = "http://www.jzyb2b.com/z_images/";
        } else if (passort_id >= 5000) {
            url = "http://www.jzyb2b.com/z_images/";
        } else {
            url = "http://www.jzyb2b.com/z_images/";
        }
        return url;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    final List<File> list = (List<File>) msg.obj;
                    if (list != null && list.size() > 0) {
                        uploadOption(list);
                    }
                    break;
            }
        }
    };

    private void uploadOption(final List<File> list) {
        OkGo.post(getApiUrl()).addFileParams("userfile", list).execute(new StringDialogCallback(Core.getInstances().getCurrentActivity()) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                ByteArrayInputStream tInputStringStream = null;
                try {
                    tInputStringStream = new ByteArrayInputStream(s.getBytes());
                    BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStringStream));
                    String sTempOneLine;
                    List listMd5 = new ArrayList<>();
                    while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                        if (sTempOneLine.contains("<h1>")) {
                            int start = sTempOneLine.lastIndexOf(" ");
                            String str;
                            str = getApiPreUrl() + sTempOneLine.substring(start + 1, 32 + start + 1);
                            listMd5.add(str);
                        }
                    }
                    deleteFiles(list);

                    if (isShowDialog) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                    if (listen != null)
                        listen.succeed(listMd5);

                    if (isShowDialog)
                        dialog = null;
                    if (listen != null)
                        listen = null;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        assert tInputStringStream != null;
                        tInputStringStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                deleteFiles(list);
                if (isShowDialog) {
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                }
                listen = null;
                ToastUtils.show("文件上传失败");
            }
        });
    }

    public boolean isShowDialog() {
        return isShowDialog;
    }

    public UploadUtil setShowDialog(boolean showDialog) {
        isShowDialog = showDialog;
        return this;
    }

    public int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 1 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public void UploadFiles(final OnUploadSucceedListen onUploadSucceedListen, final List<File> listPath) {
        listen = onUploadSucceedListen;
        if (listPath.size() == 0) {
            listen.succeed(new ArrayList<String>());
            return;
        }
        if (isShowDialog) {
            dialog = new ProgressDialog(Core.getInstance().getCurrentActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("请求网络中...");
            dialog.show();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<File> list = new ArrayList<>();
                for (int i = 0; i < listPath.size(); i++) {
                    Loger.e("debug===> " + listPath.get(i));
//                    if (listPath.get(i).getPath().contains(getApiPreUrl())) {
//                        fromServeUrl = listPath.get(i).getPath();
//                        urlList.add(fromServeUrl);
//                    } else {
                        String newUrl = compressScale(listPath.get(i));
                        list.add(new File(newUrl));
//                    }
                }
                Message ms = new Message();
                ms.obj = list;
                ms.what = 0;
                mHandler.sendMessage(ms);
            }
        }).start();
    }

    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 图片按比例大小压缩方法
     */
    public String compressScale(File file) {
        Bitmap image;
        if (file.exists()) {
            Log.i("compressScale", "compressScale: 存在");
            image = getBitmapFromFile(file, DipToPx.dip2px(300), DipToPx.dip2px(300));
            int orientation = readPictureDegree(file.getPath());
            if (Math.abs(orientation) > 0) {
                image = rotaingImageView(orientation, image);//旋转图片
            }
        } else {
            image = BitmapUtil.getBitmap(file.getPath());
        }

        //临时存储url地址
        String newUrl = DCIM + "/" + System.currentTimeMillis() + ".png";

        FileOutputStream out;
        try {
            out = new FileOutputStream(newUrl);
            if (out != null) {
                image.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            }
            return newUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUrl;
    }

    private Bitmap generateBitmap(String url) {
        return BitmapUtil.decodeUrl(url);
    }

    public Bitmap getBitmapFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);

                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength,
                        width * height);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getAbsolutePath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void deleteFiles(List<File> files) {
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }
}
