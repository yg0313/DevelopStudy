package hello.springCore_advanced.trace.hellotrace;

import hello.springCore_advanced.trace.TraceId;
import hello.springCore_advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV2 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    /**
     * @info 로그 시작
     */
    public TraceStatus begin(String message){
        TraceId traceId = new TraceId();
        long startTimeMs = System.currentTimeMillis();

        //로그 출력
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    /**
     * V2에서 추가
     * 트랜잭션 ID는 기존과 같이 유지.
     * 깊이를 표현하는 level 하나 증가.
     */
    public TraceStatus beginSync(TraceId beforeTraceId, String message){
        TraceId nextId = beforeTraceId.createNextId();
        long startTimeMs = System.currentTimeMillis();

        //로그 출력
        log.info("[{}] {}{}", nextId.getId(), addSpace(START_PREFIX, nextId.getLevel()), message);
        return new TraceStatus(nextId, startTimeMs, message);
    }

    /**
     * @info 로그 정상 종료
     * TraceStatus를 전달받아, 실행 시간을 계산.
     */
    public void end(TraceStatus status) {
        complete(status, null);
    }

    /**
     * @info 로그 예외 상황 종료
     * TraceStatus, Exception를 전달받아, 실행 시간, 예외 정보 결과 로그 출력.
     */
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }

    /**
     * 레벨에 깊이 모양
     * level=0
     * level=1 |->
     * level=2 |  |->
     * level=2,exception | |<X-
     */
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
