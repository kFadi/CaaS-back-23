package com.jb.caas.clr.off;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Customer;
import com.jb.caas.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jb.caas.utils.PrintUtils.*;

@Component
@Order(10)
@RequiredArgsConstructor
public class Test10AdminDelCustomer implements CommandLineRunner {

    //#################################################

    private final TestsMutual testsMutual;

    //#################################################

    @Override
    public void run(String... args) throws Exception {

        String info = "\n\t- login" +
                "\n\t- delete a customer -with his purchases- (inc. unsuccessful)" +
                "\n\t- get a customer -with his purchases- (inc. unsuccessful)";
        printTestPhaseTitle("ADMIN Service Test - Delete/Get a Customer", info);

        AdminService adminService = testsMutual.adGoodLogin();

        try {
            printTestTitle(true, "Get all Customers and Coupons");
            printTableCustomers(adminService.getAllCustomers());
            printTableCoupons(adminService.getAllCoupons(), "", true);

            printTestTitle(true, "Delete Customer #3 , then get all Customers\t");
            adminService.deleteCustomer(3);
            printTableCustomers(adminService.getAllCustomers());

            printTestTitle(true, "Get one Customer (#1)");
            Customer customer = adminService.getOneCustomer(1);
            System.out.printf("id #%d\nfirst name: %s\nlast name: %s\nemail: %s\npassword: %s"
                    , customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());
            printTableCoupons(List.copyOf(customer.getCoupons()), String.valueOf(customer.getId()), false);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Delete a Customer with a non-existing id (#11)");
        try {
            adminService.deleteCustomer(11);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printTestTitle(false, "Get one Customer with a non-existing id ((#11)");
        try {
            System.out.println(adminService.getOneCustomer(11));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
