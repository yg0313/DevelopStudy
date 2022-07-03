package hello.springCore_advanced.config;

import hello.springCore_advanced.trace.logtrace.LogTrace;
import hello.springCore_advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace(){
//        return new FieldLogTrace();
        return new ThreadLocalLogTrace();
    }
}
