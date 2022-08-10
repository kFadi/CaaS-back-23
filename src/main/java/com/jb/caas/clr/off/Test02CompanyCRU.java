package com.jb.caas.clr.off;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
import com.jb.caas.repos.CouponRepository;
import com.jb.caas.repos.CustomerRepository;
import com.jb.caas.services.CompanyService;
import com.jb.caas.services.CompanyServiceImpl;
import com.jb.caas.utils.FactoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.jb.caas.utils.DateUtils.beautifyDate;
import static com.jb.caas.utils.PrintUtils.*;

@Component
@Order(12)
@RequiredArgsConstructor
public class Test02CompanyCRU implements CommandLineRunner {

    //#################################################

    private final TestsMutual testsMutual;

    // for explicit add expired coupons and purchase of:
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;

    //#################################################

    @Override
    public void run(String... args) throws Exception {

        String info = "\n\t- logins (inc. unsuccessful)" +
                "\n\t- add/update coupons (inc. unsuccessful)" +
                "\n\t- get companies' details (inc. coupons even when no such)" +
                "\n\t- get specific coupons of a category / max price (probably inc. when no such)";
        printTestPhaseTitle("COMPANY Service Test - Logins and CRU", info);

        testsMutual.cmpBadLogins();

        CompanyService companyService = null;
        int companyId = 0;
        List<Coupon> coupons;
        Coupon coupon;
        Category category;
        double maxPrice;

        for (int i = 1; i <= testsMutual.getNUM_OF_COMPANIES(); i++) {

            companyService = testsMutual.cmpGoodLogin(i, 0);
            companyId = ((CompanyServiceImpl) companyService).getCompanyId();

            try {
                printTestTitle(true, "Get all Coupons (none for now!)");
                coupons = companyService.getCompanyCoupons();
                printTableCoupons(coupons, String.valueOf(companyId), true);

                printTestTitle(true, "Add " + testsMutual.getNUM_OF_COUPONS() + " Coupons");
                for (int j = 0; j < testsMutual.getNUM_OF_COUPONS(); j++) {
                    coupon = FactoryUtils.initCoupon(true);
                    companyService.addCoupon(coupon);
                }

                printTestTitle(true, "Get Company Details");
                Company company = companyService.getCompanyDetails();
                System.out.printf("id #%d\nname: %s\nemail: %s\npassword: %s"
                        , company.getId(), company.getName(), company.getEmail(), company.getPassword());
                printTableCoupons(company.getCoupons(), String.valueOf(companyId), true);

                category = Category.values()[(int) (Math.random() * Category.values().length)];
                printTestTitle(true, "Get Company's Coupons of Category " + category);
                coupons = companyService.getCompanyCoupons(category);
                printTableCoupons(coupons, String.format("%d (%s)", companyId, category), true);

                maxPrice = 5 + ((int) (Math.random() * 15)) + ((int) (Math.random() * 10)) / 10.0;
                printTestTitle(true, "Get Company's Coupons with Max Price of " + maxPrice);
                coupons = companyService.getCompanyCoupons(maxPrice);
                printTableCoupons(coupons, String.format("%d (<=%s)", companyId, maxPrice), true);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\///

        coupon = FactoryUtils.initCoupon(false);

        printTestTitle(false, "Add a Coupon with an existing title (\"CouponTitle_14\")");
        coupon.setTitle("CouponTitle_14");
        try {
            companyService.addCoupon(coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        coupon.setTitle("someTitle");

        Date startDate = Date.valueOf(LocalDate.now().plusDays(2));
        Date endDate = Date.valueOf(LocalDate.now());
        printTestTitle(false, "Add a Coupon with invalid dates (starts after it ends! : " + beautifyDate(startDate) + " --> " + beautifyDate(endDate) + ")");
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);
        try {
            companyService.addCoupon(coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        startDate = Date.valueOf(LocalDate.now().minusDays(6));
        endDate = Date.valueOf(LocalDate.now().minusDays(5));
        printTestTitle(false, "Add an expired Coupon (" + beautifyDate(startDate) + " --> " + beautifyDate(endDate) + ")");
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);
        try {
            companyService.addCoupon(coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        startDate = Date.valueOf(LocalDate.now());
        endDate = startDate;
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);

        printTestTitle(false, "Add a Coupon with a negative amount (-1)");
        coupon.setAmount(-1);
        try {
            companyService.addCoupon(coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        coupon.setAmount(1);

        //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\///

        printTestTitle(false, "Update a Coupon with a non-existing id (#22)");
        try {
            companyService.updateCoupon(22, coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Update a Coupon with an id (#11) of other Company (#4)");
        try {
            companyService.updateCoupon(11, coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Update a Coupon of the company (#14) to one with an existing title of other coupon of the company (\"CouponTitle_15\")");
        coupon.setTitle("CouponTitle_15");
        try {
            companyService.updateCoupon(14, coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        coupon.setTitle("someTitle");

        startDate = Date.valueOf(LocalDate.now().plusDays(2));
        endDate = Date.valueOf(LocalDate.now());
        printTestTitle(false, "Update a Coupon of the company (#14) to one with invalid dates (starts after it ends! : " + beautifyDate(startDate) + " --> " + beautifyDate(endDate) + ")");
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);
        try {
            companyService.updateCoupon(14, coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        startDate = Date.valueOf(LocalDate.now().minusDays(6));
        endDate = Date.valueOf(LocalDate.now().minusDays(5));
        printTestTitle(false, "Update a Coupon of the company (#14) to one that's expired (" + beautifyDate(startDate) + " --> " + beautifyDate(endDate) + ")");
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);
        try {
            companyService.updateCoupon(14, coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        startDate = Date.valueOf(LocalDate.now());
        endDate = startDate;
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);

        printTestTitle(false, "Update a Coupon of the company (#14) to one with a negative amount (-1)");
        coupon.setAmount(-1);
        try {
            companyService.updateCoupon(14, coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        coupon.setAmount(1);

        try {
            printTestTitle(true, "Update Coupon 15 (including title)");
            companyService.updateCoupon(15, coupon);
            printTableCoupons(companyService.getCompanyCoupons(), String.valueOf(companyId), true);

            printTestTitle(true, "Update back Coupon 15's title");
            coupon.setTitle("CouponTitle_15");
            companyService.updateCoupon(15, coupon);
            printTableCoupons(companyService.getCompanyCoupons(), String.valueOf(companyId), true);

            printTestTitle(true, "Update Coupon 15 [note title was not changed but should pass]");
            coupon.setDescription("CouponDescription_15");
            coupon.setImage("CouponImage_15");
            coupon.setAmount(3);
            companyService.updateCoupon(15, coupon);
            printTableCoupons(companyService.getCompanyCoupons(), String.valueOf(companyId), true);

            System.out.println(ANSI_PURPLE + "\n///////////////////////////////////////////////////////////////////////////");
            System.out.println("///// " + ANSI_RESET + "-  " + ANSI_RED + "N O T E  " + ANSI_RESET + "-  for testing purposes : " + ANSI_PURPLE + "///////////////////////////////");
            System.out.println("/////  " + ANSI_RESET + "manually added 3 " + ANSI_RED + "expired " + ANSI_RESET + "coupons #16, #17, #18  to company #5  " + ANSI_PURPLE + "/////");
            System.out.println("///////////////////  " + ANSI_RESET + "and a purchase of coupon #18 by customer #9  " + ANSI_PURPLE + "/////////");
            System.out.println("///////////////////////////////////////////////////////////////////////////\n" + ANSI_RESET);

            Company company = companyService.getCompanyDetails();  //company 5

            for (int i = 1; i <= 3; i++) {
                endDate = Date.valueOf(LocalDate.now().minusDays(5 + (int) (Math.random() * 6)));
                startDate = Date.valueOf(LocalDate.now().minusDays(10 + (int) (Math.random() * 11)));

                coupon = Coupon.builder()
                        .startDate(startDate)
                        .endDate(endDate)
                        .category(Category.FOOD)
                        .title("my title " + i)
                        .description("my description " + i)
                        .image("my image " + i)
                        .amount(1)
                        .price(1.1)
                        .company(company)
                        .build();

                couponRepository.save(coupon);
            }
            Customer cst9 = customerRepository.getById(9);
            Set<Coupon> cpnsCst9 = cst9.getCoupons();
            cpnsCst9.add(coupon);
            customerRepository.saveAndFlush(cst9);

            printTableCoupons(companyService.getCompanyCoupons(), String.valueOf(companyId), true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
