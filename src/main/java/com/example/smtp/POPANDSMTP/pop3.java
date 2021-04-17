package POPANDSMTP;

import java.io.*;
import java.net.Socket;

public class pop3 extends Thread{
    public void run(Socket clientSocket) throws IOException {
        this.clientPop(clientSocket);
    }
    public void clientPop(Socket clientSocket) throws IOException {
        int login_state = 0;
        String user_name = "";
        String pass_word = "";
        String readline = null,inTemp = null;


            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter socketOut = new PrintWriter(clientSocket.getOutputStream());

            while(true){

                inTemp = socketIn.readLine();
                String arr[]=inTemp.split(" ");

                    String res="";
                    if(arr[0].equals("USER")&&arr.length==2){
                        user_name=arr[1];
                        res="+OK!userLogin";

                    }
                    if(arr[0].equals("PASS")&&arr.length==2){
                        pass_word=arr[1];
                        res="+OKpasswordLogin";
                        if (true) {
                            //  if(mysqlJDBC.check_user(user_name,pass_word)){
                            login_state = 1;
                           res+="+OK user successfully logged on!";
                       }
                       else {
                           res+="-ERR user identify failed!";
                       }
                    }

                    if(arr[0].equals("LIST")&&arr.length==1&&login_state==1){
                        res="+ok!LIST";
                        //mysqlJDBC.listMail();
                        //将查询数据逐条返回

                    }
                if(arr[0].equals("RETR")&&arr.length==2&&login_state==1) {
                    res="+ok!RETR";
                    // mysqlJDBC.getMail();
                    //返回某封邮件

                }

                    if(arr[0].equals("DELE")&&arr.length==2&&login_state==1) {
                        res="+ok!DELE";
                       // mysqlJDBC.deleteMail();


                    }
                    if(arr[0].equals("QUIT")&&arr.length==1){
                        res= "+OK POP3 server signing off!";
                        break;
                    }
                    if(res.equals(""))res="-ERR Unknown command!";
                    socketOut.println(res);
                    socketOut.flush();
            }
            clientSocket.close();
    }
}
