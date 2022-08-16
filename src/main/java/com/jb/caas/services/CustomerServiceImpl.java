package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
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
import java.util.Set;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Getter
public class CustomerServiceImpl extends ClientService implements CustomerService {

    // AGREED - all string values comparisons are Case_Insensitive

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\

    @Override
    public boolean login(String email, String password) {

        return customerRepository.existsByEmailAndPassword(email, password);
    }

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\

    @Override
    public Coupon purchaseCoupon(int customerId, Coupon coupon) throws CouponSystemException, CouponSecurityException {
        // not purchased b4 + quantity>0 + date valid
        // quantity--
        // assumed: only coupon's id is used - rest fields assumed to be legit and suits db coupons'!

        int couponId = coupon.getId();
        Coupon dbCpn = couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.COUPON_ID_NOT_FOUND));

        if (couponRepository.isCouponPurchasedByCustomer(customerId, couponId)) {
            throw new CouponSecurityException(SecMsg.CUSTOMER_COUPON_PURCHASED_BEFORE);
        }
        if (dbCpn.getAmount() <= 0) {
            throw new CouponSystemException(ErrMsg.COUPON_NOT_LEFT);
        }
        if (dbCpn.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new CouponSystemException(ErrMsg.COUPON_EXPIRED);
        }

        // just in case
        Customer dbCst = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.CUSTOMER_ID_NOT_FOUND));

        Set<Coupon> coupons = dbCst.getCoupons();
        coupons.add(dbCpn);
        customerRepository.saveAndFlush(dbCst);
        dbCpn.setAmount(dbCpn.getAmount() - 1);
        couponRepository.saveAndFlush(dbCpn);

        return dbCpn;
    }

    @Override
    public List<Coupon> getBrowseCoupons() {

        return couponRepository.findAll();
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId) {

        return couponRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId, Category category) {

        return couponRepository.findByCustomerIdAndCategory(customerId, category.name());
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId, double maxPrice) {

        return couponRepository.findByCustomerIdAndMaxPrice(customerId, maxPrice);
    }

    @Override
    public Customer getCustomerDetails(int customerId) throws CouponSystemException {

        // just in case
        return customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.CUSTOMER_ID_NOT_FOUND));
    }

}
