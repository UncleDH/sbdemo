package com.dh.demo.controller;

import com.dh.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @ResponseBody//返回值字符串 而不是去找页面
    @PostMapping("/test")
    public User test() {
        User u1 = new User();
        u1.setName("odd");
        u1.setAccountId("123");
        u1.setId(123);
        return u1;
    }
}
