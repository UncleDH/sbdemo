package com.dh.demo.controller;

import com.dh.demo.dto.AccessTokenDTO;
import com.dh.demo.dto.GithubUser;
import com.dh.demo.mapper.UserMapper;
import com.dh.demo.model.User;
import com.dh.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")//该注解获取application.properties配置文件中的参数
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.setRedirect.uri}")
    private String redirectUri;

    /*引入要添加要数据库的用户数据类*/
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/sql")
    @ResponseBody//返回字符串 而不是去resources/templates中找页面 与RestController一个效果
    public List<User> getAll(){
        return userMapper.findAll();
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request) {
        System.out.println("code:" + code);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("token: " + accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println("name: " + githubUser.getName());
        if(githubUser.getName() != null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate((System.currentTimeMillis()));
            user.setGmtModified(user.getGmtCreate());
            //userMapper.insert(user);
            //登录成功 写cookie和session
            request.getSession().setAttribute("user", githubUser);//默认配置cookie
            return "redirect:/";
        } else {
            //登录失败
            return "redirect:/";
        }
    }
}
