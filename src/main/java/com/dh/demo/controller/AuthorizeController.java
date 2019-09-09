package com.dh.demo.controller;

import com.dh.demo.dto.AccessTokenDTO;
import com.dh.demo.dto.GithubUser;
import com.dh.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println("name: " + user.getName());
        if(user.getName() != null) {
            //登录成功 写cookie和session
            request.getSession().setAttribute("user", user);//默认配置cookie
            return "redirect:/";
        } else {
            //登录失败
            return "redirect:/";
        }
    }
}
