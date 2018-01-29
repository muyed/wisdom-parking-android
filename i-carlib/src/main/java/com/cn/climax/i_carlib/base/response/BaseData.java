package cn.hs.com.wovencloud.data.remote.response;

import com.app.framework.data.AbsJavaBean;


/**
 * author：leo on 2017/9/15 0015 07:49
 * email： leocheung4ever@gmail.com
 * description: 响应数据基类
 * what & why is modified:
 */
public class BaseData<T> extends AbsJavaBean {

    private T returnData;
    private int returnState;

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }

    public int getReturnState() {
        return returnState;
    }

    public void setReturnState(int returnState) {
        this.returnState = returnState;
    }
}
