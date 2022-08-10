package com.jb.caas.clr.off;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Company;
import com.jb.caas.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.jb.caas.utils.PrintUtils.*;

@Component
@Order(18)
@RequiredArgsConstructor
public class Test08AdminDelCompany implements CommandLineRunner {

    //#################################################

    private final TestsMutual testsMutual;

    //#################################################

    @Override
    public void run(String... args) throws Exception {

        String info = "\n\t- login" +
                "\n\t- delete a company -with its coupons- (inc. unsuccessful)" +
                "\n\t- get a company -with its coupons- (inc. unsuccessful)";
        printTestPhaseTitle("ADMIN Service Test - Delete/Get a Company", info);

        AdminService adminService = testsMutual.adGoodLogin();

        try {
            printTestTitle(true, "Print all Companies and Coupons");
            printTableCompanies(adminService.getAllCompanies());
            printTableCoupons(adminService.getAllCoupons(), "", true);

            printTestTitle(true, "Delete Company #3, then get all Companies and Coupons");
            adminService.deleteCompany(3);
            printTableCompanies(adminService.getAllCompanies());
            printTableCoupons(adminService.getAllCoupons(), "", true);

            printTestTitle(true, "Get one Company (#1)");
            Company company = adminService.getOneCompany(1);
            System.out.printf("id #%d\nname: %s\nemail: %s\npassword: %s"
                    , company.getId(), company.getName(), company.getEmail(), company.getPassword());
            printTableCoupons(company.getCoupons(), String.valueOf(company.getId()), true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Delete a Company with a non-existing id (#11)");
        try {
            adminService.deleteCompany(11);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Get a Company with a non-existing id (#11)");
        try {
            System.out.println(adminService.getOneCompany(11));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
