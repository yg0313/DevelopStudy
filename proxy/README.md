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

- 리플렉션
    - 클래스나 메소드의 메타정보를 사용해서 동적으로 호출하는 메소드를 변경할 수 있다.
