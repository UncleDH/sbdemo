package com.dh.demo.controller;

import com.dh.demo.dto.QuestionDTO;
import com.dh.demo.mapper.UserMapper;
import com.dh.demo.model.User;
import com.dh.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request, Model model) {
        /*User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {//防止cookie为空
            for(Cookie cookie : cookies) {
                if("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }*/
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最近回复");
        }

        List<QuestionDTO> questionList = questionService.list(user.getId());
        model.addAttribute("questions", questionList);
        return "profile";
    }
}
