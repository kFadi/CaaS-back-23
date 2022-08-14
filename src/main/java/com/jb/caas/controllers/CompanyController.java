package com.jb.caas.controllers;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.CouponSystemException;
import com.jb.caas.exceptions.SecMsg;
import com.jb.caas.security.ClientType;
import com.jb.caas.security.TokenManager;
import com.jb.caas.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CompanyController {

    private final TokenManager tokenManager;
    private final CompanyService companyService;

    //---------------------------------------------------------------------------------

    @PostMapping("/coupons")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoupon(@RequestHeader("Authorization") UUID token, @Valid @RequestBody Coupon coupon) throws CouponSecurityException, CouponSystemException {

        int companyId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.COMPANY))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        companyService.addCoupon(companyId, coupon);

    }

    @PutMapping("/coupons/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int id, @Valid @RequestBody Coupon coupon) throws CouponSecurityException, CouponSystemException {

        int companyId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.COMPANY))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        companyService.updateCoupon(companyId, id, coupon);
    }

    @DeleteMapping("/coupons/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSecurityException, CouponSystemException {

        int companyId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.COMPANY))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        companyService.deleteCoupon(companyId, id);

    }

    //---------------------------------------------------------------------------------

    @GetMapping("/coupons")
    public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {

        int companyId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.COMPANY))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return companyService.getCompanyCoupons(companyId);
    }

    @GetMapping("/coupons/categories")
    public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CouponSecurityException {

        int companyId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.COMPANY))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return companyService.getCompanyCoupons(companyId, category);
    }

    @GetMapping("/coupons/price")
    public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @RequestParam double max) throws CouponSecurityException {

        int companyId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.COMPANY))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return companyService.getCompanyCoupons(companyId, max);
    }

    //---------------------------------------------------------------------------------

    @GetMapping
    public Company getCompanyDetails(@RequestHeader("Authorization") UUID token) throws CouponSecurityException, CouponSystemException {

        int companyId = tokenManager.getIdByToken(token);
        if (!(tokenManager.getTypeByToken(token).equals(ClientType.COMPANY))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return companyService.getCompanyDetails(companyId);
    }

    //---------------------------------------------------------------------------------

}
