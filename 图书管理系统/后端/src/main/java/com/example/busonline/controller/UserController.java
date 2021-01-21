package com.example.busonline.controller;

import com.example.busonline.RestfulResponse;
import com.example.busonline.entity.User;
import com.example.busonline.utils.UserPageUtils;
import com.example.busonline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    private RestfulResponse restfulResponse;

    @GetMapping("/findAll")
    public RestfulResponse findAll(@RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize,
                                   @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                   @RequestParam(value = "username",defaultValue = "") String username,
                                   @RequestParam(value = "gender",defaultValue = "") String gender) {
        List<User> list = userService.findAll(pageSize*(currentPage-1), pageSize, username, gender);
        Integer totalSize = userService.findTotal(username, gender);
        UserPageUtils userPageUtils = new UserPageUtils(totalSize,list,username,gender);
        if(list != null) {
            restfulResponse = new RestfulResponse(true,200,"查询成功", userPageUtils);
        } else {
            restfulResponse = new RestfulResponse(true,200,"查询失败，用户数据不存在", null);
        }
        return restfulResponse;
    }

    @PostMapping("/login")
    public RestfulResponse login(@RequestBody Map<String, String> f) {
        String id = f.get("id");
        String password = f.get("password");
        System.out.println(id + "----------" + password);
        User user = userService.login(Integer.valueOf(id), password);
        if(user != null){
            System.out.println(user.toString());
            Map<String, String> map = new HashMap<>();
            map.put("role", "user");
            map.put("userid",user.getId().toString());
            restfulResponse = new RestfulResponse(true, 200, "登录成功", map);
        }
        else
            restfulResponse = new RestfulResponse(false, 200, "用户名或密码错误，登录失败", null);
        return restfulResponse;
    }

    @PostMapping(value = "/add")
    public RestfulResponse addOne(@RequestBody User user) {
        System.out.println(user.toString());
        int result = userService.addUser(user);
        if(result == 1)
            restfulResponse = new RestfulResponse(true,200,"添加用户成功", null);
        else
            restfulResponse = new RestfulResponse(false,200,"添加用户失败", null);
        return restfulResponse;
    }

    @GetMapping("/delete")
    public RestfulResponse deleteById(@RequestParam("id") Integer id) {
        if(id == null)
            restfulResponse = new RestfulResponse(false, 200, "用户编号不能为空", null);
        else {
            int result = userService.deleteById(id);
            if(result == 1)
                restfulResponse = new RestfulResponse(true,200,"删除成功",null);
            else
                restfulResponse = new RestfulResponse(false,200,"删除失败",null);

        }
        return restfulResponse;
    }

    @PostMapping("/update")
    public RestfulResponse update(@RequestBody User user) {
        if(user.getId() == null) {
            System.out.println("update" + user.getId());
            restfulResponse = new RestfulResponse(false, 200, "用户编号不能为空", null);
        } else {
            System.out.println(user.toString());
            int result = userService.updateUser(user);
            if(result == 1)
                restfulResponse = new RestfulResponse(true,200,"修改用户成功", null);
            else
                restfulResponse = new RestfulResponse(false,200,"修改用户失败", null);
        }
        return restfulResponse;
    }

    @PostMapping("/changePwd")
    public RestfulResponse changePwd(@RequestParam("id") Integer id, @RequestParam("prePwd") String prePassword,
                                     @RequestParam("newPwd") String newPassword) {
        //用户编号为空
        if(id == null || prePassword.equals("") || newPassword.equals("")){
            restfulResponse = new RestfulResponse(false,200,"部分信息为空，修改失败", null);
            return restfulResponse;
        }
        User user = userService.getById(id);
        //用户不存在
        if(user == null) {
            restfulResponse = new RestfulResponse(false,200,"该用户不存在，修改失败", null);
            return restfulResponse;
        } else if(!user.getPassword().equals(prePassword)) {
            restfulResponse = new RestfulResponse(false,200,"两次密码不一致，修改失败", null);
            return restfulResponse;
        }
            user.setPassword(newPassword);
            userService.changePwd(user);
        restfulResponse = new RestfulResponse(true,200,"修改成功", null);
        return restfulResponse;
    }

    @PostMapping("/getById")
    public RestfulResponse getById(@RequestParam("id") Integer id) {
        if(id!=null) {
            User user = userService.getById(id);
            if(user != null)
                restfulResponse = new RestfulResponse(true,200,"查询用户成功", user);
            else
                restfulResponse = new RestfulResponse(false,200,"用户不存在，查询失败", null);
        } else {
            restfulResponse = new RestfulResponse(false,200,"用户编号为空，查询失败", null);
        }
        return restfulResponse;
    }
}
