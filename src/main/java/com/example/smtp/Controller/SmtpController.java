package com.example.smtp.Controller;

import POPANDSMTP.smtpSend;
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
public class SmtpController {
    @Autowired
    private ManagerMapper managerMapper;

    @CrossOrigin
    @RequestMapping(value = "/getPSMT", method = RequestMethod.POST, consumes = "application/json")
    public void pop(@RequestBody String jsonParamStr) throws IOException, InterruptedException {
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        String currentUser = jsonObject.getString("currentUser");
        String password = jsonObject.getString("password");
        int port = managerMapper.getSMTP();  //默认25 ，从数据库中取


        ServerSocket serSo = new ServerSocket(port);
        while (true) {
            System.out.println(currentUser);
            System.out.println(password);
            Socket server = serSo.accept();
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintWriter socketOut = new PrintWriter(server.getOutputStream());
            String inTemp = socketIn.readLine();
            POPANDSMTP.smtpSend smtp = new smtpSend();
            // pop=new POPANDSMTP.pop3();
            socketOut.println("+ok");
            socketOut.flush();
            smtp.rectFromClient(server);
            //pop.clientPop(server);
        }
    }

    //查看端口
    @CrossOrigin
    @GetMapping("/getSMTP")
    public int getSMTP() {
        return managerMapper.getSMTP();
    }

    //修改端口
    @CrossOrigin
    @RequestMapping(value = "/updateSMTP", method = RequestMethod.POST, consumes = "application/json")
    public void updateSMTP(@RequestBody String jsonParamStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonParamStr);
        int smtp = jsonObject.getInteger("smtp");
        managerMapper.updateSMTP(smtp);
    }
}
