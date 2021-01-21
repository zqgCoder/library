package com.example.busonline.controller;

import com.example.busonline.RestfulResponse;
import com.example.busonline.entity.Admin;
import com.example.busonline.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    private RestfulResponse restfulResponse;

    /*
     *Resolved [org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class com.example.busonline.RestfulResponse]
     * 原因是json没有转换，RestfulResponse没有添加Data注解。
     */
    @GetMapping("/findAll")
    public RestfulResponse findAll(){
        List<Admin> list = adminService.findAll();
        if(list != null) {
            restfulResponse = new RestfulResponse(true,200,"请求成功", list);
        } else {
            restfulResponse = new RestfulResponse(true,200,"请求失败，用户数据不存在", null);
        }
        return restfulResponse;
    }

    @PostMapping("/login")
    public RestfulResponse login(@RequestBody Map<String, String> f) {
//        @RequestParam("id") Integer id, @RequestParam("password") String password
        String id = f.get("id");
        String password = f.get("password");
        System.out.println(id + "----------" + password);
        Admin admin = adminService.findByNameAndPwd(Integer.valueOf(id), password);
        if(admin != null){
            System.out.println(admin.toString());
            Map<String, String> map = new HashMap<>();
            map.put("role", "admin");
            map.put("userid",admin.getId().toString());
            restfulResponse = new RestfulResponse(true, 200, "登录成功", map);
        }
        else
            restfulResponse = new RestfulResponse(false, 200, "用户名或密码错误，登录失败", null);

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
        Admin admin = adminService.getById(id);
        //用户不存在
        if(admin == null) {
            restfulResponse = new RestfulResponse(false,200,"该用户不存在，修改失败", null);
            return restfulResponse;
        } else if(!admin.getPassword().equals(prePassword)) {
            restfulResponse = new RestfulResponse(false,200,"两次密码不一致，修改失败", null);
            return restfulResponse;
        }
        admin.setPassword(newPassword);
        adminService.changePwd(admin);
        restfulResponse = new RestfulResponse(true,200,"修改成功", null);
        return restfulResponse;
    }
}
