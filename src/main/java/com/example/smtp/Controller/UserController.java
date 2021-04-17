package com.example.smtp.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.smtp.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    //用户登录
    @CrossOrigin
    @RequestMapping(value = "/checkUser", method = RequestMethod.POST, consumes = "application/json")
    public Boolean checkUser(@RequestBody String jsonParamStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        String userName = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String identity = jsonObject.getString("identity");
        return true;
    }

    //添加用户
    @CrossOrigin
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = "application/json")
    public Boolean addUser(@RequestBody String jsonParamStr) {
        boolean isRegister = false;
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        String userName = jsonObject.getString("username");
        String passWord = jsonObject.getString("password");
        String email = userName+"@qq.com";
        userMapper.addUser(userName,passWord,email);
        return true;
    }

    //查看用户列表
    @CrossOrigin
    @GetMapping("/getUserList")
    public List<Map<Object, Object>> getUserList() {
        return userMapper.getUserList();
    }

}
