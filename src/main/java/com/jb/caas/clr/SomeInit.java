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

    //    private final AdminService adminService;
    //    private final CompanyService companyService;
    private final CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {

        Company comp;
        Customer cust;
        Coupon cpn;

        for (int i = 0; i < 6; i++) {
            comp = initCompany();
            companyRepository.save(comp);
            for (int j = 0; j < 15; j++) {
                cpn = initCoupon();
                cpn.setCompany(comp);
                couponRepository.save(cpn);
            }
        }

        for (int i = 1; i <= 12; i++) {
            cust = initCustomer();
            customerRepository.save(cust);
            for (int j = 1; j <= 6; j++) {
                customerService.purchaseCoupon(i, couponRepository.getReferenceById(1 + (19 * i + 7 * j) % 90));
            }
        }
    }
}
