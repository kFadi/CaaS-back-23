package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
import com.jb.caas.exceptions.CouponSystemException;

import java.util.List;

public interface CustomerService {

    void purchaseCoupon(Coupon coupon) throws CouponSystemException;

    List<Coupon> getCustomerCoupons();

    List<Coupon> getCustomerCoupons(Category category);

    List<Coupon> getCustomerCoupons(double maxPrice);

    Customer getCustomerDetails();

}
