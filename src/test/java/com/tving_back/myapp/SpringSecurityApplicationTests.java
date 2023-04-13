package com.tving_back.myapp;

import com.tving_back.myapp.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.tving_back.myapp.model.Member;
import com.tving_back.myapp.service.JwtUtil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringSecurityApplicationTests {

    @Autowired
    private MemberRepository memberRepository;


    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional()
    void testDB(){
        Member member = new Member();
        member.setUsername("user4");
        member.setPassword("a1234");
        member.setName("김동근");
        member.setEmail("ehdrms2034@naver.com");
        member.setAddress("부산광역시 부산진구 땡땡로 땡땡길 101동 1001호");
        memberRepository.save(member);
        Member user1 = memberRepository.findByUsername("user4");
        assertThat(user1.getName(),is("김동근"));
        memberRepository.delete(user1);
    }

    @Test
    void test(){

        String username = jwtUtil.getUsername("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImVoZHJtczIwMzQiLCJpYXQiOjE1ODQ2MTkzMTMsImV4cCI6MTU4NDYyMTExM30.llLcxtXVoI3xBROLrrru1IYXSAADywNwtb9IBMhXzfQ");
        System.out.println(username);
    }
}