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
