package com.hello.aop.proxyvs;

import com.hello.aop.member.MemberService;
import com.hello.aop.member.MemberServiceImpl;
import com.hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) //JDK 동적 프록시
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) //CGLIB 프록시
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    /**
     * CGLIB 프록시는 구체클래스를 기반으로 만들어지므로,
     * 구체클래스가 상속한 인터페이스 상속도 가능하다.
     */
    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go(){
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
