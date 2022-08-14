package com.jb.caas.exceptions;

/*
 * copyrights @ fadi
 */

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SecMsg {

    //--------------------------------------------------------------------------------------
    INVALID_CREDENTIALS("Incorrect Type or Email or Password!", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("Invalid Token! Please re-login..", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_OPERATION("Unauthorized Operation!", HttpStatus.UNAUTHORIZED),
    //--------------------------------------------------------------------------------------
    COMPANY_ALREADY_EXISTS_EMAIL("A Company with the same EMAIL already exists!", HttpStatus.CONFLICT),
    COMPANY_ALREADY_EXISTS_NAME("A Company with the same NAME already exists!", HttpStatus.CONFLICT),
    //-------------------------------
    CUSTOMER_ALREADY_EXISTS_EMAIL("A Customer with the same EMAIL already exists!", HttpStatus.CONFLICT),
    CUSTOMER_COUPON_PURCHASED_BEFORE("Coupon has been PURCHASED before!", HttpStatus.CONFLICT),
    //-------------------------------
    COUPON_ALREADY_EXISTS_TITLE("A Coupon of the Company with the same TITLE already exists!", HttpStatus.CONFLICT);
    //--------------------------------------------------------------------------------------


    private final String msg;
    private final HttpStatus status;

    SecMsg(String msg, HttpStatus status) {
        this.msg = msg;
        this.status = status;
    }
}