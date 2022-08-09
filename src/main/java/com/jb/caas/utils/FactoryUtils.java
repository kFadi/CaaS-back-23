package com.jb.caas.utils;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;

import java.sql.Date;
import java.time.LocalDate;

public class FactoryUtils {

    private static final int CPN_MAX_DAYS = 8;
    private static final int CPN_AMOUNT = 3;
    private static final int CPN_MAX_BASE_PRICE = 50;

    private static int idxComp = 0;
    private static int idxCust = 0;
    private static int idxCpn = 0;

    public static Company initCompany(boolean indexed) {
        if (indexed) {
            idxComp++;
        }
        return Company.builder()
                .name(indexed ? "CompanyName_" + idxComp : "someName")
                .email(indexed ? "CompanyEmail_" + idxComp + "_@site.com" : "someEmail")
                .password(indexed ? "CompanyPass_" + idxComp : "somePass")
                .build();
    }

    public static Customer initCustomer(boolean indexed) {
        if (indexed) {
            idxCust++;
        }
        return Customer.builder()
                .firstName(indexed ? "CustomerFirstName_" + idxCust : "someFirstName")
                .lastName(indexed ? "CustomerLastName_" + idxCust : "someLastName")
                .email(indexed ? "CustomerEmail_" + idxCust + "_@site.com" : "someEmail")
                .password(indexed ? "CustomerPass_" + idxCust : "somePass")
                .build();
    }

    public static Coupon initCoupon(boolean indexed) {
        if (indexed) {
            idxCpn++;
        }
        Date now = Date.valueOf(LocalDate.now());
        return Coupon.builder()
                .category(Category.values()[rnd(Category.values().length)])
                .title(indexed ? "CouponTitle_" + idxCpn : "someTitle")
                .description(indexed ? "CouponDescription_" + idxCpn : "someDescription")
                .startDate(now)
                .endDate(indexed ? Date.valueOf(LocalDate.now().plusDays(rnd(CPN_MAX_DAYS))) : now)
                .amount(indexed ? CPN_AMOUNT : 1)
                .price(indexed ? (rnd(CPN_MAX_BASE_PRICE) + rnd(10) / 10.0) : 1.1)
                .image(indexed ? "CouponImage_" + idxCpn : "someImage")
                .build();
    }

    private static int rnd(int limit) {
        return (int) (Math.random() * limit);
    }
}