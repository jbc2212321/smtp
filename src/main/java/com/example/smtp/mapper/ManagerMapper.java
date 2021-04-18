package com.example.smtp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ManagerMapper {
    //查看端口
    @Select("select pop3 from port")
    int getPOP();

    @Select("select smtp from port")
    int getSMTP();

    //修改端口
    @Update("update port set smtp=#{smtp}")
    void updateSMTP(@Param("smtp") int smtp);

    @Update("update port set pop3=#{pop}")
    void updatePOP(@Param("pop") int pop);
}
