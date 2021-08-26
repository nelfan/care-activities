package com.softserve.careactivities;

import com.google.cloud.spring.data.spanner.repository.config.EnableSpannerRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableSpannerRepositories
@EnableFeignClients
public class CareActivitiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareActivitiesApplication.class, args);
    }

}
