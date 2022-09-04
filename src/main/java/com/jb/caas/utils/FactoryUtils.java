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
    private static final int CPN_AMOUNT = 2;
    private static final int CPN_MAX_BASE_PRICE = 50;

    private static int idxComp = 0;
    private static int idxCust = 0;
    private static int idxCpn = 0;

    public static Company initCompany() {

        idxComp++;
        return Company.builder()
                .name("comp" + idxComp)
                .email("comp" + idxComp + "@c.c")
                .password("1111")
                .build();
    }

    public static Customer initCustomer() {

        idxCust++;
        return Customer.builder()
                .firstName("first" + idxCust)
                .lastName("last" + idxCust)
                .email("cust" + idxCust + "@c.c")
                .password("1111")
                .build();
    }

    public static Coupon initCoupon() {

        idxCpn++;
        return Coupon.builder()
                .category(Category.values()[rnd(Category.values().length)])
                .title("title_" + idxCpn)
                .description("Lorem Ipsum is simply dummy text of the print")
                .startDate(Date.valueOf(LocalDate.now().plusDays(1)))
                .endDate(Date.valueOf(LocalDate.now().plusDays(1 + rnd(CPN_MAX_DAYS))))
                .amount(CPN_AMOUNT)
                .price(rnd(CPN_MAX_BASE_PRICE) + rnd(10) / 10.0)
                .image("img_" + idxCpn)
                .build();
    }

    private static int rnd(int limit) {
        return (int) (Math.random() * limit);
    }
}