package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.repos.CompanyRepository;
import com.jb.caas.repos.CouponRepository;
import com.jb.caas.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class ClientService {

    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected CouponRepository couponRepository;

    //\/\/\/\/\/\/\/\/\/\

    public abstract boolean login(String email, String password);

    //\/\/\/\/\/\/\/\/\/\

}
