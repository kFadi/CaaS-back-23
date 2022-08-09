package com.jb.caas.utils;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Company;
import com.jb.caas.beans.Coupon;
import com.jb.caas.beans.Customer;

import java.util.List;

import static com.jb.caas.utils.DateUtils.beautifyDate;


public class PrintUtils {

    //#################################################

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    //#################################################

    public static final String SMILE = ANSI_GREEN + "  :)" + ANSI_RESET;

    private static final int W_VARCHAR = 30; //50
    private static final int W_OTHERS = 15;

    //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\///

    public static void printTestTitle(boolean trueForSucceedFalseForFail, String str) {
        String s = trueForSucceedFalseForFail ? ANSI_GREEN + "succeed" + ANSI_RESET : ANSI_RED + "fail" + ANSI_RESET;
        System.out.printf("\n . . . . . .   T E S T I N G  [should %s]  " + (trueForSucceedFalseForFail ? "" : "\t") + "%s\t. . . . . . \n\n", s, str);
    }


    public static void printTestPhaseTitle(String title, String info) {
        System.out.println("\n\n");
        System.out.println(ANSI_CYAN + "  § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § §");
        System.out.println(ANSI_BLUE + " * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *" + ANSI_RESET);
        System.out.println(" " + title);
        System.out.print(ANSI_CYAN + " - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -" + ANSI_RESET);
        System.out.println(info);
        System.out.println(ANSI_BLUE + " * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println(ANSI_CYAN + "  § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § § §\n\n\n" + ANSI_RESET);

    }

    //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\///

    private static StringBuilder sbRowOfOneCol(String text, boolean isVarchar, boolean isLastCol) {

        int w = (isVarchar) ? W_VARCHAR : W_OTHERS;
        int wText = text.length();
        int wBefore = (w - wText) / 2;
        int wAfter = w - wBefore - wText;

        StringBuilder sb = new StringBuilder("|");

        for (int i = 0; i < wBefore; i++) {
            sb.append(" ");
        }
        sb.append(text);
        for (int i = 0; i < wAfter; i++) {
            sb.append(" ");
        }
        if (isLastCol) {
            sb.append("|\n");
        }

        return sb;
    }

    private static StringBuilder sbLine(int varchars, int others, boolean isTitle) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < W_VARCHAR * varchars + W_OTHERS * others + varchars + others + 1; i++) {
            sb.append((isTitle) ? "#" : "─");
        }

        sb.append("\n");
        return sb;
    }

    //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\///

    private static void noSuch(String str) {
        String s = ANSI_YELLOW + "   / / / / / / / / / / /" + ANSI_RESET;
        System.out.println(s + "   no such " + str + " to display!" + s);
    }


    public static void printTableCompanies(List<Company> companies) {

        if (companies.isEmpty()) {
            noSuch("companies");
            return;
        }

        StringBuilder sb = new StringBuilder(ArtUtils.ART_TABLE_COMPANIES);

        sb.append(sbRowOfOneCol("id", false, false));
        sb.append(sbRowOfOneCol("name", true, false));
        sb.append(sbRowOfOneCol("email", true, false));
        sb.append(sbRowOfOneCol("password", true, true));

        sb.append(sbLine(3, 1, true));

        for (Company company : companies) {
            sb.append(sbRowOfOneCol(String.valueOf(company.getId()), false, false));
            sb.append(sbRowOfOneCol(company.getName(), true, false));
            sb.append(sbRowOfOneCol(company.getEmail(), true, false));
            sb.append(sbRowOfOneCol(company.getPassword(), true, true));
            sb.append(sbLine(3, 1, false));
        }

        System.out.println(sb);
    }


    public static void printTableCustomers(List<Customer> customers) {

        if (customers.isEmpty()) {
            noSuch("customers");
            return;
        }

        StringBuilder sb = new StringBuilder(ArtUtils.ART_TABLE_CUSTOMERS);

        sb.append(sbRowOfOneCol("id", false, false));
        sb.append(sbRowOfOneCol("first_name", true, false));
        sb.append(sbRowOfOneCol("last_name", true, false));
        sb.append(sbRowOfOneCol("email", true, false));
        sb.append(sbRowOfOneCol("password", true, true));

        sb.append(sbLine(4, 1, true));

        for (Customer customer : customers) {
            sb.append(sbRowOfOneCol(String.valueOf(customer.getId()), false, false));
            sb.append(sbRowOfOneCol(customer.getFirstName(), true, false));
            sb.append(sbRowOfOneCol(customer.getLastName(), true, false));
            sb.append(sbRowOfOneCol(customer.getEmail(), true, false));
            sb.append(sbRowOfOneCol(customer.getPassword(), true, true));
            sb.append(sbLine(4, 1, false));
        }

        System.out.println(sb);
    }


    public static void printTableCoupons(List<Coupon> coupons, String str, boolean trueForCompanyFalseForCustomer) {

        if (coupons.isEmpty()) {
            noSuch("coupons");
            return;
        }

        StringBuilder sb = new StringBuilder();

        if (!str.equals("")) {
            String who = (trueForCompanyFalseForCustomer) ? "Company" : "Customer";
            sb.append("\n────────────────────────────\n" + who + " #" + str + " 's\n");
        }

        sb.append(ArtUtils.ART_TABLE_COUPONS);

        sb.append(sbRowOfOneCol("id", false, false));
        sb.append(sbRowOfOneCol("company_id", false, false));
        sb.append(sbRowOfOneCol("category", false, false));
        sb.append(sbRowOfOneCol("title", true, false));
        sb.append(sbRowOfOneCol("description", true, false));
        sb.append(sbRowOfOneCol("start_date", false, false));
        sb.append(sbRowOfOneCol("end_date", false, false));
        sb.append(sbRowOfOneCol("amount", false, false));
        sb.append(sbRowOfOneCol("price", false, false));
        sb.append(sbRowOfOneCol("image", true, true));

        sb.append(sbLine(3, 7, true));

        for (Coupon coupon : coupons) {
            sb.append(sbRowOfOneCol(String.valueOf(coupon.getId()), false, false));
            sb.append(sbRowOfOneCol(String.valueOf(coupon.getCompany().getId()), false, false));
            sb.append(sbRowOfOneCol(String.valueOf(coupon.getCategory()), false, false));
            sb.append(sbRowOfOneCol(coupon.getTitle(), true, false));
            sb.append(sbRowOfOneCol(coupon.getDescription(), true, false));
            sb.append(sbRowOfOneCol(beautifyDate(coupon.getStartDate()), false, false));
            sb.append(sbRowOfOneCol(beautifyDate(coupon.getEndDate()), false, false));
            sb.append(sbRowOfOneCol(String.valueOf(coupon.getAmount()), false, false));
            sb.append(sbRowOfOneCol(String.valueOf(coupon.getPrice()), false, false));
            sb.append(sbRowOfOneCol(coupon.getImage(), true, true));
            sb.append(sbLine(3, 7, false));
        }

        System.out.println(sb);
    }

}
