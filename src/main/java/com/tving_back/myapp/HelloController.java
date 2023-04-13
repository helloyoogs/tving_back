package com.tving_back.myapp;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public List<String> Hello(){
        return Arrays.asList("서버서버", "뷰뷰");
    }
    @GetMapping("/api/join")
    public List<String> Join(){
        return Arrays.asList("회원가입", "회원가입듀듄");
    }

}