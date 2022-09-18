# proxy
- 클라이언트가 요한 결과를 서버에 직접 요청하는 것이 안니 대리자를 통해 대신 서버에 요청할 수 있는데, 이와 같이 대리자를 프록시라고 한다.
- 프록시를 사용하더라도 클라이언트는 서버에게 요청을 한 것인지, 프록시에게 요청을 한 것인지 몰라야 한다.  


**주요기능**
- 접근제어
  - 권한에 따른 접근 차단
  - 캐싱
  - 지연 로딩
- 부가 기능 추가
  - 원래 서버가 제공하는 기능에 더해서 부가 기능을 수행한다.  
ex) 요청 값이나, 응답 값을 중간에 변형한다.  
    실행 시간을 측정해서 추가 로그를 남긴다.  
  
  
## 프록시 패턴과 데코레이터 패턴  
- 프록시 패턴 : 접근 제어가 목적
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

- **포인트컷**(Pointcut) : 어디에 부가 기능을 적용할지, 어디에 부가 기능을 적용하지 않을지 판단하는 **필터링 로직**이다. 주로 클래스와 메소드 이름으로 필터링한다. 이름 그대로 어떤 포인트(Point)에 기능을 적용할지 하지 않을지 잘라서(cut) 구분하는 것이다.
- **어드바이스**(Advice) : 프록시가 호출하는 부가 기능이다.  단순하게는 프록시 로직이라 생각하면 된다.
- **어드바이저**(Advisor) : 단순하게 하나의 포인트컷과 하나의 어드바이스를 가지고 있는 것. → 포인트컷1 + 어드바이스1
![image](https://user-images.githubusercontent.com/11959111/188322997-16a98fe8-d8da-420f-bcc8-50fc29271441.png)


## 빈 후처리기

![image](https://user-images.githubusercontent.com/11959111/190174776-ba501fd2-298c-490b-a4f9-a4720bac72ac.png)  

- `@Bean` 이나 컴포넌트 스캔으로 스프링 빈을 등록하면, 스프링은 대상 객체를 생성하고 스프링 컨테이너 내부의 빈 저장소에 등록한다. 이후에 스프링 컨테이너를 통해 등록한 스프링 빈을 조회해서 사용한다.

### 빈 후처리기 - BeanPostProcessor

스프링이 빈 저장소를 등록할 목적으로 생성한 객체를 빈 저장소에 등록하기 직전에 조작하고 싶다면 빈 후기처리를 사용하면 된다. 빈 포스트 프로세서(BeanPostProcessor)는 빈을 생성한 후에 무언가를 처리하는 용도로 사용한다.

**빈 후처리기 기능**

- 객체를 조작할 수도 있고, 완전히 다른 객체로 바꿔치기 하는 것도 가능하다.

**빈 후처리기 과정**

![image](https://user-images.githubusercontent.com/11959111/190178937-1c590e22-301c-4d47-8aff-6fae2935980a.png)  

1. 생성 : 스프링 빈 대상이 되는 객체를 생성한다.(@Bean, 컴포넌트 스캔 모두 포함)
2. 전달 : 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달한다.
3. 후 처리 작업 : 빈 후처리기는 전달된 스프링 빈 객체를 조작하거나 다른 객체로 바꿔치기 할 수 있다.
4. 등록 : 빈 후처리기는 빈을 반환한다. 전달 된 빈을 그대로 반환하면 해당 빈이 등록되고, 바꿔치기 하면 다른 객체가 빈 저장소에 등록된다.

![image](https://user-images.githubusercontent.com/11959111/190181715-50256c5f-5f38-4642-b092-6498e225b9d0.png)

****** **프록시 적용 대상 여부 체크**

- 직접 등록한 스프링 빈 뿐만 아니라, 스프링 부트가 기본으로 등록하는 빈들도 빈 후처리기에 넘어오므로, 어떤 빈을 프록시로 만들 것인지 기준이 필요하다.
- 스프링 부트가 기본으로 제공하는 빈 중에는 프록시 객체를 만들 수 없는 빈들도 있다. 따라서 모든 객체를 프록시로 만들 경우 오류가 발생한다.

**—>  빈 후처리기를 이용한 프록시를 적용하는 경우에는 프록시로 만들 빈을 설정하는 기준이 필요**


## 스프링이 제공하는 빈 후처리기

### 자동 프록시 생성기 - AutoProxyCreator

```kotlin
// gradle에 추가
implementation group: 'org.springframework.boot', name: 'spring-boot-starter-aop'
```

- 스프링 부트 자동 설정으로 `AnnotationAwareAsjpectJAutoProxyCreator` 빈 후처리기가 스프링 빈에 자동 등록된다.
- 스프링 빈으로 등록된 `Advisor`들을 자동으로 찾아서 프록시가 필요한 곳에 자동으로 프록시를 적용해준다.
- `Advisor` 안에는 `Pointcut`과 `Advice`가 이미 모두 포함되어 있으므로, `Advisor`만 알고 있으면 그 안에 있는 `Pointcut`으로 어떤 스프링 빈에 프록시를 적용해야 할지 알 수 있다. 그리고 `Advice`로 부가 기능을 적용하면 된다.

** `AnnotationAwareAspectJAutoProxyCreator` 는 @AspectJ와 관련된 AOP 기능도 자동으로 찾아서 처리해준다.


***자동 프록시 생성기 작동 과정***  

![image](https://user-images.githubusercontent.com/11959111/190653056-8b3688b0-fd13-4bbb-96af-e3a5a308079c.png)

1. 생성 : 스프링이 스프링 빈 대상이 되는 객체를 생성한다. (@Bean, 컴포넌트 스캔 모두 포함)
2. 전달 : 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달한다.
3. 모든 Advisor 빈 조회 : 자동 프록시 생성기 - 빈 후처리기는 스프링 컨테이너에서 모든 Advisor를 조회한다.
4. 프록시 적용 대상 체크 : 앞서 조회한 Advisor에 포함되어 있는 포인트컷을 사용해서 해당 객체가 프록시를 적용할 대상인지 아닌지 판단한다.  객체의 클래스 정보, 해당 객체의 모든 메소드를 포인트컷에 하나하나 모두 매칭한다. 조건이 하나라도 만족하면 프록시 적용 대상이 된다.
    
    ex) 10개의 메소드 중 하나만 포인트컷 조건에 만족해도 프록시 적용 대상.
    
5. 프록시 생성 : 프록시 적용 대상이면 프록시를 생성하고 반환해서 프록시를 스프링 빈으로 등록한다. 만약 프록시 적용 대상이 아니라면 원본 객체를 반환해서 원본 객체를 스프링 빈으로 등록한다.
6. 빈 등록 : 반환된 객체는 스프링 빈으로 등록된다.

 ** ***프록시를 만드는 단계에서 쓰는 포인트컷과 실제 실행단계에서 쓰는 포인트컷을 구분해야 한다.***
 
 
 ### ** 포인트컷의 사용 방식

- 포인트 컷은 2가지로 사용된다.
1. 프록시 적용 여부 판단 - 생성 단계
    - 자동 프록시 생성기는 퐁니트컷을 사용해서 해당 빈이 프록시를 생성할 필요가 있는지 없는지 체크한다.
    - 클래스 + 메소드 조건을 모두 비교한다. 모든 메소드를 체크하며, 포인트컷 조건에 하나하나 매칭 후, 조건에 맞는 것이 하나라도 있으면 프록시를 생성한다.
    - 조건에 맞는 것이 하나도 없으면 프록시를 생성하지 않는다.
2. 어드바이스 적용 여부 판단 - 사용 단계
- 프록시가 호출되었을 때 부가 기능인 어드바이스를 적용할지 말지 포인트컷을 보고 판단한다.
    
    ex) TestController는 프록시가 설정되어 있다.
    - TestController의 `request()` 는 현재 포인트컷 조건에 만족하므로 프록시는 어드바이스를 먼저 호출하고, `target` 을 호출한다.
    - TestController의 `noLog()` 는 현재 포인트컷 조건에 만족하지 않으므로 어드바이스를 호출하지 않고 바로 `target` 만 호출한다.
    

** ***프록시를 모든 곳에 생성하는 것은 비용 낭비이다. 꼭 필요한 곳에 최소한의 프록시를 적용해야 한다. 그래서 자동 프록시 생성기는 모든 스프링 빈에 프록시를 적용하는 것이 아니라 포인트컷으로 한번 필터링해서 어드바이스가 사용될 가능성이 있는 곳에만 프록시를 생성한다.***


## 스프링이 제공하는 빈 후처리기2

![image](https://user-images.githubusercontent.com/11959111/190664543-1505c534-fac6-41b1-b6d0-b3ca1be9623a.png)

- 스프링 부트를 실행하면, 스프링이 초기화되면서 의도하지 않은 프록시가 설정되어 로그가 남게 된다. —> 메소드 이름뿐만 아니라 패키지에 이름과 같이 저장할 수 있는 매우 정밀한 포인트컷이 필요하다.


## 하나의 프록시, 여러 Advisor 적용

어떤 스프링 빈이 `advisor1, advisor2` 가 제공하는 포인트컷의 조건을 모두 만족하더라도 프록시 자동 생성기는 프록시를 하나만 생성한다. → 프록시 팩토리가 생성하는 프록시는 내부에  여러 `advisor` 들을 포함할 수 있기 때문이다. 따라서 프록시를 여러개 생성해서 자원을 낭비할 필요가 없다.

** 프록시 자동 생성기 상황별 정리

- `advisor1`의 포인트컷만 만족 → 프록시 1개 생성, 프록시에 `advisor1` 만 포함
- `advisor1, advisor2` 의 포인트컷 모두 만족 → 프록시 1개 생성, 프록시에 `advisor1,advisor2` 모두 포함
- `advisor1, advisor2` 의 포인트컷 모두 불만족 → 프록시가 생성되지 않음.


## @Aspect AOP

스프링은 `@Aspect` 어노테이션으로 포인트컷과 어드바이스로 구성되어 있는 어드바이저 생성 기능을 지원한다.
```kotlin
@Aspect
public class AspectTest{
    @Around("execution(패키지명, 메소드명등)") // 포인트컷
    public Object execut(ProceedingJoinPoint joinPoint){
	...// 어드바이스 로직
    }
}
```

** @Aspect 는 관점 지향 프로그래밍(AOP)을 가능하게 하는 AspectJ 프로젝트에서 제공하는 어노테이션으로 스프링은 이것을 차용해서 프록시를 통한 AOP를 가능하게 한다.

자동 프록시 생성기는 2가지 일을 한다.

- @Aspect 어노테이션을 보고 어드바이저로 변환해서 저장한다.

![image](https://user-images.githubusercontent.com/11959111/190915669-ed3bd171-7c99-4fef-b36d-dcbffa5dfb99.png)

1. 실행 : 스프링 애플리케이션 로딩 시점에 자동 프록시 생성기를 호출한다.
2. 모든 @Aspect 빈 조회 : 자동 프록시 생성기는 스프링 컨테이너에서 @Aspect 어노테이션이 붙은 스프링 빈을 모두 조회한다.
3. 어드바이저 생성 : @Aspect 어드바이저 빌더ㅐ를 통해  @Aspect 어노테이션 정보를 기반으로 어드바이저를 생성한다.
4. @Aspect 기반 어드바이저 저장 : 생성한 어드바이저를 @Aspect 어드바이저 빌더 내부에 저장한다.

 **@Aspect 어드바이저 빌더**

`BeanFactoryAspectJAdvisorsBuilder` 클래스, @Aspect의 정보를 기반으로 포인트컷, 어드바이스, 어드바이저를 생성하고 보관하는 것을 담당한다. @Aspect의 정보를 기반으로 어드바이저를 만들고, @Aspect 어드바이저 빌더 내부 저장소에 캐시한다. 캐시에 어드바이저가 이미 만들어져 있는 경우 캐시에 저장된 어드바이저를 반환한다.

- 어드바이저를 기반으로 프록시를 생성한다.

![image](https://user-images.githubusercontent.com/11959111/190915647-22d3c787-fd5c-4bb4-862b-1ce621a22320.png)

1. 생성 : 스프링 빈 대상이 되는 객체를 생성한다. (@Bean, 컴포넌트 스캔 모두 포함)
2. 전달 : 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달한다.

3-1. Advice 빈 조회 : 스프링 컨테이너에서 Advisor 빈을 모두 조회한다.

3-2. @AspectAdvisor : @Aspec4t 어드바이저 빌더 내부에 저장된 Advisor를 모두 조회한다.

4. 프록시 적용 대상 체크 : 3-1, 3-2에서 조회한 Advisor에 ㅜ포함되어 있는 포인트컷을 사용해서 해당 객체가 프록시를 적용할 대상인지 아닌지 판단한다. 객체의 클래스 정보, 해당 객체의 모든 메소드를 포인트컷에 하나하나 모두 매칭한다. 조건이 하나라도 만족하면 프록시 적용 대상이 된다.
5. 프록시 생성 : 프록시 적용 대상이면 프록시를 생성하고 프록시를 반환하고, 스프링 빈으로 등록한다. 프록시 적용 대상이 아니라면 원본 객체를 반환해서 원본 객체를 스프링 빈으로 등록한다.
6. 빈 등록 : 반환된 객체는 스프링 빈으로 등록된다.
