package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class BeanPostProcessorTest {

    @Test
    void postProcessor(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

        B b = applicationContext.getBean("beanA", B.class);
        b.helloB();

        C c = applicationContext.getBean(C.class);
        c.helloC();

        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                ()-> applicationContext.getBean(A.class));
    }

    @Slf4j
    @Configuration
    static class BeanPostProcessorConfig{
        @Bean(name = "beanA")
        public A a(){
            return new A();
        }

        @Bean(name = "beanB")
        public B b(){
            return new B();
        }

        @Bean(name = "beanC")
        public C c(){
            return new C();
        }

        @Bean
        public AToBPostProcessor helloPostProcessor(){
            return new AToBPostProcessor();
        }
    }

    @Slf4j
    static class A{
        public void helloA(){
            log.info("hello A");
        }
    }

    @Slf4j
    static class B{
        public void helloB(){
            log.info("hello B");
        }
    }

    @Slf4j
    static class C{
        public void helloC(){
            log.info("hello C");
        }
    }

    /**
     * 빈 후처리기.
     * 인터페이스 BeanPostProcessor 구현.
     * 스프링 빈으로 등록하면 스프링 컨테이너가 빈 후처리기로 인식하고 동작.
     */
    @Slf4j
    static class AToBPostProcessor implements BeanPostProcessor{
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={}, bean={}", beanName, bean);

            if(bean instanceof A){
                log.info("bean의 타입은 A");
                return new B();
            }
            return bean;
        }
    }
}
