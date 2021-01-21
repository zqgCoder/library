package com.example.busonline;

import com.example.busonline.entity.Admin;
import com.example.busonline.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BusonlineApplicationTests {
    private AdminService adminService;
    @Test
    void contextLoads() {
         List<Admin> list = adminService.findAll();
        System.out.println(list.toString());
    }

}
