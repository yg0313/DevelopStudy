package com.hello.aop.internallcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

    /**
     * ApplicationContext를 통해 CallService2를 주입(지연 조회).
     * but 단지 하나의 기능을 위해 ApplicationContext를 통한 주입은 리소스 소모(?)가 있음.
     */
//    private final ApplicationContext applicationContext;
//
//    public CallServiceV2(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }

    // 스프링 컨테이너에서 조회하는 것을 스프링 빈 생성 시점이 아닌 실제 객체를 사용하는 시점으로 지연.
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    public void external(){
        log.info("call external()");
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal(); //자기 자신을 의존으로 설정하여 프록시 인스턴스 호출
//        internal(); //내부 메소드 호출(this.internal())
    }

    public void internal(){
        log.info("call internal()");
    }
}
