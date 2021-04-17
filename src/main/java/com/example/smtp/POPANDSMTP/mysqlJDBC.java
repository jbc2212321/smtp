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
            String sql="select * from user where user=? and password=?;";

            PreparedStatement stt = conn.prepareStatement(sql);

            stt.setString(1,user);
            stt.setString(2,password);
            ResultSet set=null;
            set=stt.executeQuery();
            if(set!=null)return true;
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
            String sql="select * from mail where mail_no =?;";







            PreparedStatement stt = conn.prepareStatement(sql);

          //  stt.setString(0,user);
            stt.setInt(1,mailID);
            ResultSet set=null;
            set=stt.executeQuery();
            if(set!=null)return false;
            sql="delete from mail where mail_no =?;";
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

    public static ArrayList<mail> listMail(String user){
        Connection conn = mysqlJDBC.getConnection();
        try {
            if (conn == null)
                return null;
            String sql="select * from email where email_to=?;";







            PreparedStatement stt = conn.prepareStatement(sql);

            stt.setString(1,user);

            ResultSet set=null;
            set=stt.executeQuery();

            if(set!=null)return null;
            return null;
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

    public static mail viewMail(String user){
        Connection conn = mysqlJDBC.getConnection();
        try {
            if (conn == null)
                return null;
            String sql="select * from email where email_no=?;";



            PreparedStatement stt = conn.prepareStatement(sql);

            stt.setString(1,user);

            ResultSet set=null;
            set=stt.executeQuery();

            if(set!=null)return null;
            return null;
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
