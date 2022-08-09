package com.jb.caas.exceptions;

/*
 * copyrights @ fadi
 */

public class CouponSystemException extends Exception {

    public CouponSystemException(ErrMsg errMsg) {
        super(errMsg.getMsg());
    }
}