package com.example.busonline.service.impl;

import com.example.busonline.entity.Admin;
import com.example.busonline.mapper.AdminMapper;
import com.example.busonline.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AdminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> findAll() {
        return adminMapper.findAll();
    }

    @Override
    public Admin findByNameAndPwd(Integer id, String password) {
        return adminMapper.findByNameAndPwd(id, password);
    }

    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    @Override
    public Integer changePwd(Admin admin) {
        return adminMapper.changePwd(admin);
    }
}
