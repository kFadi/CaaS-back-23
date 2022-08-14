package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.CouponSystemException;
import com.jb.caas.exceptions.ErrMsg;
import com.jb.caas.exceptions.SecMsg;
import lombok.Getter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Getter
public class CompanyServiceImpl extends ClientService implements CompanyService {

    // AGREED - all string values comparisons are Case_Insensitive

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\

    @Override
    public boolean login(String email, String password) {

        return companyRepository.existsByEmailAndPassword(email, password);
    }

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\


    @Override
    public Coupon addCoupon(int companyId, Coupon coupon) throws CouponSystemException, CouponSecurityException {
        // no duplicates title (for coupons of the same company)
        // agreed: ignore value of coupon's id / company
        // added: startDate <= endDate >= now  (( "=,=" one day coupon today ))
        // amount>=0  (( "=" for adding it and updating it later, sort of adding it "locked" ))

        coupon.setId(0);

        // just in case
        coupon.setCompany(companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.COMPANY_ID_NOT_FOUND)));

        if (couponRepository.existsByCompanyIdAndTitle(companyId, coupon.getTitle())) {
            throw new CouponSecurityException(SecMsg.COUPON_ALREADY_EXISTS_TITLE);
        }
        if (coupon.getEndDate().before(coupon.getStartDate())) {
            throw new CouponSystemException(ErrMsg.COUPON_INVALID_DATE);
        }
        if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new CouponSystemException(ErrMsg.COUPON_EXPIRED);
        }
        if (coupon.getAmount() < 0) {
            throw new CouponSystemException(ErrMsg.COUPON_INVALID_AMOUNT);
        }

        couponRepository.save(coupon);

        return coupon;
    }

    @Override
    public Coupon updateCoupon(int companyId, int couponId, Coupon coupon) throws CouponSystemException, CouponSecurityException {
        // no update (ignore) coupon's id / company
        // no duplicates title (concluded from addCoupon(..))
        // added: startDate <= endDate >= now  (( "=,=" one day coupon today ))
        // amount>=0  (( "=" for allowing using this method to update last purchase (amount--),
        //                   for continuing updating it later, sort of moving it to "locked" state ))

        coupon.setId(couponId);

        // just in case
        coupon.setCompany(companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.COMPANY_ID_NOT_FOUND)));

        if (!couponRepository.existsByIdAndCompanyId(couponId, companyId)) {
            throw new CouponSystemException(ErrMsg.COUPON_ID_NOT_FOUND_OF_COMPANY);
        }

        // just in case
        Coupon dbCpn = couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.COUPON_ID_NOT_FOUND));

        String title = coupon.getTitle();

        if (!dbCpn.getTitle().equalsIgnoreCase(title) && couponRepository.existsByCompanyIdAndTitle(companyId, title)) {
            throw new CouponSecurityException(SecMsg.COUPON_ALREADY_EXISTS_TITLE);
        }
        if (coupon.getEndDate().before(coupon.getStartDate())) {
            throw new CouponSystemException(ErrMsg.COUPON_INVALID_DATE);
        }
        if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new CouponSystemException(ErrMsg.COUPON_EXPIRED);
        }
        if (coupon.getAmount() < 0) {
            throw new CouponSystemException(ErrMsg.COUPON_INVALID_AMOUNT);
        }

        couponRepository.saveAndFlush(coupon);

        return coupon;
    }

    @Override
    public void deleteCoupon(int companyId, int couponId) throws CouponSystemException {
        //delete customers' purchase history

        if (!couponRepository.existsByIdAndCompanyId(couponId, companyId)) {
            throw new CouponSystemException(ErrMsg.COUPON_ID_NOT_FOUND_OF_COMPANY);
        }

        couponRepository.deleteById(couponId);
        couponRepository.updatePurchasesJoin();
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId) {

        return couponRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId, Category category) {

        return couponRepository.findByCompanyIdAndCategory(companyId, category);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId, double maxPrice) {

        return couponRepository.findByCompanyIdAndMaxPrice(companyId, maxPrice);
    }

    @Override
    public Company getCompanyDetails(int companyId) throws CouponSystemException {

        // just in case
        return companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.COMPANY_ID_NOT_FOUND));
    }

}
