package com.dh.demo.controller;

import com.dh.demo.dto.AccessTokenDTO;
import com.dh.demo.dto.GithubUser;
import com.dh.demo.mapper.UserMapper;
import com.dh.demo.model.User;
import com.dh.demo.provider.GithubProvider;
import com.dh.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private UserService userService;

/*    @GetMapping("/sql")
    @ResponseBody//返回字符串 而不是去resources/templates中找页面 与RestController一个效果
    public List<User> getAll(){
        return userMapper.findAll();
    }*/

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
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
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            //user.setGmtCreate((System.currentTimeMillis()));
            //user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            //userMapper.insert(user);//这里没有验证用户在数据库中是否存在就又创建一个新用户
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            //登录成功 写cookie和session
            //request.getSession().setAttribute("user", githubUser);//默认配置cookie
            return "redirect:/";
        } else {
            //登录失败
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
