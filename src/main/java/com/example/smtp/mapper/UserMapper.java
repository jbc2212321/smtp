package com.example.smtp.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 78240
 */
@Mapper
@Repository
public interface UserMapper {
    @Select("select * from user where identy =0")
    List<Map<Object,Object>> getUserList();  //获取用户列表

    //添加用户
    void addUser(@Param("username") String username, @Param("password") String password,@Param("usermail") String usermail); 
    
    //验证用户
    @Select(" SELECT DISTINCT IF(EXISTS(SELECT 1 FROM user where username=#{username} and password=#{password}),1,0) AS a FROM user")
    int checkUser(@Param("username") String username, @Param("password") String password);

    //验证用户
    @Select(" SELECT DISTINCT IF(EXISTS(SELECT 1 FROM user where username=#{username} and password=#{password} and identy=1),1,0) AS a FROM user")
    int checkAdmin(@Param("username") String username, @Param("password") String password);

    //删除用户
    @Delete("delete from user where username=#{username}")
    void deleteUser(@Param("username") String username);

    //返回当前用户的所有邮件信息
    @Select("select * from email where email_to=#{username}")
    List<Map<Object, Object>> getCurrentUserEmail(@Param("username")String userName);

    //通过编号获取当前邮件的具体信息
    @Select("select * from email where email_no=#{number}")
    Map<Object, Object> getEmailDetail(@Param("number")String number);
}
