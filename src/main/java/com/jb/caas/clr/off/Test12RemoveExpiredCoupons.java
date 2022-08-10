package com.jb.caas.clr.off;

/*
 * copyrights @ fadi
 */

import com.jb.caas.repos.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.jb.caas.utils.ArtUtils.ART_BYE;
import static com.jb.caas.utils.PrintUtils.printTableCoupons;
import static com.jb.caas.utils.PrintUtils.printTestPhaseTitle;

@Component
@Order(22)
@RequiredArgsConstructor
public class Test12RemoveExpiredCoupons implements CommandLineRunner {

    //#################################################

    private final CouponRepository couponRepository;

    //#################################################

    @Override
    public void run(String... args) throws Exception {
        printTestPhaseTitle("\"Remove Expired Coupons\" Test", "");

        try {
            viewAll("B E F O R E");

            couponRepository.deleteExpiredCoupons();
            couponRepository.updatePurchasesJoin();

            System.out.println("\n\n");

            viewAll("  A F T E R ");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n\n" + ART_BYE);

    }

    private void viewAll(String str) {

        System.out.println("   ~ ~ ~ ~ ~ * ~ * ~ * ~ * ~ ~ ~ ~ ~");
        System.out.println("  - * - * -   " + str + "   - * - * -");
        System.out.println("~ ~ ~ ~ ~ ~ * ~ * ~ * ~ * ~ * ~ ~ ~ ~ ~\n\n\n");
        printTableCoupons(couponRepository.findAll(), "", true);
        printTableCoupons(couponRepository.findByCompanyId(5), "5", true);
        printTableCoupons(couponRepository.findByCustomerId(9), "9", false);
    }

}
