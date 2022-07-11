package hello.springCore_advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 전략을 파라미터로 전달 받는 방식.
 */
@Slf4j
public class ContextV2 {

    /**
     * 전략을 필드로 갖지않고, execute()가 호출될 때 마다 항상 파라미터로 전달 받음.
     */
    public void execute(Strategy strategy){
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        strategy.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
