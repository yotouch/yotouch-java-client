package com.yotouch;

public class YotouchException extends RuntimeException {

    private static final long serialVersionUID = -2822404944661837422L;
    
    public static final int ERROR_CODE_UNKNOWN = 9999999;

    private int errCode;

    private String errMsg;
    
    public YotouchException(String errMsg) {
        this(ERROR_CODE_UNKNOWN, errMsg);
    }
    
    public YotouchException(int errCode) {
        this(errCode, "");
    }

    public YotouchException(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    
    public int getErrCode() {
        return this.errCode;
    }
    
    public String getErrMsg() {
        return this.errMsg;
    }
    

}
