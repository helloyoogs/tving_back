package com.tving_back.myapp.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tving_back.myapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tving_back.myapp.model.Member;
import com.tving_back.myapp.model.Response;
import com.tving_back.myapp.model.Request.RequestSocialData;
import com.tving_back.myapp.service.CookieUtil;
import com.tving_back.myapp.service.JwtUtil;
import com.tving_back.myapp.service.RedisUtil;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/signup/naver")
    public Response signUpNaverUser(@RequestBody RequestSocialData socialData){
        Response response;
        try{
            authService.signUpSocialUser(socialData);
            response = new Response("success","성공적으로 회원가입을 완료했습닌다.",null);
        }catch(Exception e){
            response = new Response("error","회원가입 실패",e.getMessage());
        }
        return response;
    }

    @GetMapping("/login/naver")
    public Response loginNaverUser(@RequestBody RequestSocialData socialData, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try{
            final Member member = authService.loginSocialUser(socialData.getId(),socialData.getType());
            final String token = jwtUtil.generateToken(member);
            final String refreshJwt = jwtUtil.generateRefreshToken(member);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, member.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
            response = new Response("success", "로그인에 성공했습니다.", token);
        }catch(Exception e){
            response =  new Response("error", "로그인에 실패했습니다.", e.getMessage());
        }
        return response;
    }

}