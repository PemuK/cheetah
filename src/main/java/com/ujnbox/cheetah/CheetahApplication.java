package com.ujnbox.cheetah;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.ujnbox.cheetah.dao")
public class CheetahApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheetahApplication.class, args);
    }

}
