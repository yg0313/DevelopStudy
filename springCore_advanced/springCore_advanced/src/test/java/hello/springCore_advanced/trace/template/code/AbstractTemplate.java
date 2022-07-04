package hello.springCore_advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 템플릿 메소드.
 * 템플릿을 사용하는 방식.
 * 템플릿이라는 틀에 변하지 않는 부분을 몰아둔 후, 변하는 부분을 별도로 호출하여 해결.
 */
@Slf4j
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        call(); //상속
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    protected abstract void call();
}
