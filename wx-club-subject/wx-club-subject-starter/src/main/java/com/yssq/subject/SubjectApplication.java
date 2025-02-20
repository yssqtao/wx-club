package com.yssq.subject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.yssq.**.mapper")
@EnableFeignClients(basePackages = "com.yssq")
public class SubjectApplication {
    public static void main(String[] args) {

        SpringApplication.run(SubjectApplication.class);
    }
}
