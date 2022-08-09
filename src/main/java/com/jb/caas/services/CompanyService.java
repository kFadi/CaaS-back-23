package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.exceptions.CouponSystemException;

import java.util.List;

public interface CompanyService {

    void addCoupon(Coupon coupon) throws CouponSystemException;

    void updateCoupon(int couponId, Coupon coupon) throws CouponSystemException;

    void deleteCoupon(int couponId) throws CouponSystemException;

    List<Coupon> getCompanyCoupons();

    List<Coupon> getCompanyCoupons(Category category);

    List<Coupon> getCompanyCoupons(double maxPrice);

    Company getCompanyDetails();

}
