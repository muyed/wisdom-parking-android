
package com.cn.smart.i_carlib.share.core.error;

public class UnSupportedException extends ShareException {

    public UnSupportedException(String detailMessage) {
        super(detailMessage);
        setCode(CarSmartShareStatusCode.ST_CODE_SHARE_ERROR_PARAM_UNSUPPORTED);
    }

}
