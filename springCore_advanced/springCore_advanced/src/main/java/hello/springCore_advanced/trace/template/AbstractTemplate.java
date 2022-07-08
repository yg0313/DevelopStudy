package hello.springCore_advanced.trace.template;

import hello.springCore_advanced.trace.TraceStatus;
import hello.springCore_advanced.trace.logtrace.LogTrace;

/**
 * 템플릿 메소드 패턴의 부모 클래스, 템플릿 역할
 * <T> 제네릭을 사용하여, 반환타입 정의
 */
public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message){
        TraceStatus status = null;
        try{
            status = trace.begin(message);

            //로직 호출
            T result = call();

            trace.end(status);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 해당 객체를 생성한 코드에서 메소드 재정의
     */
    protected abstract T call();
}
