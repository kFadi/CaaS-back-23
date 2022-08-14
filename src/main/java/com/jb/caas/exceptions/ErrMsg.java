package com.jb.caas.exceptions;

/*
 * copyrights @ fadi
 */

import lombok.Getter;

@Getter
public enum ErrMsg {

    //--------------------------------------------------------------------------------------
    COMPANY_ID_NOT_FOUND("No Company with the given ID was found!"),
    COMPANY_NO_UPDATE_NAME("Updating Company's NAME is Not allowed!"),
    //--------------------------------------------------------------------------------------
    CUSTOMER_ID_NOT_FOUND("No Customer with the given ID was found!"),
    //--------------------------------------------------------------------------------------
    COUPON_ID_NOT_FOUND("No Coupon with the given ID was found!"),
    COUPON_ID_NOT_FOUND_OF_COMPANY("No Coupon of your Company with the given ID was found!"),
    COUPON_ID_NOT_FOUND_OF_CUSTOMER("No purchased Coupon with the given ID was found!"),
    COUPON_NOT_LEFT("There are no units LEFT of this Coupon to purchase!"),
    COUPON_EXPIRED("Coupon is EXPIRED!"),
    COUPON_INVALID_AMOUNT("Illegal (negative) value of Coupon's AMOUNT!\n         [ amount can be 0, and can be updated later (positive amount) to enable purchasing ]"),
    COUPON_INVALID_DATE("Invalid Coupon's DATEs!");
    //--------------------------------------------------------------------------------------


    private final String msg;

    ErrMsg(String msg) {
        this.msg = msg;
    }
}