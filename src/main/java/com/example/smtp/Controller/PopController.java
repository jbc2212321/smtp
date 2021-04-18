package com.example.smtp.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.smtp.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@RestController
public class PopController {
    @Autowired
    private ManagerMapper managerMapper;

    //pop
    @CrossOrigin
    @RequestMapping(value = "/getPOP", method = RequestMethod.POST, consumes = "application/json")
    public void pop(@RequestBody String jsonParamStr) throws IOException {
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        String currentUser = jsonObject.getString("currentUser");
        String password=jsonObject.getString("password");
        int port = managerMapper.getPOP();  //默认110 ，从数据库中取


        ServerSocket serSo=new ServerSocket(port);
        while (true){
            System.out.println(currentUser);
            System.out.println(password);
            Socket server=serSo.accept();
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintWriter socketOut = new PrintWriter(server.getOutputStream());
            String inTemp=socketIn.readLine();
                POPANDSMTP.pop3 pop=new POPANDSMTP.pop3();
                socketOut.println("+ok");
                socketOut.flush();
                pop.clientPop(server);
            }
    }

    //查看端口
    @CrossOrigin
    @GetMapping("/getPOP")
    public int getPOP() {
        return managerMapper.getPOP();
    }

    @CrossOrigin
    @RequestMapping(value = "/updatePOP", method = RequestMethod.POST, consumes = "application/json")
    public void updatePOP(@RequestBody String jsonParamStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        int pop = jsonObject.getInteger("pop");
        managerMapper.updatePOP(pop);
    }
}
