#  스프링 AOP 구현

### 어드바이스 종류  
- @Around : 메소드 호출 전후에 수행, 가장 강력한 어드바이스, 조인포인트 실행 여부 선택, 반환 값 변환, 예외 변환등 가능
- @Before : 조인 포인트 실행 이전에 실행
- @After Returning : 조인 포인트가 정상 완료후 실행
- @After Throwing : 메소드가 예외를 던지는 경우 실행
- @After : 조인 포인트가 정상 또는 예외에 관계없이 실행(finally)

** 모든 어드바이스는 `org.aspectj.lang.JoinPoint`를 첫번째 파라미터에 사용할 수 있다. (생략 가능)  
  단, @Around는 `ProceedingJoinPoint`를 사용해야 한다.
