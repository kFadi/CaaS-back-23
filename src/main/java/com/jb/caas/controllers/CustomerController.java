package com.jb.caas.controllers;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.CouponSystemException;
import com.jb.caas.exceptions.SecMsg;
import com.jb.caas.security.ClientType;
import com.jb.caas.security.TokenManager;
import com.jb.caas.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    private final TokenManager tokenManager;
    private final CustomerService customerService;

    //---------------------------------------------------------------------------------

    @PostMapping("/coupons")
    @ResponseStatus(HttpStatus.CREATED)
    public void purchaseCoupon(@RequestHeader("Authorization") UUID token, @Valid @RequestBody Coupon coupon) throws CouponSecurityException, CouponSystemException {

        int customerId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.CUSTOMER))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        customerService.purchaseCoupon(customerId, coupon);
    }

    @GetMapping("/coupons")
    public List<Coupon> getCustomerCoupons(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {

        int customerId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.CUSTOMER))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return customerService.getCustomerCoupons(customerId);
    }

    @GetMapping("/coupons/categories")
    public List<Coupon> getCustomerCoupons(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CouponSecurityException {

        int customerId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.CUSTOMER))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return customerService.getCustomerCoupons(customerId, category);
    }

    @GetMapping("/coupons/price")
    public List<Coupon> getCustomerCoupons(@RequestHeader("Authorization") UUID token, @RequestParam double max) throws CouponSecurityException {

        int customerId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.CUSTOMER))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return customerService.getCustomerCoupons(customerId, max);
    }

    //---------------------------------------------------------------------------------

    @GetMapping
    public Customer getCustomerDetails(@RequestHeader("Authorization") UUID token) throws CouponSecurityException, CouponSystemException {

        int customerId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.CUSTOMER))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return customerService.getCustomerDetails(customerId);
    }

    //---------------------------------------------------------------------------------

}
