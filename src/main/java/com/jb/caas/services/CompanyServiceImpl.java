package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.exceptions.CouponSystemException;
import com.jb.caas.exceptions.ErrMsg;
import com.jb.caas.utils.PrintUtils;
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

    private int companyId; // logged-in company

    // AGREED - all DB values comparisons are Case_Insensitive

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\

    @Override
    public boolean login(String email, String password) {
        if (companyRepository.existsByEmailAndPassword(email, password)) {
            this.companyId = companyRepository.getIdFromEmailAndPass(email, password);
        }
        return companyId != 0;
    }

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\


    @Override
    public void addCoupon(Coupon coupon) throws CouponSystemException {
        // no duplicates title (for coupons of the same company)
        // agreed: ignore value of coupon's id / company
        // added: startDate <= endDate >= now  (( "=,=" one day coupon today ))
        // amount>=0  (( "=" for adding it and updating it later, sort of adding it "locked" ))

        coupon.setId(0);
        coupon.setCompany(companyRepository.getById(companyId));

        if (couponRepository.existsByCompanyIdAndTitle(companyId, coupon.getTitle())) {
            throw new CouponSystemException(ErrMsg.COUPON_ALREADY_EXISTS_TITLE);
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
        System.out.println("\n . . .  Coupon was added" + PrintUtils.SMILE + "\n");
    }

    @Override
    public void updateCoupon(int couponId, Coupon coupon) throws CouponSystemException {
        // no update (ignore) coupon's id / company
        // no duplicates title (concluded from addCoupon(..))
        // added: startDate <= endDate >= now  (( "=,=" one day coupon today ))
        // amount>=0  (( "=" for allowing using this method to update last purchase (amount--),
        //                   for continuing updating it later, sort of moving it to "locked" state ))

        coupon.setId(couponId);
        coupon.setCompany(companyRepository.getById(companyId));

        if (!couponRepository.existsByIdAndCompanyId(couponId, companyId)) {
            throw new CouponSystemException(ErrMsg.COUPON_ID_NOT_FOUND_OF_COMPANY);
        }

        Coupon dbCpn = couponRepository.getById(couponId);
        String title = coupon.getTitle();

        if (!dbCpn.getTitle().equals(title) && couponRepository.existsByCompanyIdAndTitle(companyId, title)) {
            throw new CouponSystemException(ErrMsg.COUPON_ALREADY_EXISTS_TITLE);
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
        System.out.println("\n . . .  Coupon was updated" + PrintUtils.SMILE + "\n");
    }

    @Override
    public void deleteCoupon(int couponId) throws CouponSystemException {
        //delete customers' purchase history

        if (!couponRepository.existsByIdAndCompanyId(couponId, companyId)) {
            throw new CouponSystemException(ErrMsg.COUPON_ID_NOT_FOUND_OF_COMPANY);
        }
        couponRepository.deleteById(couponId);
        couponRepository.updatePurchasesJoin();
        System.out.println("\n . . .  Coupon was deleted (with all its purchases if existed!)" + PrintUtils.SMILE + "\n");
    }

    @Override
    public List<Coupon> getCompanyCoupons() {
        // logged-in company
        return couponRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Coupon> getCompanyCoupons(Category category) {
        // logged-in company
        return couponRepository.findByCompanyIdAndCategory(companyId, category);
    }

    @Override
    public List<Coupon> getCompanyCoupons(double maxPrice) {
        // logged-in company
        return couponRepository.findByCompanyIdAndMaxPrice(companyId, maxPrice);
    }

    @Override
    public Company getCompanyDetails() {
        // logged-in company
        return companyRepository.getById(companyId);
    }

}
