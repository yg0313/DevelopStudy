package hello.springCore_advanced.app.v4;

import hello.springCore_advanced.trace.TraceStatus;
import hello.springCore_advanced.trace.logtrace.LogTrace;
import hello.springCore_advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId){
        //제네릭에서 반환타입이 없으면 Void 타입사용
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");
    }
}
