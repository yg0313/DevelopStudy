## proxy - 프록시 패턴과 데코레이터 패턴  
- proxy 
  - 프록시 패턴
    - 체인이 가능함  --> 역할 위임이 가능  
  - 데코레이터 패턴 : 원래 서버가 제공하는 기능에 더해서 부가 기능을 수행.  
  ex) 요청 값이나, 응답 값을 중간에 변형.  
  ex) 실행 시간을 측정해서 추가 로그를 남긴다.
    - 데코레이터패턴에서의 체인 --> 부가기능을 계속해서 추가.  

## 리플렉션  

```java
// 리플렉션 적용 전  

Hello target = new Hello();

//공통 로직1 시작
log.info("start");
String result1 = target.callA(); //호출 메소드가 다름
log.info("result={}", result1);
//공통 로직1 종료

//공통 로직2 시작
log.info("start");
String result2 = target.callB(); //호출 메소드가 다름
log.info("result={}", result2);
//공통 로직2 종료
```

- 리플렉션 : 클래스나 메소드의 메타정보를 사용해서 동적으로 호출하는 메소드를 변경할 수 있다.
      
```java
// 리플렉션 적용 후  
@Test
void reflectionTest() throws Exception{
    //클래스 정보
    Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

    Hello target = new Hello();
    //callA 메소드 정보
    Method methodCallA = classHello.getMethod("callA");
    dynamicCall(methodCallA, target);

    //callB 메소드 정보
    Method methodCallB = classHello.getMethod("callB");
    dynamicCall(methodCallB, target);
}

/**
 * A,B를 한번에 처리할 수 있는 공통처리 로직.
 * 리플렉션을 사용하여 Method라는 메타정보를 추상화.
 */
private void dynamicCall(Method method, Object target) throws Exception {
    log.info("start");
    Object result = method.invoke(target);
    log.info("result={}", result);
}
```  

- 런타임에 동작하기 때문에, 컴파일 시점에 오류를 잡을 수 없다.  
- 정말 필요한 상황이 아니면 일반적으로는 사용을 권장하지 않는다.


## JDK 동적 프록시  

- 개발자가 직접 프록시 클래스를 만들지 않고, 프록시 객체를 동적으로 런타임에 개발자 대신 만들어 준다.  
- 인터페이스를 기반의로 프록시를 동적으로 만들어준다. —> **인터페이스 필수**  

```java
// JDK 동적 프록시가 제공하는 InvocationHandler
package java.lang.reflect;

/**
* proxy : 프록시 자신
* method : 호출한 메소드
* args : 메소드를 호출할 때 전달한 인수
*/
public interface InvocationHandler{
	public Object invoke(Object proxy, Method method, Object[] args) throw Throwable;
}
```  

## CGLIB

- CGLIB : Code Generator Library
- 바이트코드를 조작해서 동적으로 클래스를 생성하는 기술을 제공하는 라이브러리
- 사용하면 인터페이스가 없어도 구체 클래스만 가지고 동적 프록시를 만들어낼 수 있다.
- 원래는 외부 라이브러리지만, 스프링 프레임워크가 스프링 내부 소스 코드에 포함했다. 따라서 스프링을 사용한다면 별도의 외부 라이브러리를 추가하지 않아도 사용할 수 있다.
- 제공 인터페이스
```java
package org.springframework.cglib.proxy;

public interface MethodInterceptor extends Callback {
	 Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) 
				throws Throwable;
}
```
