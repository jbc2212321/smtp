package POPANDSMTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class smtpSend extends Thread{
    public void run(Socket clientSocket) throws IOException, InterruptedException {
        this.rectFromClient(clientSocket);
    }

    public void rectFromClient(Socket clientSocket) throws IOException, InterruptedException {
        int port=25;


        int welcome_state = 0;
        int login_state = 0;
        int source_state = 0;
        int subject_state = 0;
        int des_state = 0;
        String mail_cont = "";
        String mail_subject = "";
        String mail_source = "";
        String mail_des = "";

        String inTemp="";
            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter socketOut = new PrintWriter(clientSocket.getOutputStream());

            while(true) {
                System.out.println("正在接收");
                while(inTemp=="")inTemp = socketIn.readLine();
                String arr[] = inTemp.split(" ");
                System.out.println(arr[0]);
                inTemp="";
             //   System.out.println("onesentence");
                String res = "";
                if (arr[0].equals("EHLO") ) {
                    welcome_state = 1;
                    res = "250-smtp.163.com\t" +
                            "250-PIPELINING\t" +
                            "250-STARTTLS\t" +
                            "250-AUTH LOGIN PLAIN\t" +
                            "250-AUTH=LOGIN\t" +
                            "250-MAILCOMPRESS\t" +
                            "250 8BITMIME!";
                }

                if (arr[0].equals("LOGIN") && arr.length == 3) {
                    String user_name = arr[1];
                    String pass_word = arr[2];
                    res = "+OK";
                    if(true){
                   // if (mysqlJDBC.check_user(user_name, pass_word)) {
                        login_state = 1;
                        res += "+OK user successfully logged on!";
                    } else {
                        res += "-ERR user identify failed!";
                    }
                    System.out.println("11111"+user_name+pass_word);
                }
                if (arr[0].equals("MAILFROM") && arr.length == 2 && login_state == 1) {
                    mail_source = arr[1];
                    source_state = 1;
                    res += "+ok!";
                }
                if (arr[0].equals("RCPTO") && arr.length == 2 && login_state == 1) {
                    mail_des = arr[1];
                    des_state = 1;
                    res = "+ok!";
                }
                if (arr[0].equals("SUBJECT") && login_state == 1) {
                    for (int i=1;i<arr.length;++i){
                        mail_subject+=arr[i]+" ";
                    }
                    subject_state=1;
                    res="+ok";
                }
                if (arr[0].equals("QUIT") && arr.length == 1) {
                    res = "+OK smtp server signing off!";
                    socketOut.println(res);
                    break;
                }
                if(arr[0].equals("data")&&arr.length==1&&des_state==1&&source_state==1&&subject_state==1){
                    res="354 Enter mail,end with '.' on a line by itself!";
                    socketOut.println(res);
                    socketOut.flush();
                    while(true){
                        inTemp=socketIn.readLine();
                        if(inTemp.equals(".")){
                            break;
                        }
                        else{
                            mail_cont+=inTemp+"$$";
                            res="$$";
                            socketOut.println(res);
                            socketOut.flush();
                        }
                    }
                    res="ok!";
                    mail sendMail=new mail();
                    sendMail.createMail(mail_cont,mail_subject, mail_source,mail_des);
                    sendToSever(sendMail);
                    mail_cont="";
                }
                if (res.equals("")) res = "-ERR Unknown command!";
             //   wait(5);
                //for(int i=0;i<100000;++i)
                System.out.println(res);
                    socketOut.println(res);
                    socketOut.flush();
            }
            clientSocket.close();;
    }

    public void sendToSever(mail sendMail) throws IOException {
        String ip="";
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            ip=ip4.getHostAddress();
            System.out.println(ip4.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Socket socket=new Socket(ip,8888);
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        socketOut.print(sendMail.toString());
        socketOut.flush();
        socket.close();
    }


}
