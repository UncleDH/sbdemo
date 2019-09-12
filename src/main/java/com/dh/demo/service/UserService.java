package com.dh.demo.service;

import com.dh.demo.mapper.UserMapper;
import com.dh.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser == null) {
            //插入新的用户
            user.setGmtCreate((System.currentTimeMillis()));
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新用户
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            userMapper.update(dbUser);
        }
    }
}
