package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping//스프링은 @Controller 또는 @RequestMapping 이 있어야 스프링 컨트롤러로 인식.
@ResponseBody //HTTP 메시지 컨버터를 사용해서 응답. 인터페이스에서 사용해도 된다.
public interface OrderControllerV1 {

    /**
     * LogTrace 적용 대상.
     */
    @GetMapping("/v1/request")
    String request(@RequestParam("itemId")String itemId);

    /**
     * LogTrace 미적용.
     */
    @GetMapping("/v1/no-log")
    String noLog();
}
