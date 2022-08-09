package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
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
import java.util.Set;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Getter
public class CustomerServiceImpl extends ClientService implements CustomerService {

    private int customerId; // logged-in customer

    // AGREED - all DB values comparisons are Case_Insensitive

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\

    @Override
    public boolean login(String email, String password) {
        if (customerRepository.existsByEmailAndPassword(email, password)) {
            this.customerId = customerRepository.getIdFromEmailAndPass(email, password);
        }
        return customerId != 0;
    }

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\


    @Override
    public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
        // not purchased b4 + quantity>0 + date valid
        // quantity--
        // assumed: only coupon's id is used - rest fields assumed to be legit and suits db coupons'!

        int couponId = coupon.getId();
        Coupon dbCpn = couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.COUPON_ID_NOT_FOUND));

        if (couponRepository.isCouponPurchasedByCustomer(customerId, couponId)) {
            throw new CouponSystemException(ErrMsg.COUPON_PURCHASED_BEFORE);
        }
        if (dbCpn.getAmount() <= 0) {
            throw new CouponSystemException(ErrMsg.COUPON_NOT_LEFT);
        }
        if (dbCpn.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new CouponSystemException(ErrMsg.COUPON_EXPIRED);
        }

        Customer dbCst = customerRepository.getById(customerId);
        Set<Coupon> coupons = dbCst.getCoupons();
        coupons.add(dbCpn);
        customerRepository.saveAndFlush(dbCst);
        dbCpn.setAmount(dbCpn.getAmount() - 1);
        couponRepository.saveAndFlush(dbCpn);
        System.out.println("\n . . .  Coupon was purchased" + PrintUtils.SMILE + "\n");
    }

    @Override
    public List<Coupon> getCustomerCoupons() {
        // logged-in customer
        return couponRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Coupon> getCustomerCoupons(Category category) {
        // logged-in customer
        return couponRepository.findByCustomerIdAndCategory(customerId, category.name());
    }

    @Override
    public List<Coupon> getCustomerCoupons(double maxPrice) {
        // logged-in customer
        return couponRepository.findByCustomerIdAndMaxPrice(customerId, maxPrice);
    }

    @Override
    public Customer getCustomerDetails() {
        // logged-in customer
        return customerRepository.getById(customerId);
    }

}
