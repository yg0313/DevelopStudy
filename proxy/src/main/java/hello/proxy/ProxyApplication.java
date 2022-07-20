package hello.proxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


//@Import(AppV1Config.class) //AppV1Config.class 를 스프링 빈으로 등록.
@Import({AppV1Config.class, AppV2Config.class}) //AppV2Config.class 를 스프링 빈으로 등록.
@SpringBootApplication(scanBasePackages = "hello.proxy.app") //@Component 기능과 같다. 컴포넌트 스캔을 시작할 위치를 "hello.proxy.app"와 하위 패키지만 지정
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}

}
