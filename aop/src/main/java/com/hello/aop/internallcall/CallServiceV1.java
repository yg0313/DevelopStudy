package com.hello.aop.internallcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 setter={}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    public void external(){
        log.info("call external()");
        callServiceV1.internal(); //자기 자신을 의존으로 설정하여 프록시 인스턴스 호출
//        internal(); //내부 메소드 호출(this.internal())
    }

    public void internal(){
        log.info("call internal()");
    }
}
