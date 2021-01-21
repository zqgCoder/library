package com.example.busonline.service.impl;

import com.example.busonline.entity.User;
import com.example.busonline.mapper.UserMapper;
import com.example.busonline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> findAll(Integer start, Integer end, String username, String gender) {
        return userMapper.findAll(start, end, username, gender);
    }

    //查询总记录数
    @Override
    public Integer findTotal(String username, String gender) {
        return userMapper.findTotal(username,gender);
    }

    @Override
    public User login(Integer id, String password) {
        return userMapper.login(id, password);
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public Integer deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public Integer changePwd(User user) {
        return userMapper.changePwd(user);
    }
}
