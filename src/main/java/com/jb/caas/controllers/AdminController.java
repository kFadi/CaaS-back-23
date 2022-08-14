package com.jb.caas.controllers;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.CouponSystemException;
import com.jb.caas.exceptions.SecMsg;
import com.jb.caas.security.ClientType;
import com.jb.caas.security.TokenManager;
import com.jb.caas.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final TokenManager tokenManager;
    private final AdminService adminService;

    //---------------------------------------------------------------------------------

    @PostMapping("/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestHeader("Authorization") UUID token, @Valid @RequestBody Company company) throws CouponSecurityException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return adminService.addCompany(company);
    }

    @PutMapping("/companies/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public Company updateCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id, @Valid @RequestBody Company company) throws CouponSecurityException, CouponSystemException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return adminService.updateCompany(id, company);
    }

    @DeleteMapping("/companies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSecurityException, CouponSystemException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        adminService.deleteCompany(id);
    }

    @GetMapping("/companies")
    public List<Company> getAllCompanies(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return adminService.getAllCompanies();
    }

    @GetMapping("/companies/{id}")
    public Company getOneCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSecurityException, CouponSystemException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return adminService.getOneCompany(id);
    }

    //---------------------------------------------------------------------------------

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addCustomer(@RequestHeader("Authorization") UUID token, @Valid @RequestBody Customer customer) throws CouponSecurityException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return adminService.addCustomer(customer);
    }

    @PutMapping("/customers/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public Customer updateCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id, @Valid @RequestBody Customer customer) throws CouponSecurityException, CouponSystemException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return adminService.updateCustomer(id, customer);
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSecurityException, CouponSystemException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        adminService.deleteCustomer(id);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return adminService.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getOneCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSecurityException, CouponSystemException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return adminService.getOneCustomer(id);
    }

    //---------------------------------------------------------------------------------

    @GetMapping("/coupons")
    public List<Coupon> getAllCoupons(@RequestHeader("Authorization") UUID token) throws CouponSecurityException {

        if (!(tokenManager.getTypeByToken(token).equals(ClientType.ADMIN))) {
            throw new CouponSecurityException(SecMsg.UNAUTHORIZED_OPERATION);
        }

        return adminService.getAllCoupons();
    }

    //---------------------------------------------------------------------------------

}
