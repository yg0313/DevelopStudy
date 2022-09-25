package com.hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
public class AspectV1 {

    /**
     * @Around -> 포인트컷.
     * execution(* hello.aop.order..*(..)) -> hello.aop.order 패키지와 해당 하위 패키지를 지정.
     * doLog() -> 어드바이스.
     */
    @Around("execution(* com.hello.aop.order..*(..))")  // 포인트컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처

        return joinPoint.proceed();
    }
}
