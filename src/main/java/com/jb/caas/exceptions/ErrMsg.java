package com.jb.caas.exceptions;

/*
 * copyrights @ fadi
 */

import com.jb.caas.utils.PrintUtils;
import lombok.Getter;

@Getter
public enum ErrMsg {

    LOGIN_FAILED("Login FAILED!"),

    COMPANY_ALREADY_EXISTS_NAME("A Company with the same NAME already exists!"),
    COMPANY_ALREADY_EXISTS_EMAIL("A Company with the same EMAIL already exists!"),
    COMPANY_ID_NOT_FOUND("No Company with the given ID was found!"),
    COMPANY_NO_UPDATE_NAME("Updating Company's NAME is Not allowed!"),

    CUSTOMER_ALREADY_EXISTS_EMAIL("A Customer with the same EMAIL already exists!"),
    CUSTOMER_ID_NOT_FOUND("No Customer with the given ID was found!"),

    COUPON_ALREADY_EXISTS_TITLE("A Coupon of your Company with the same TITLE already exists!"),
    COUPON_ID_NOT_FOUND("No Coupon with the given ID was found!"),
    COUPON_ID_NOT_FOUND_OF_COMPANY("No Coupon of your Company with the given ID was found!"),
    COUPON_ID_NOT_FOUND_OF_CUSTOMER("No purchased Coupon with the given ID was found!"),
    COUPON_PURCHASED_BEFORE("You've PURCHASED this Coupon before (cannot be purchased again)!"),
    COUPON_NOT_LEFT("There are no units LEFT of this Coupon to purchase!"),
    COUPON_EXPIRED("Coupon is EXPIRED!"),
    COUPON_INVALID_AMOUNT("Illegal (negative) value of Coupon's AMOUNT!\n         [ amount can be 0, and can be updated later (positive amount) to enable purchasing ]"),
    COUPON_INVALID_DATE("Invalid Coupon's DATEs!");

    private final String msg;

    ErrMsg(String msg) {

        String s1 = PrintUtils.ANSI_RED_BACKGROUND + " " + PrintUtils.ANSI_RESET;
        this.msg = " > > >  The operation was NOT carried out  < < <  " + s1 + "  " + msg + "\n";
    }
}