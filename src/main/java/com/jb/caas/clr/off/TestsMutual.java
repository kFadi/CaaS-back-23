package com.jb.caas.clr.off;

/*
 * copyrights @ fadi
 */

import com.jb.caas.security.ClientType;
import com.jb.caas.security.LoginManager;
import com.jb.caas.services.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.jb.caas.utils.ArtUtils.*;
import static com.jb.caas.utils.PrintUtils.*;

@Component
@RequiredArgsConstructor
@Getter
public class TestsMutual {

    private final int NUM_OF_COMPANIES = 5;
    private final int NUM_OF_COUPONS = 3;
    private final int NUM_OF_CUSTOMERS = 9;
    private final int NUM_OF_PURCHASES = 3;

    @Getter(AccessLevel.NONE)
    private final LoginManager loginManager;

    //#################################################
    //#################################################

    public void adBadLogins() {

        String usr = "admin@site.com";
        String pss = "admin";
        ClientType cl = ClientType.ADMIN;
        printTestTitle(false, loginStr(cl, usr, pss));
        try {
            loginManager.login(usr, pss, cl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        usr = "admin@admin.com";
        pss = "admin";
        cl = ClientType.COMPANY;
        printTestTitle(false, loginStr(cl, usr, pss));
        try {
            loginManager.login(usr, pss, cl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //#################################################

    public AdminService adGoodLogin() {

        ClientService clientService = null;

        String usr = "admin@admin.com";
        String pss = "admin";
        ClientType cl = ClientType.ADMIN;

        printTestTitle(true, loginStr(cl, usr, pss));

        try {
            clientService = loginManager.login(usr, pss, cl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ART_LOGIN_ADMIN);
        return (AdminService) clientService;
    }

    //#################################################

    public void adPrintCoupons() {
        printTestPhaseTitle("ADMIN Service Test - print all Coupons", "");

        AdminService adminService = adGoodLogin(/*loginManager*/);

        try {
            printTableCoupons(adminService.getAllCoupons(), "", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //#################################################
    //#################################################

    public void cmpBadLogins() {

        String usr = "CompanyEmail_11_@site.com";
        String pss = "CompanyPass_11";
        ClientType cl = ClientType.COMPANY;
        printTestTitle(false, loginStr(cl, usr, pss));
        try {
            loginManager.login(usr, pss, cl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        usr = "CompanyEmail_1_@site.com";
        pss = "CompanyPass_1";
        cl = ClientType.CUSTOMER;
        printTestTitle(false, loginStr(cl, usr, pss));
        try {
            loginManager.login(usr, pss, cl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //#################################################

    public CompanyService cmpGoodLogin(int i, int deletedIdx) {

        ClientService clientService;

        String usr = "CompanyEmail_" + i + "_@site.com";
        String pss = "CompanyPass_" + i;
        ClientType cl = ClientType.COMPANY;

        printTestTitle(i != deletedIdx, (i == deletedIdx ? "* company#" + deletedIdx + " has been deleted *  " : "") + loginStr(cl, usr, pss));

        try {
            clientService = loginManager.login(usr, pss, cl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        CompanyService companyService = (CompanyService) clientService;
        System.out.println(ART_LOGIN_COMPANY + ((CompanyServiceImpl) companyService).getCompanyId() + "\n");
        return companyService;
    }

    //#################################################

    public void cmpPrintCoupons(int deletedIdx) {

        printTestPhaseTitle("COMPANY Service Test - print companies' Coupons", "");
        CompanyService companyService;

        for (int i = 1; i <= NUM_OF_COMPANIES; i++) {
            companyService = cmpGoodLogin(i, deletedIdx);
            if (companyService != null) {
                try {
                    printTestTitle(true, "Get Company's coupons");
                    printTableCoupons(companyService.getCompanyCoupons(), String.valueOf(companyService.getCompanyDetails().getId()), true);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    //#################################################
    //#################################################

    public void cstBadLogins() {

        String usr = "CustomerEmail_11_@site.com";
        String pss = "CustomerPass_11";
        ClientType cl = ClientType.CUSTOMER;
        printTestTitle(false, loginStr(cl, usr, pss));
        try {
            loginManager.login(usr, pss, cl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        usr = "CustomerEmail_1_@site.com";
        pss = "CustomerPass_1";
        cl = ClientType.COMPANY;
        printTestTitle(false, loginStr(cl, usr, pss));
        try {
            loginManager.login(usr, pss, cl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //#################################################

    public CustomerService cstGoodLogin(int i, int deletedIdx) {

        ClientService clientService;

        String usr = "CustomerEmail_" + i + "_@site.com";
        String pss = "CustomerPass_" + i;
        ClientType cl = ClientType.CUSTOMER;

        printTestTitle(i != deletedIdx, (i == deletedIdx ? "* customer#" + deletedIdx + " has been deleted *  " : "") + loginStr(cl, usr, pss));

        try {
            clientService = loginManager.login(usr, pss, cl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        CustomerService customerService = (CustomerService) clientService;
        System.out.println(ART_LOGIN_CUSTOMER + ((CustomerServiceImpl) customerService).getCustomerId() + "\n");
        return customerService;
    }

    //#################################################

    public void cstPrintCoupons(int deletedIdx) {

        printTestPhaseTitle("CUSTOMER Service Test - print customers' purchased Coupons", "");
        CustomerService customerService;
        //String str;

        for (int i = 1; i <= NUM_OF_CUSTOMERS; i++) {
            customerService = cstGoodLogin(i, deletedIdx);
            if (customerService != null) {
                try {
                    printTestTitle(true, "Get Customer's coupons");
                    printTableCoupons(customerService.getCustomerCoupons(), String.valueOf(((CustomerServiceImpl) customerService).getCustomerId()), false);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    //#################################################
    //#################################################

    private String loginStr(ClientType cl, String usr, String pss) {
        return "login as " + cl + "  (" + usr + ":" + pss + ")";
    }

}
