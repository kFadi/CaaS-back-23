package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.CouponSystemException;
import com.jb.caas.exceptions.ErrMsg;
import com.jb.caas.exceptions.SecMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class AdminServiceImpl extends ClientService implements AdminService {

    // AGREED - all string values comparisons are Case_Insensitive

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\

    @Value("${login.admin.email}")
    private String adminEmail;

    @Value("${login.admin.password}")
    private String adminPassword;

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\

    @Override
    public boolean login(String email, String password) {
        // admin@admin.com : admin
        return email.equalsIgnoreCase(adminEmail) && password.equalsIgnoreCase(adminPassword);
    }

    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\


    @Override
    public Company addCompany(Company company) throws CouponSecurityException {
        // no duplicates name OR email
        // agreed: ignore value of company's id / coupons
        // added: name And email can have duplicates across 3 Client Types

        company.setId(0);
        company.setCoupons(new ArrayList<>());

        if (companyRepository.existsByName(company.getName())) {
            throw new CouponSecurityException(SecMsg.COMPANY_ALREADY_EXISTS_NAME);
        }
        if (companyRepository.existsByEmail(company.getEmail())) {
            throw new CouponSecurityException(SecMsg.COMPANY_ALREADY_EXISTS_EMAIL);
        }

        companyRepository.save(company);

        return company;
    }

    @Override
    public Company updateCompany(int companyId, Company company) throws CouponSystemException, CouponSecurityException {
        // no update name
        // no update (ignore) company's id / coupons
        // no duplicates email (concluded from addCompany(..))
        // added: email can have duplicates across 3 Client Types

        company.setId(companyId);

        Company dbCmp = companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.COMPANY_ID_NOT_FOUND));

        company.setCoupons(dbCmp.getCoupons());

        if (!dbCmp.getName().equalsIgnoreCase(company.getName())) {
            throw new CouponSystemException(ErrMsg.COMPANY_NO_UPDATE_NAME);
        }

        String email = company.getEmail();
        if (!dbCmp.getEmail().equalsIgnoreCase(email) && companyRepository.existsByEmail(email)) {
            throw new CouponSecurityException(SecMsg.COMPANY_ALREADY_EXISTS_EMAIL);
        }

        companyRepository.saveAndFlush(company);

        return company;
    }

    @Override
    public void deleteCompany(int companyId) throws CouponSystemException {
        // delete company's coupons AND customers' purchase history

        if (!companyRepository.existsById(companyId)) {
            throw new CouponSystemException(ErrMsg.COMPANY_ID_NOT_FOUND);
        }
        companyRepository.deleteById(companyId);
        couponRepository.updatePurchasesJoin();
    }

    @Override
    public List<Company> getAllCompanies() {

        return companyRepository.findAll();
    }

    @Override
    public Company getOneCompany(int companyId) throws CouponSystemException {

        return companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.COMPANY_ID_NOT_FOUND));
    }


    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\


    @Override
    public Customer addCustomer(Customer customer) throws CouponSecurityException {
        // no duplicates email
        // agreed: ignore value of customer's id / coupons
        // added: email can have duplicates across 3 Client Types

        customer.setId(0);
        customer.setCoupons(new HashSet<>());

        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new CouponSecurityException(SecMsg.CUSTOMER_ALREADY_EXISTS_EMAIL);
        }
        customerRepository.save(customer);

        return customer;
    }

    @Override
    public Customer updateCustomer(int customerId, Customer customer) throws CouponSystemException, CouponSecurityException {
        // no update (ignore) value of customer's id / coupons
        // no duplicates email (concluded from addCustomer(..))
        // added: email can have duplicates across 3 Client Types

        customer.setId(customerId);

        Customer dbCst = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.CUSTOMER_ID_NOT_FOUND));

        customer.setCoupons(dbCst.getCoupons());

        if (!dbCst.getEmail().equalsIgnoreCase(customer.getEmail()) && customerRepository.existsByEmail(customer.getEmail())) {
            throw new CouponSecurityException(SecMsg.CUSTOMER_ALREADY_EXISTS_EMAIL);
        }
        customerRepository.saveAndFlush(customer);

        return customer;
    }

    @Override
    public void deleteCustomer(int customerId) throws CouponSystemException {
        // delete customers' purchase history

        if (!customerRepository.existsById(customerId)) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_ID_NOT_FOUND);
        }

        customerRepository.deleteById(customerId);
    }

    @Override
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    @Override
    public Customer getOneCustomer(int customerId) throws CouponSystemException {
        return customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.CUSTOMER_ID_NOT_FOUND));
    }


    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\


    @Override
    public List<Coupon> getAllCoupons() {

        return couponRepository.findAll();
    }


    //\/\/\/\/\/\/\/\/\/\
    //\/\/\/\/\/\/\/\/\/\
}
