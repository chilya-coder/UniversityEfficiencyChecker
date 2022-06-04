package com.chimyrys.universityefficiencychecker.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.chimyrys.universityefficiencychecker"})
@EnableJpaRepositories(basePackages = "com.chimyrys.universityefficiencychecker.db")
@EntityScan("com.chimyrys.universityefficiencychecker.model")
public class UniversityEfficiencyCheckerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(UniversityEfficiencyCheckerApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(UniversityEfficiencyCheckerApplication.class);
    }

}
