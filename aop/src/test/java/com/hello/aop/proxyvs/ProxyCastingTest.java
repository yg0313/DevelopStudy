package com.hello.aop.proxyvs;

import com.hello.aop.member.MemberService;
import com.hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); //JDK 동적 프록시 설정

        //프록시를 인터페이스 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        /**
         * JDK 동적 프록시를 구체 클래스로 캐스팅 시도 실패, ClassCastException 발생.
         * JDK 동적 프록시는 인터페이스를 구현한것이지, 구체 클래스를 알수없기때문에 캐스팅되지 않음.
         */
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //CGLIB 동적 프록시 설정

        //프록시를 인터페이스 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        log.info("proxy class={}", memberServiceProxy.getClass());
        /**
         * CGLIB를 구체 클래스로 캐스팅 시도 성공
         * CGLIB는 인터페이스 구체 클래스를 기반으로 생성.
         */
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}
