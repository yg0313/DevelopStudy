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
@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) //JDK 동적 프록시
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    /**
     * JDK 동적 프록시에서는 인터페이스가 아닌, 구체클래스타입 의존관계 주입 불가능.
     */
//    @Autowired
//    MemberServiceImpl memberServiceImpl;

    @Test
    void go(){
        log.info("memberService class={}", memberService.getClass());
//        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
//        memberServiceImpl.hello("hello");
    }
}
