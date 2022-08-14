package com.jb.caas.clr;

/*
 * copyrights @ fadi
 */


import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
import com.jb.caas.repos.CompanyRepository;
import com.jb.caas.repos.CouponRepository;
import com.jb.caas.repos.CustomerRepository;
import com.jb.caas.services.AdminService;
import com.jb.caas.services.CompanyService;
import com.jb.caas.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.jb.caas.utils.FactoryUtils.*;

@Component
@Order(2)
@RequiredArgsConstructor
public class SomeInit implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;

    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {


        for (int i = 1; i <= 3; i++) {

            Company comp = initCompany();
            companyRepository.save(comp);

            for (int j = 0; j < 5; j++) {
                Coupon cpn = initCoupon();
                cpn.setCompany(comp);
                couponRepository.save(cpn);
            }

            Customer cust = initCustomer();
            customerRepository.save(cust);

        }

        for (int i = 1; i <= 3; i++) {
            customerService.purchaseCoupon(1, couponRepository.getReferenceById(5 * i - 4));
            customerService.purchaseCoupon(2, couponRepository.getReferenceById(5 * i - 2));
            customerService.purchaseCoupon(3, couponRepository.getReferenceById(5 * i));
        }

    }
}
