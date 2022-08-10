package com.jb.caas.clr.off;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;
import com.jb.caas.services.CustomerService;
import com.jb.caas.services.CustomerServiceImpl;
import com.jb.caas.utils.FactoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jb.caas.utils.PrintUtils.*;

@Component
@Order(14)
@RequiredArgsConstructor

public class Test04CustomerCRU implements CommandLineRunner {


    //#################################################

    private final TestsMutual testsMutual;

    //#################################################

    @Override
    public void run(String... args) throws Exception {

        String info = "\n\t- logins (inc. unsuccessful)" +
                "\n\t- add coupons' purchases (inc. unsuccessful)" +
                "\n\t- get customers' details (inc. purchased coupons even when no such)" +
                "\n\t- get specific purchased coupons of a category / max price (probably inc. when no such)";
        printTestPhaseTitle("CUSTOMER Service Test - Logins and CRU", info);

        testsMutual.cstBadLogins();

        CustomerService customerService = null;
        Customer customer;
        int customerId;
        List<Coupon> coupons;
        Coupon coupon = FactoryUtils.initCoupon(false);
        int couponId;
        Category category;
        double maxPrice;

        String str = "Get Customer's purchased Coupons - ";

        for (int i = 1; i <= testsMutual.getNUM_OF_CUSTOMERS(); i++) {

            customerService = testsMutual.cstGoodLogin(i, 0);
            customerId = ((CustomerServiceImpl) customerService).getCustomerId();
            String s = "none for now!";

            try {
                if (i == 9) {
                    s = " just the previous \"manually-purchased\" coupon #18!";
                }
                printTestTitle(true, str + s);
                coupons = customerService.getCustomerCoupons();
                printTableCoupons(coupons, String.valueOf(customerId), false);

                printTestTitle(true, "Add " + testsMutual.getNUM_OF_PURCHASES() + " purchases");
                for (int j = 0; j < testsMutual.getNUM_OF_PURCHASES(); j++) {
                    couponId = i + testsMutual.getNUM_OF_PURCHASES() * j;
                    coupon.setId(couponId);
                    customerService.purchaseCoupon(coupon);
                }

                printTestTitle(true, "Get Customer Details");
                customer = customerService.getCustomerDetails();
                System.out.printf("id #%d\nfirst name: %s\nlast name: %s\nemail: %s\npassword: %s"
                        , customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());
                printTableCoupons(List.copyOf(customer.getCoupons()), String.valueOf(customer.getId()), false);

                category = Category.values()[(int) (Math.random() * Category.values().length)];
                printTestTitle(true, "Get Customer's Coupons of Category " + category);
                coupons = customerService.getCustomerCoupons(category);
                printTableCoupons(coupons, String.format("%d (%s)", customerId, category), false);

                maxPrice = 5 + ((int) (Math.random() * 15)) + ((int) (Math.random() * 10)) / 10.0;
                printTestTitle(true, "Get Customer's Coupons with Max Price of " + maxPrice);
                coupons = customerService.getCustomerCoupons(maxPrice);
                printTableCoupons(coupons, String.format("%d (<=%s)", customerId, maxPrice), false);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        printTestTitle(false, "Purchase a Coupon with a non-existing id (#22)");
        try {
            coupon.setId(22);
            customerService.purchaseCoupon(coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Purchase a previously purchased Coupon (#12)");
        try {
            coupon.setId(12);
            customerService.purchaseCoupon(coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Purchase a 0 amount Coupon (#8)");
        try {
            coupon.setId(8);
            customerService.purchaseCoupon(coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Purchase an expired Coupon (#16)");
        try {
            coupon.setId(16);
            customerService.purchaseCoupon(coupon);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
