package com.jb.caas.jobs;

/*
 * copyrights @ fadi
 */

import com.jb.caas.repos.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyRemoveExpiredCoupons {

    private final CouponRepository couponRepository;

    @Scheduled(cron = "@midnight")
    private void go() {
        couponRepository.deleteExpiredCoupons();
        couponRepository.updatePurchasesJoin();
    }

}
