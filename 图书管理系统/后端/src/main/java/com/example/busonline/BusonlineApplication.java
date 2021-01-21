package com.example.busonline;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.busonline.mapper")//使用MapperScan批量扫描所有的Mapper接口；
public class BusonlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusonlineApplication.class, args);
    }

}
