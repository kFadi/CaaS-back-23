package com.jb.caas.clr.off;

/*
 * copyrights @ fadi
 */

import com.jb.caas.services.CompanyService;
import com.jb.caas.services.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.jb.caas.utils.PrintUtils.*;

@Component
@Order(16)
@RequiredArgsConstructor
public class Test06CompanyDelCoupons implements CommandLineRunner {

    //#################################################

    private final TestsMutual testsMutual;

    //#################################################

    @Override
    public void run(String... args) throws Exception {

        String info = "\n\t- logins" +
                "\n\t- delete coupons (inc. unsuccessful)";
        printTestPhaseTitle("COMPANY Service Test - Delete Coupons", info);

        CompanyService companyService;
        int companyId;
        int couponId;

        for (int i = 2; i <= 4; i += 2) {

            companyService = testsMutual.cmpGoodLogin(i, 0);
            companyId = ((CompanyServiceImpl) companyService).getCompanyId();
            couponId = (i == 2) ? 5 : 10;

            try {
                printTestTitle(true, "Get Company's coupons #BEFORE");
                printTableCoupons(companyService.getCompanyCoupons(), String.valueOf(companyId), true);

                printTestTitle(true, "Delete coupon #" + couponId + " of the company (#" + companyId + ")");
                companyService.deleteCoupon(couponId);

                printTestTitle(true, "Get Company's coupons #AFTER");
                printTableCoupons(companyService.getCompanyCoupons(), String.valueOf(companyId), true);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        companyService = testsMutual.cmpGoodLogin(1, 0);

        printTestTitle(false, "Delete a Coupon with a non-existing id (#22)");
        try {
            companyService.deleteCoupon(22);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Delete a Coupon with an id (#15) of other Company");
        try {
            companyService.deleteCoupon(15);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
