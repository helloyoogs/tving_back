package com.tving_back.myapp.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;


@Service
public class CookieUtil {

    public Cookie createCookie(String cookieName, String value){
        Cookie token = new Cookie(cookieName,value);
        //token.setHttpOnly(true); 이 코드가 있으면 보안상은 좋지만 logout 구현시 서버에서 쿠키 만료나 삭제 처리되도 클라이언트에서 삭제 처리 할 수 없음
        token.setMaxAge((int)JwtUtil.TOKEN_VALIDATION_SECOND);
        token.setPath("/");
        return token;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        final Cookie[] cookies = req.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }
    public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
    }



}
