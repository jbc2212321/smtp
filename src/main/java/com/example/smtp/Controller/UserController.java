package com.example.smtp.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.smtp.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    //用户登录
    @CrossOrigin
    @RequestMapping(value = "/checkUser", method = RequestMethod.POST, consumes = "application/json")
    public String checkUser(@RequestBody String jsonParamStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        String userName = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        int check = userMapper.checkUser(userName, password);
        if (check==1){
            return "登录成功";
        }
        return "登录失败";

    }

    //管理员登录
    @CrossOrigin
    @RequestMapping(value = "/checkAdmin", method = RequestMethod.POST, consumes = "application/json")
    public Boolean checkAdmin(@RequestBody String jsonParamStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        String userName = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        int check = userMapper.checkAdmin(userName, password);
        System.out.println(check);
        return check == 1;

    }

    //添加用户
    @CrossOrigin
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = "application/json")
    public String addUser(@RequestBody String jsonParamStr) {
        boolean isRegister = false;
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        String userName = jsonObject.getString("username");
        String passWord = jsonObject.getString("password");
        String email = userName + "@dyz.com";
        userMapper.addUser(userName, passWord, email);
        return "注册成功";
    }

    //查看用户列表
    @CrossOrigin
    @GetMapping("/getUserList")
    public List<Map<Object, Object>> getUserList() {
        return userMapper.getUserList();
    }

    //删除用户
    @CrossOrigin
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST, consumes = "application/json")
    public Boolean deleteUser(@RequestBody String jsonParamStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        String userName = jsonObject.getString("username");
        userMapper.deleteUser(userName);
        return true;
    }

    //返回当前用户的所有邮件信息
    @CrossOrigin
    @RequestMapping(value = "/getCurrentUserEmail", method = RequestMethod.POST, consumes = "application/json")
    public List<Map<Object, Object>>getCurrentUserEmail(@RequestBody String jsonParamStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        String userName = jsonObject.getString("username");
        return userMapper.getCurrentUserEmail(userName);
    }

    //通过编号获取当前邮件的具体信息
    @CrossOrigin
    @RequestMapping(value = "/getEmailDetail", method = RequestMethod.POST, consumes = "application/json")
    public Map<Object, Object>getEmailDetail(@RequestBody String jsonParamStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        String number = jsonObject.getString("number");
        return userMapper.getEmailDetail(number);
    }
}

