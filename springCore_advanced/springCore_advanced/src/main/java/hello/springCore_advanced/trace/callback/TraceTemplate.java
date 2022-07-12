package hello.springCore_advanced.trace.callback;

import hello.springCore_advanced.trace.TraceStatus;
import hello.springCore_advanced.trace.logtrace.LogTrace;

/**
 * TODO 스프링 빈으로 등록해서 사용해보기
 * 템플릿 콜백패턴 적용.
 */
public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public <T> T execute(String message, TraceCallback<T> callback){
        TraceStatus status = null;
        try{
            status = trace.begin(message);
            //로직 호출
            T result = callback.call();

            trace.end(status);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
