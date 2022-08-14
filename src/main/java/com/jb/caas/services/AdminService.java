package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.CouponSystemException;

import java.util.List;

public interface AdminService {

    //---------------------------------------------------------------------------------

    Company addCompany(Company company) throws CouponSecurityException;

    Company updateCompany(int companyId, Company company) throws CouponSystemException, CouponSecurityException;

    void deleteCompany(int companyId) throws CouponSystemException;

    List<Company> getAllCompanies();

    Company getOneCompany(int companyId) throws CouponSystemException;

    //---------------------------------------------------------------------------------

    Customer addCustomer(Customer customer) throws CouponSecurityException;

    Customer updateCustomer(int customerId, Customer customer) throws CouponSystemException, CouponSecurityException;

    void deleteCustomer(int customerId) throws CouponSystemException;

    List<Customer> getAllCustomers();

    Customer getOneCustomer(int customerId) throws CouponSystemException;

    //---------------------------------------------------------------------------------

    List<Coupon> getAllCoupons();

    //---------------------------------------------------------------------------------
}
