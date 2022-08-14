package com.jb.caas.exceptions;

/*
 * copyrights @ fadi
 */

import lombok.Data;

@Data
public class CouponSecurityException extends Exception {

    private SecMsg secMsg;

    public CouponSecurityException(SecMsg secMsg) {
        super(secMsg.getMsg());
        this.secMsg = secMsg;
    }
}