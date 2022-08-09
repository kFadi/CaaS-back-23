package com.jb.caas.clr.off;

/*
 * copyrights @ fadi
 */

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(11)
@RequiredArgsConstructor
public class Test11CustomerPrintCoupons implements CommandLineRunner {

    //#################################################

    private final TestsMutual testsMutual;

    //#################################################

    @Override
    public void run(String... args) throws Exception {

        testsMutual.cstPrintCoupons(3);
    }
}
