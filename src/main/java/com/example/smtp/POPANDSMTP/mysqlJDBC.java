package POPANDSMTP;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.ArrayList;
public class mysqlJDBC {
    private static Connection Conn; // 数据库连接对象

    // 数据库连接地址
    private static String URL = "jdbc:mysql://localhost:3306/" +
            "smtp?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";

    // 数据库的用户名
    private static String UserName = "root";
    // 数据库的密码
    private static String Password = "111111";


    public static Connection getConnection() {
        //连接数据库
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        try {

            //通过DriverManager类的getConenction方法指定三个参数,连接数据库
            Conn = DriverManager.getConnection(URL, UserName, Password);
            //       System.out.println("连接数据库成功!!!");
            //返回连接对象
            return Conn;

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    public static boolean check_user(String user,String password){
        Connection conn = mysqlJDBC.getConnection();
        try {
            if (conn == null)
                return false;
            String sql="select 1 from user where username=? and password=?;";

            PreparedStatement stt = conn.prepareStatement(sql);

            stt.setString(1,user);
            stt.setString(2,password);
            ResultSet set=null;
            set=stt.executeQuery();
          //  boolean exist=;
            if(set.next())return true;
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
            } catch (Exception e2) {}

        }
        return true;
    }

    public static boolean deleteMail(int mailID){
        Connection conn = mysqlJDBC.getConnection();
        try {
            if (conn == null)
                return false;
            String sql="select * from email where email_no =?;";



            PreparedStatement stt = conn.prepareStatement(sql);

          //  stt.setString(0,user);
            stt.setInt(1,mailID);

            ResultSet set=null;
            set=stt.executeQuery();
            if(!set.next())return false;
            sql="delete from email where email_no=?;";
            stt = conn.prepareStatement(sql);

            //  stt.setString(0,user);
            stt.setInt(1,mailID);
            System.out.println(stt.toString());
            stt.execute();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
            } catch (Exception e2) {}

        }
        return true;
    }

    public static ArrayList<POPANDSMTP.mail> listMail(String user){
        Connection conn = mysqlJDBC.getConnection();
        try {
            if (conn == null)
                return null;
            String sql="select * from email where email_to=?;";

            PreparedStatement stt = conn.prepareStatement(sql);

            stt.setString(1,user);

            ResultSet set=null;
            set=stt.executeQuery();
            ArrayList<POPANDSMTP.mail> arrL=new ArrayList<>();
            while(set.next()){
                POPANDSMTP.mail Mail=new POPANDSMTP.mail();
                Mail.mail_no=set.getInt(1);
                Mail.mail_from=set.getString(2);
                Mail.mail_des=set.getString(3);
                Mail.mail_subject=set.getString(4);
                arrL.add(Mail);
            }
            return arrL;
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
            } catch (Exception e2) {}

        }
        return null;
    }

    public static String viewMail(int mailNo){
        Connection conn = mysqlJDBC.getConnection();
        try {
            if (conn == null)
                return null;
            String sql="select * from email where email_no=?;";

            PreparedStatement stt = conn.prepareStatement(sql);

            stt.setInt(1,mailNo);

            ResultSet set=null;
            set=stt.executeQuery();
            while (set.next()){
                String cont=set.getString(6);
                return cont;
            }
            return "no this letter!!";
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
            } catch (Exception e2) {}

        }
        return null;
    }


    public static void AddLog(int id,String type,String time,String actionType){
        Connection conn = mysqlJDBC.getConnection();
        try {
            if (conn == null)
                return;
            String sql="insert into log(userNo,type,action,time)  VALUES(?,?,?,?);";
            PreparedStatement stt = conn.prepareStatement(sql);
            //执行sql语句
            //    System.out.println(time);
            stt.setInt(1,id);
            stt.setString(2,type);
            stt.setString(3,actionType);
            stt.setTimestamp(4, Timestamp.valueOf(time));

            stt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
            } catch (Exception e2) {}

        }
    }
}
