package com.jb.caas.clr.off;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Company;
import com.jb.caas.beans.Customer;
import com.jb.caas.services.AdminService;
import com.jb.caas.utils.FactoryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.jb.caas.utils.PrintUtils.*;

@Component
@Order(11)
@RequiredArgsConstructor
public class Test01AdminCRU implements CommandLineRunner {

    //#################################################

    private final TestsMutual testsMutual;

    //#################################################

    @Override
    public void run(String... args) throws Exception {

        String info = "\n\t- logins (inc. unsuccessful)" +
                "\n\t- add/update companies (inc. unsuccessful)" +
                "\n\t- add/update customers (inc. unsuccessful)" +
                "\n\t- get all companies/customers (inc. when no such yet)";
        printTestPhaseTitle("ADMIN Service Test - Logins & CRU", info);

        testsMutual.adBadLogins();
        AdminService adminService = testsMutual.adGoodLogin();

        try {
            printTestTitle(true, "Get all Companies (none for now!)");
            printTableCompanies(adminService.getAllCompanies());

            printTestTitle(true, "Add Companies 1 to " + testsMutual.getNUM_OF_COMPANIES());
            for (int i = 0; i < testsMutual.getNUM_OF_COMPANIES(); i++) {
                adminService.addCompany(FactoryUtils.initCompany(true));
            }
            printTableCompanies(adminService.getAllCompanies());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\///

        Company cmp = FactoryUtils.initCompany(false);

        printTestTitle(false, "Add a Company with an existing name (\"COMPANYName_1\")");
        cmp.setName("COMPANYName_1");
        try {
            adminService.addCompany(cmp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        cmp.setName("someName");

        printTestTitle(false, "Add a Company with an existing email (COMPANYEmail_1_@site.com\")");
        cmp.setEmail("COMPANYEmail_1_@site.com");
        try {
            adminService.addCompany(cmp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Update a Company with a non-existing id (#11)");
        try {
            adminService.updateCompany(11, cmp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Update a Company's name");
        try {
            adminService.updateCompany(1, cmp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Update Company 1 to an existing email of another company (\"CompanyEmail_2_@site.com\")");
        cmp.setName("CompanyName_1");
        cmp.setEmail("CompanyEmail_2_@site.com");
        try {
            adminService.updateCompany(1, cmp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            printTestTitle(true, "Update Company 1's email/pass (1->11)");
            cmp.setEmail("CompanyEmail_11_@site.com");
            cmp.setPassword("CompanyPass_11");
            adminService.updateCompany(1, cmp);
            printTableCompanies(adminService.getAllCompanies());

            printTestTitle(true, "Update back Company 1's email (11->1)");
            cmp.setEmail("CompanyEmail_1_@site.com");
            adminService.updateCompany(1, cmp);
            printTableCompanies(adminService.getAllCompanies());

            printTestTitle(true, "Update back Company 1's pass (11->1) [note email was not changed but should pass]");
            cmp.setPassword("CompanyPass_1");
            adminService.updateCompany(1, cmp);
            printTableCompanies(adminService.getAllCompanies());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\///
        //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\///
        System.out.println("\n\n\n");

        try {
            printTestTitle(true, "Get all Customers (none for now!)");
            printTableCustomers(adminService.getAllCustomers());

            printTestTitle(true, "Add Customers 1 to " + testsMutual.getNUM_OF_CUSTOMERS());
            for (int i = 0; i < testsMutual.getNUM_OF_CUSTOMERS(); i++) {
                adminService.addCustomer(FactoryUtils.initCustomer(true));
            }
            printTableCustomers(adminService.getAllCustomers());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\///

        Customer cst = FactoryUtils.initCustomer(false);

        printTestTitle(false, "Add a Customer with an existing email (\"CUSTOMEREmail_1_@site.com\")");
        cst.setEmail("CUSTOMEREmail_1_@site.com");
        try {
            adminService.addCustomer(cst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Update a Customer with a non-existing id (#11)");
        try {
            adminService.updateCustomer(11, cst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Update Customer 1 to an existing email of another customer (\"CustomerEmail_2_@site.com\")");
        cst.setEmail("CUSTOMEREmail_2_@site.com");
        try {
            adminService.updateCustomer(1, cst);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            printTestTitle(true, "Update Customer 1's firstName/lastName/email/pass (1->11)");
            cst.setFirstName("CustomerFirstName_11");
            cst.setLastName("CustomerLastName_11");
            cst.setEmail("CustomerEmail_11_@site.com");
            cst.setPassword("CustomerPass_11");
            adminService.updateCustomer(1, cst);
            printTableCustomers(adminService.getAllCustomers());

            printTestTitle(true, "Update back Customer 1's email (11->1)");
            cst.setEmail("CustomerEmail_1_@site.com");
            adminService.updateCustomer(1, cst);
            printTableCustomers(adminService.getAllCustomers());

            printTestTitle(true, "Update back Customer 1 firstName/lastName/pass (11->1) [note email was not changed but should pass]");
            cst.setFirstName("CustomerFirstName_1");
            cst.setLastName("CustomerLastName_1");
            cst.setPassword("CustomerPass_1");
            adminService.updateCustomer(1, cst);
            printTableCustomers(adminService.getAllCustomers());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
