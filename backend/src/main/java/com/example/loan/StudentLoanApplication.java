package com.example.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class StudentLoanApplication extends SpringBootServletInitializer {

    // Used when the WAR is deployed to an external Tomcat 9
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StudentLoanApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentLoanApplication.class, args);
    }
}
