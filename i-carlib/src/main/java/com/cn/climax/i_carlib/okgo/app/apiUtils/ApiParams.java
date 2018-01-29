package cn.hs.com.wovencloud.data.apiUtils;

import com.app.framework.utils.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import cn.hs.com.wovencloud.Core;
import cn.hs.com.wovencloud.R;
import cn.hs.com.wovencloud.util.ContextHolderUtil;
import cn.hs.com.wovencloud.util.TT;
import okhttp3.OkHttpClient;

/**
 * 接口中转处理类
 */
public class ApiParams {

    private static ApiParams instance;

    private ApiParams() {

    }

    public static ApiParams getInstance() {
        if (instance == null) {
            instance = new ApiParams();
        }
        return instance;
    }

    private String getSSLPwd() {
        String id = SharedUtil.getInstance(Core.getInstances().getCurrentActivity()).get(ApiParamsKey.PASSORT_ID, ApiParamsKey.PASSORT_ID_DEFAULT);
        int passort_id = Integer.parseInt(id);
        String keyPassword;
        if (passort_id <= 1000) {
            keyPassword = "q1w2e3r4";
        } else if (passort_id >= 5000) {
            keyPassword = "Xhjzy5008app";
        } else {
            keyPassword = "Xhjzy5008app";
        }
        return keyPassword;
    }

    private int getSSLCer() {
        String id = SharedUtil.getInstance(ContextHolderUtil.getContext()).get(ApiParamsKey.PASSORT_ID, ApiParamsKey.PASSORT_ID_DEFAULT);
        int passort_id = Integer.parseInt(id);
        int certify;
        if (passort_id <= 1000) {
            certify = R.raw.jzy20171212;
        } else if (passort_id >= 5000) {
            certify = R.raw.jzy_and20170109;
        } else {
            certify = R.raw.jzy_and20170109;
        }
        return certify;
    }

    private String getBKSCer() {
        String id = SharedUtil.getInstance(ContextHolderUtil.getContext()).get(ApiParamsKey.PASSORT_ID, ApiParamsKey.PASSORT_ID_DEFAULT);
        int passort_id = Integer.parseInt(id);
        String certify;
        if (passort_id <= 1000) {
            certify = "client1.bks";
        } else if (passort_id >= 5000) {
            certify = "jzyadmin.bks";
        } else {
            certify = "jzyadmin.bks";
        }
        return certify;
    }

    /**
     * 设置服务器请求接口的公共字段
     */
    public void setOkGoHeader() {

        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        //headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
        //  headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        // params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        //params.put("commonParamsKey2", "这里支持中文参数");
        //-----------------------------------------------------------------------------------//

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            BufferedInputStream caInput = new BufferedInputStream(Core.getInstances().getContext().getResources().openRawResource(getSSLCer()));
            InputStream caBKSInput = Core.getInstances().getApplicationContext().getAssets().open(getBKSCer());
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)

                    //如果使用默认的 60秒,以下三行也不需要传
                    // .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setConnectTimeout(30000)
                    //  .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    //.setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
                    .setReadTimeOut(30000)     //全局的读取超时时间
                    .setWriteTimeOut(30000)
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(0)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
//                .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
//                    .setCertificates()                                  //方法一：信任所有证书,不安全有风险
//                    .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//                    .setCertificates(caInput)      //方法三：使用预埋证书，校验服务端证书（自签名证书）
                    .setCertificates(caBKSInput, getSSLPwd(), caInput)// //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）

                    //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//                    .setHostnameVerifier(new SafeHostnameVerifier())

                    //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })
                    //  .addInterceptor(new KLogHttpInterceptor("JSON"))
                    //这两行同上，不需要就不要加入
                    .addCommonHeaders(headers)  //设置全局公共头
                    .addCommonParams(params);   //设置全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
