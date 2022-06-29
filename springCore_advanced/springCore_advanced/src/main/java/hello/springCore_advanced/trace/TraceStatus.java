package hello.springCore_advanced.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그를 시작할때의 상태 정보를 보관.
 * 로그를 종료할 때 사용.
 */
@AllArgsConstructor
@Getter
public class TraceStatus {

    private TraceId traceId; //내부에 트랜잭션ID와 level을 가지고 있음.
    private Long startTimeMs; //로그 시작 시간
    private String message; //시작시 사용한 메시지, 로그 종료시에도 메시지를 사용하여 출력

}
