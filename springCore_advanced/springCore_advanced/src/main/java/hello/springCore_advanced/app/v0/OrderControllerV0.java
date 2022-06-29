package hello.springCore_advanced.app.v0;

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
public class OrderControllerV0 {

    private final OrderServiceV0 orderService;

    @GetMapping("/v0/request")
    public String request(String itemId){
        orderService.orderItem(itemId);
        return "ok";
    }
}
