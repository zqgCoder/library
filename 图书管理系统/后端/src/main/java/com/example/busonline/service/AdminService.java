package com.example.busonline.service;

import com.example.busonline.entity.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> findAll();

    Admin findByNameAndPwd(Integer id, String password);

    Admin getById(Integer id);

    //修改密码
    Integer changePwd(Admin admin);
}
