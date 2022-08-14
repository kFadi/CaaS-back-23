package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.CouponSystemException;

import java.util.List;

public interface CompanyService {

    void addCoupon(int companyId, Coupon coupon) throws CouponSystemException, CouponSecurityException;

    void updateCoupon(int companyId, int couponId, Coupon coupon) throws CouponSystemException, CouponSecurityException;

    void deleteCoupon(int companyId, int couponId) throws CouponSystemException;

    List<Coupon> getCompanyCoupons(int companyId);

    List<Coupon> getCompanyCoupons(int companyId, Category category);

    List<Coupon> getCompanyCoupons(int companyId, double maxPrice);

    Company getCompanyDetails(int companyId) throws CouponSystemException;
}
