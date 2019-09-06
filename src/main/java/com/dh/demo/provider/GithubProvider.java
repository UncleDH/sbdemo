package com.dh.demo.provider;

import com.alibaba.fastjson.JSON;
import com.dh.demo.dto.AccessTokenDTO;
import com.dh.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component//这个标签使对象不用实例化
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            String[] split = s.split("&");
            String token = split[0].split("=")[1];
            System.out.println(s);
            System.out.println(token);
            return token;
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?accsee_token=" + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            System.out.println(s);
            GithubUser githubUser = JSON.parseObject(s, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }

    }
}
