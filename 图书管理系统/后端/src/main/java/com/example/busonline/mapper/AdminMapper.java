package com.example.busonline.mapper;

import com.example.busonline.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<Admin> findAll();

    Admin findByNameAndPwd(Integer id, String password);

    Admin getById(Integer id);

    //修改密码
    Integer changePwd(Admin admin);
}
