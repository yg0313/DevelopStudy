package com.hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    /**
     * com.hello.aop.order 패키지와 하위 패키지.
     */
    @Pointcut("execution(* com.hello.aop.order..*(..))")
    private void allaOrder(){}  // pointcut signature -> 메소드 이름과 파라미터를 합친것.

    @Around("allaOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처

        return joinPoint.proceed();
    }
}
