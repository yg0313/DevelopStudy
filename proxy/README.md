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
/**
* obj : CGLIB가 적용된 객체
* method : 호출된 메소드
* args : 메소드를 호출하면서 전달된 인수
* proxy : 메소드 호출에 사용
*/
public interface MethodInterceptor extends Callback {
	 Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) 
				throws Throwable;
}
```

## 프록시 팩토리

스프링은 유사한 구제적인 기술들이 있을 때, 그것들을 통합해서 일관성 있게 접근할 수 있고, 편리하게 사용할 수 있는 추상화된 기술을 제공한다. 

### 프록시 팩토리  
- 동적 프록시를 통합해서 편리하게 만들어주는 기술.
- 스프링에서 이 기술을 지원해주지 않는다면 상황에 따라서 JDK  동적 프록시를 사용하거나 CGLIB를 사용해야 했다.  
- 프록시 팩토리를 이용하여, 인터페이스가 있으면 JDK 동적 프록시를 사용하고, 구체 클래스가 있다면  CGLIB를 사용한다. 그리고 이 설정을 변경할 수도 있다.  

### Advice

- 프록시 팩토리를 사용하기 이전에는 JDK 동적 프록시의 `InvocationHandler` CGLIB가 제공하는 `MethodInterceptor` 를 각각 구현하여 사용했지만, 프록시 팩토리를 이용하면  Advice를 이용하여 해결하면 된다.  
![image](https://user-images.githubusercontent.com/11959111/188117349-03b57c38-795c-4fa5-8e77-2eb8eab9c679.png)
- 프록시 팩토리를 사용하면 Advice를 호출하는 전용 `InvocationHandler` , `MethodInterceptor` 를 내부에서 사용한다.

** 스프링 부트는 AOP를 적용할 때 기본적으로 proxyTargetClass=true 로 설정해서 사용, 따라서 인터페이스가 있어도 항상 CGLIB를 사용해서 구체 클래스를 기반으로 프록시를 생성한다.

## 포인트컷, 어드바이스, 어드바이저

- **포인트컷**(Pointcut) : 어디에 부가 기능을 적용할지, 어디에 부가 기능을 적용하지 않을지 판단하는 필터링 로직이다. 주로 클래스와 메소드 이름으로 필터링한다. 이름 그대로 어떤 포인트(Point)에 기능을 적용할지 하지 않을지 잘라서(cut) 구분하는 것이다.
- **어드바이스**(Advice) : 프록시가 호출하는 부가 기능이다.  단순하게는 프록시 로직이라 생각하면 된다.
- **어드바이저**(Advisor) : 단순하게 하나의 포인트컷과 하나의 어드바이스를 가지고 있는 것. → 포인트컷1 + 어드바이스1
