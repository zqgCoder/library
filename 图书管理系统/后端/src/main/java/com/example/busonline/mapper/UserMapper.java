package com.example.busonline.mapper;

import com.example.busonline.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    //查询所有,根据页数大小
    List<User> findAll(Integer start, Integer end, String username, String gender);

    //查询总记录数
    Integer findTotal(String username, String gender);

    //登录
    User login(Integer id, String password);

    // 添加一个用户
    Integer addUser(User user);

    // 通过id删除用户
    Integer deleteById(Integer id);

    // 修改用户记录
    Integer updateUser(User user);

    // 通过id查询用户
    User getById(Integer id);

    Integer changePwd(User user);
}
