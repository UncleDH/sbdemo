package com.dh.demo.controller;

import com.dh.demo.mapper.QuestionMapper;
import com.dh.demo.mapper.UserMapper;
import com.dh.demo.model.Question;
import com.dh.demo.model.User;
import com.dh.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;//map service 一起出现可以精简留一个

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model){
        Question question = questionMapper.selectByPrimaryKey(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("id") Integer id,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            Model model) {
        /*User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {//防止cookie为空
            for(Cookie cookie : cookies) {
                if("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    System.out.println("user" + user);
                    if(user == null) {
                        model.addAttribute("error", "用户未登录");
                        return "publish";
                    } else {
                        Question question = new Question();
                        question.setCreator(user.getId());
                        question.setTitle(title);
                        question.setDescription(description);
                        question.setTag(tag);
                        question.setGmtCreate(System.currentTimeMillis());
                        question.setGmtModified(question.getGmtCreate());
                        questionMapper.create(question);
                        return "redirect:/";
                    }
                }
            }
        }*/
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        } else {
            Question question = new Question();
            question.setCreator(user.getId());
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            //question.setGmtCreate(System.currentTimeMillis());
            //question.setGmtModified(question.getGmtCreate());
            question.setId(id);

            questionService.createOrUpdate(question);
            //questionMapper.create(question);
            return "redirect:/";
        }
    }
}
