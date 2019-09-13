package com.dh.demo.controller;

import com.dh.demo.dto.QuestionDTO;
import com.dh.demo.mapper.UserMapper;
import com.dh.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;



    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        /*Cookie[] cookies = request.getCookies();
        if(cookies != null) {//防止cookie为空
            for(Cookie cookie : cookies) {
                if("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }*/

        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions", questionList);
        return "index";
    }
}
