package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.CouponSystemException;

import java.util.List;

public interface CustomerService {

    Coupon purchaseCoupon(int customerId, Coupon coupon) throws CouponSystemException, CouponSecurityException;

    List<Coupon> getCustomerCoupons(int customerId);

    List<Coupon> getCustomerCoupons(int customerId, Category category);

    List<Coupon> getCustomerCoupons(int customerId, double maxPrice);

    Customer getCustomerDetails(int customerId) throws CouponSystemException;

}
