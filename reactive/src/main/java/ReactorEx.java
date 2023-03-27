import reactor.core.publisher.Flux;

/**
 * projectreactor.io/core
 */
public class ReactorEx {
    public static void main(String[] args) {
        Flux.<Integer>create(e -> {
                    e.next(1);
                    e.next(2);
                    e.next(3);
                    e.complete();
                })
                .log() //operator 사이의 과정을 보여줌.
                .map(s -> s * 10)
                .reduce(0, (a, b) -> a + b)
                .log()
                .subscribe(s -> System.out.println(s));
    }
}
