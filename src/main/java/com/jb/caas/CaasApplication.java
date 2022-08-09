package com.jb.caas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = {"com.jb.caas"}, excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "com.jb.caas.clr.off.*"))
public class CaasApplication {

    public static void main(String[] args) {

        SpringApplication.run(CaasApplication.class, args);

        System.out.println("\n\n\n\t\t  ──────────────────────────────────────────");
        System.out.println("\t\t  ──────────────────────────────────────────\n");
        System.out.println("\t\t\t  \" CaaS \"  server is running . . .");
        System.out.println("\n\t\t  ──────────────────────────────────────────");
        System.out.println("\t\t  ──────────────────────────────────────────\n\n");
    }

}
