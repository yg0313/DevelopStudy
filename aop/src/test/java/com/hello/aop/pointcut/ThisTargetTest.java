package com.hello.aop.pointcut;

import com.hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * spring.aop.proxy-target-class=true (DEFAULT) -> CGLIB .
 * spring.aop.proxy-target-class=false -> JDK 동적 프록시.
 */
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
//@SpringBootTest
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success(){
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Aspect
    @Slf4j
    static class ThisTargetAspect{

        /**
         * 부모타입 허용.
         */
        @Around("this(com.hello.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(com.hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(com.hello.aop.member.MemberServiceImpl)")
        public Object doThisInterfaceImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interfaceImpl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(com.hello.aop.member.MemberServiceImpl)")
        public Object doTargetInterfaceImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interfaceImpl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
