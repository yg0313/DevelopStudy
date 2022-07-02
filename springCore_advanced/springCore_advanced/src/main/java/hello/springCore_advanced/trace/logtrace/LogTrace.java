package hello.springCore_advanced.trace.logtrace;

import hello.springCore_advanced.trace.TraceStatus;

/**
 * 로그 추적기를 위한 최소한의 기능.
 */
public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
