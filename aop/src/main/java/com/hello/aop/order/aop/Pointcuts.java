package com.hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 공용으로 사용하기 위한 포인트컷 관리.
 * 외부에서 호출시 포인트컷의 접근제어자는 public 설정.
 */
public class Pointcuts {

    /**
     * com.hello.aop.order 패키지와 하위 패키지.
     */
    @Pointcut("execution(* com.hello.aop.order..*(..))")
    public void allOrder() {
    }  // pointcut signature -> 메소드 이름과 파라미터를 합친것.

    /**
     * 클래스 이름 패턴이 *Service
     */
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {
    }

    /**
     * allOrder && allService
     */
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
