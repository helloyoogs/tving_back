package com.tving_back.myapp.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tving_back.myapp.repository.MemberRepository;
import com.tving_back.myapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.tving_back.myapp.model.Member;
import com.tving_back.myapp.model.Response;
import com.tving_back.myapp.model.Request.RequestChangePassword1;
import com.tving_back.myapp.model.Request.RequestChangePassword2;
import com.tving_back.myapp.model.Request.RequestLoginUser;
import com.tving_back.myapp.model.Request.RequestVerifyEmail;
import com.tving_back.myapp.service.CookieUtil;
import com.tving_back.myapp.service.JwtUtil;
import com.tving_back.myapp.service.RedisUtil;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class MemberController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;
    @Autowired
    private CookieUtil cookieUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MemberRepository memberRepository;

    //사용자 조회
    @GetMapping("/userData")
    public Member getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Member> allMember = (List<Member>) memberRepository.findAll();

        if (allMember == null) {
            return null;
        }

        Optional<Member> memberOptional = allMember.stream()
                .filter(state ->
                        userId.equals(state.getUsername())
                )
                .findFirst();

        return memberOptional.orElse(null);
    }

    //회원가입
    @PostMapping("/signup")
    public Response signUpUser(@RequestBody Member member) {
        try {
            authService.signUpUser(member);
            return new Response("success", "회원가입을 성공적으로 완료했습니다.", null);
        } catch (Exception e) {
            return new Response("error", "회원가입을 하는 도중 오류가 발생했습니다.", null);
        }
    }

    //로그인
    @PostMapping("/login")
    public Response login(@RequestBody RequestLoginUser user,
                          HttpServletRequest req,
                          HttpServletResponse res) {
        try {
            final Member member = authService.loginUser(user.getUsername(), user.getPassword());
            final String token = jwtUtil.generateToken(member);
            final String refreshJwt = jwtUtil.generateRefreshToken(member);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, member.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);
            return new Response("success", "로그인에 성공했습니다.", token);
        } catch (Exception e) {
            return new Response("error", "로그인에 실패했습니다.", e.getMessage());
        }
    }

    //로그아웃
    @PostMapping("/logout")
    public Response logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie refreshToken = cookieUtil.getCookie(request, JwtUtil.REFRESH_TOKEN_NAME);
            String refreshJwt = refreshToken.getValue();
            redisUtil.deleteData(refreshJwt);
            cookieUtil.deleteCookie(request, response, JwtUtil.REFRESH_TOKEN_NAME);
            cookieUtil.deleteCookie(request, response, JwtUtil.ACCESS_TOKEN_NAME);
            return new Response("success", "로그아웃 되었습니다.", null);
        } catch (Exception e) {
            return new Response("error", "로그아웃에 실패했습니다.", e.getMessage());
        }
    }

    @PostMapping("/verify")
    public Response verify(@RequestBody RequestVerifyEmail requestVerifyEmail, HttpServletRequest req, HttpServletResponse res) {
        Response response;
        try {
            Member member = authService.findByUsername(requestVerifyEmail.getUsername());
            authService.sendVerificationMail(member);
            response = new Response("success", "성공적으로 인증메일을 보냈습니다.", null);
        } catch (Exception exception) {
            response = new Response("error", "인증메일을 보내는데 문제가 발생했습니다.", exception);
        }
        return response;
    }

    @GetMapping("/verify/{key}")
    public Response getVerify(@PathVariable String key) {
        Response response;
        try {
            authService.verifyEmail(key);
            response = new Response("success", "성공적으로 인증메일을 확인했습니다.", null);

        } catch (Exception e) {
            response = new Response("error", "인증메일을 확인하는데 실패했습니다.", null);
        }
        return response;
    }

    @GetMapping("/password/{key}")
    public Response isPasswordUUIdValidate(@PathVariable String key) {
        Response response;
        try {
            if (authService.isPasswordUuidValidate(key))
                response = new Response("success", "정상적인 접근입니다.", null);
            else
                response = new Response("error", "유효하지 않은 Key값입니다.", null);
        } catch (Exception e) {
            response = new Response("error", "유효하지 않은 key값입니다.", null);
        }
        return response;
    }

    @PostMapping("/password")
    public Response requestChangePassword(@RequestBody RequestChangePassword1 requestChangePassword1) {
        Response response;
        try {
            Member member = authService.findByUsername(requestChangePassword1.getUsername());
            if (!member.getEmail().equals(requestChangePassword1.getEmail())) throw new NoSuchFieldException("");
            authService.requestChangePassword(member);
            response = new Response("success", "성공적으로 사용자의 비밀번호 변경요청을 수행했습니다.", null);
        } catch (NoSuchFieldException e) {
            response = new Response("error", "사용자 정보를 조회할 수 없습니다.", null);
        } catch (Exception e) {
            response = new Response("error", "비밀번호 변경 요청을 할 수 없습니다.", null);
        }
        return response;
    }

    @PutMapping("/password")
    public Response changePassword(@RequestBody RequestChangePassword2 requestChangePassword2) {
        Response response;
        try{
            Member member = authService.findByUsername(requestChangePassword2.getUsername());
            authService.changePassword(member,requestChangePassword2.getPassword());
            response = new Response("success","성공적으로 사용자의 비밀번호를 변경했습니다.",null);
        }catch(Exception e){
            response = new Response("error","사용자의 비밀번호를 변경할 수 없었습니다.",null);
        }
        return response;

    }

}