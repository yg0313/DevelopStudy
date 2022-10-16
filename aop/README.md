#  스프링 AOP 구현

### 어드바이스 종류   
- @Around : 메소드 호출 전후에 수행, 가장 강력한 어드바이스, 조인포인트 실행 여부 선택, 반환 값 변환, 예외 변환등 가능
- @Before : 조인 포인트 실행 이전에 실행
    - @Around 와 다르게 작업 흐름을 변경할 수 없다.
    - 메소드 종료시 자동으로 다음 타겟이 호출된다. 예외가 발생하면 다음코드가 호출되지는 않는다.
- @After Returning : 조인 포인트가 정상 완료후 실행
    
    ```java
    @AfterReturning(value = pointcut, returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result){
    	...
    }
    ```
    
    - returning 속성에 사용된 이름은 어드바이스 메소드 이름과 일치해야 한다.
    - returning 절에 지정된 타입의 값을 반환하는 메소드만 대상으로 실행한다. (부모 타입을 지정하면 모든 자식 타입은 인정된다.)
    - @Around와 다르게 반환되는 객체를 변경할 수는 없다. 반환 객체를 변경하려면 @Around 를 사용해야 한다. (반환 객체를 조작할 수도 있다.)
- @After Throwing : 메소드가 예외를 던지는 경우 실행
    
    ```java
    @AfterThrowing(value = pointcut, throwing="ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex){
    	...
    }
    ```
    
    - throwing 속성에 사용된 이름은 어드바이스 메소드의 매개변수 이름과 일치해야 한다.
    - throwing 절에 지정된 타입과 맞은 예외를 대상으로 실행한다. (부모 타입을 지정하면 모든 자식 타입은 인정된다.)
- @After : 조인 포인트가 정상 또는 예외에 관계없이 실행(finally)
    - 메소드 실행이 종료되면 실행된다. (try - catch - finally에서 finally와 유사)
    - 정상 및 예외 반환 조건을 모두 처리한다.
    - 일반적으로 리소스를 해제하는 데 사용한다.
<br/>

** 모든 어드바이스는 `org.aspectj.lang.JoinPoint`를 첫번째 파라미터에 사용할 수 있다. (생략 가능)  
  단, @Around는 `ProceedingJoinPoint`를 사용해야 한다.
<br/>
### JoinPoint 인터페이스의 주요 기능

- getArgs() : 메소드 인수를 반환
- getThis() : 프록시 객체를 반환
- getTarget() : 대상 객체를 반환
- getSignature() : 조언되는 메소드에 대한 설명을 반환
- toString() : 조언되는 방법에 대한 유용한 설명을 인쇄

### ProceedingJoinPoint 인터페이스

- org.aspectj.lang.JoinPoint의 하위 타입
- proceed() : 다음 어드바이스나 타겟을 호출
<br/>

### 포인트컷 지시자

애스펙트J는 포인트컷을 편리하게 표현하기 위한 특별한 표현식을 제공한다.

포인트컷 표현식은 `execution` 같은 포인트컷 지시자(Pointcut Designator)로 시작한다. (줄여서 PCD)

- 포인트컷 지시자의 종류
    - execution : 메소드 실행 조인 포인트를 매칭한다. 스프링 AOP에서 가장 많이 사용하고, 기능도 복잡하다.
    - within : 특정 타입 내의 조인 포인트를 매칭한다.
    - args : 인자가 주어진 타입의 인스턴스인 조인 포인트
    - this : 스프링 빈 객체(스프링 AOP 프록시)를 대상으로 하는 조인 포인트
    - target : Target 객체(스프링 AOP 프록시가 가리키는 실제 대상)ㄹ;ㅡㄹ 대상으로 하는 조인 포인트
    - @target : 실행 객체의 클래스에 주어진 타입의 어노테이션이 있는 조인 포인트
    - @within : 주어진 어노테이션이 있는 타입 내 조인 포인트.
    - @annotation : 메소드가 주어진 어노테이션을 가지고 있는 조인포인트를 매칭
    - @args : 전달된 실제 인수의 런타임 타입이 주어진 타입의 어노테잇현을 갖는 조인 포인트
    - bean : 스프링 전용 포인트컷 지시자, 빈의 이름으로 포인트컷을 지정한다.
      - 스프링 빈의 이름으로 AOP 적용 여부를 지정한다. 스프링에서만 사용 가능한 지시자.
### within

특정 타입 내의 조인 포인트에 대한 매칭을 제한한다. → 해당 타입이 매칭되면 그 안의 메소드(조인 포인트)들이 자동으로 매칭된다.

** **주의사항**

표현식에 부모 타입을 지정하면 안된다. → 정확하게 타입을 맞춰야 하는점에서  execution과 차이가 있다.

### args

인자가 주어진 타입의 인스턴스인 조인 포인트로 매칭

기본문법은 `execution`의 `args` 부분과 같다.

** execution과 args의 차이점

- execution은 파라미터 타입이 정확하게 매칭되어야 한다. execution 은 클래스에 선언된 정보를 기반으로 판단한다.
- args는 부모타입을 허용한다. args는 실제 넘어온 파라미터 객체 인스턴스를 보고 판단한다.

### @target vs @within

- @target은 인스턴스의 모든 메소드를 조인 포인트로 적용한다.
- @within은 해당 타입 내에 있는 메소드만 적용한다.
- 쉽게 이야기해서 @target은 부모 클래스의 메소드까지 어드바이스를 모두 적용하고, @within은 자기 자신의 클래스에 정의된 메소드에만 어드바이스를 적용한다. (* 부모 클래스의 메소드를 오버라이딩하면 어드바이스 적용)
<br/>

### this, target

- 적용 타입 하나를 정확하게 지정해야한다.

```kotlin
this(패키지.클래스)
target(패키지.클래스)
```

- * 같은 패턴을 사용할 수 없다.
- 부모 타입을 허용한다.
<br/>

**this vs target**

스프링에서 AOP를 적용하면 실제 target 객체 대신에 프록시 객체가 스프링 빈으로 등록된다.

- this는 스프링 빈으로 등록되어 있는 **프록시 객체**를 대상으로 포인트컷을 매칭한다.
- target은 실제 **target 객체**를 대상으로 포인트컷을 매칭한다.
<br/>

**프록시 생성 방식에 따른 차이**

스프링은 프록시를 생성할 때 JDK 동적 프록시와 CGLIB를 선택할 수 있다. 둘의 프록시를 생성하는 방식이 다르기 때문에 차이가 발생한다.

- JDK 동적 프록시: 인터페이스가 필수이며, 인터페이스를 구현한 프록시 객체를 생성한다.
 ![image](https://user-images.githubusercontent.com/11959111/196023915-afd45ecb-49ff-4810-95b9-5812acd40dcb.png)

- CGLIB: 인터페이스가 있어도 구체 클래스를 상속 받아서 프록시 객체를 생성한다.
 ![image](https://user-images.githubusercontent.com/11959111/196023923-23546f43-7284-45ef-a15f-af9d546498a4.png)

