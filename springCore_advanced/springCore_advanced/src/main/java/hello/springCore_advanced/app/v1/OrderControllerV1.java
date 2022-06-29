package hello.springCore_advanced.app.v1;

import hello.springCore_advanced.trace.TraceStatus;
import hello.springCore_advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그 추적기 - 요구사항분석.
 * 모든 public 메소드의 호출과 응답 정보를 로그로 출력.
 * 메소드 호출에 걸린 시간 체크.
 * 정상흐름과 예외흐름 구분.
 * 메소드 호출의 깊이 표현.
 */
@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져주어야 한다.
        }
    }
}
