package com.dh.demo.mapper;

import com.dh.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name, accountId, token, gmtCreate, gmtModified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from user")
    List<User> findAll();

    @Select("select * from user where token = #{token}")
    User findByToken(String token);
}
