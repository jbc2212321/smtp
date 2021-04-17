package com.example.smtp.mapper;

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

    void addUser(@Param("username") String username, @Param("password") String password,@Param("usermail") String usermail); //添加用户
}
