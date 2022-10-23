package com.hello.aop.internallcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    public void external(){
        log.info("call external()");
        /**
         * 내부 메소드 호출(this.internal())
         * 프록시는 내부호출에서 AOP를 적용할 수 없다.
         */
        internal();
    }

    public void internal(){
        log.info("call internal()");
    }
}
