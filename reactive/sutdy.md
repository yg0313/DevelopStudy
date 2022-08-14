# 리액티브 프로그래밍  
### 리액티브 프로그래밍이란
- 함수적이면서 선언적이다. 순차적으로 수행되는 작업 단계를 나타내는 것이 아닌 데이터가 흘러가는 파이프라인이나 스트림을 포함한다.  
  - 이러한 리액티브 스트림은 데이터 전체를 사용할 수 있을 때까지 기다리지 않고 사용 가능한 데이터가 있을 때마다 처리되므로 사실상 입력되는 데이터는 무한.

### 리액티브 스트림  
- 차단되지 않는 *백 프레셔를 갖는 비동기 스트림 처리의 표준을 제공하는 것이 목적.  
  *백 프레셔 : 데이터를 소비하는(읽는) 컨슈머가 처리할 수 있는 만큼으로 전달 데이터를 제한함으로써 지나치게 빠른 데이터 소스로부터의 데이터 전달 폭주를 피할 수 있는 수단.
 
- #### 자바 스트림 vs 리액티브 스트림
  ```
  - 자바스트림 
    - 동기화되어 있고 한정된 데이터로 작업을 수행.
  - 리액티브 스트림  
    - 무한 데이터셋을 비롯해서 어떤 크기의 데이터셋이건 비동기 처리 지원.  
    - 실시간으로 데이터를 처리하며, 백 프레셔를 사용해서 데이터 전달 폭주를 막는다.  
  ```
- 리액티브 스트림은 4개의 인터페이스로 요약할 수 있다.
  - Publisher(발행자)   
    - Subscriber가 Publisher를 구독 신청할 수 있는 subscribe() 메소드가 선언되어 있다. 
  ```
   public interface Publisher<T> {
    public void subscribe(Subscriber<? super T> s);
   }
  ```  
  
  - Subscriber(구독자)
    - Subscriber가 수시할 첫 번째 이벤트는 onSubscirbe()의 호출을 통해 이루어진다.
    - Publisher가 onSubsribe()를 호출할 때 이 메소드의 인자로 Subscription 객체를 Subscriber에 전달.  
    - Subscription 객체를 통해서 구독 관리.
    - 데이터 요청이 완료되면 데이터가 스트림을 통해 전달되기 시작, 이때 onNext() 메소드가 호출되어 Publisher가 전송하는 데이터가 Subscriber에게 전달되며 에러가 발생하는 경우, onError() 호출.
    - Publisher에서 전송할 데이터가 없고 더 이상의 데이터를 생성하지 않는다면 Publisher가 onComplete()를 호출하여 작업이 끝났다고 Subscriber에게 전달.
  ```
    public interface Subscriber<T> {
      public void onSubscribe(Subscription s);
      public void onNext(T t);
      public void onError(Throwable t);
      public void onComplete();
    }
  ```
  - Subscription(구독)
    - Subscriber는 request()를 호출하여 전송되는 데이터를 요청.
    - 받고자하는 데이터 항목 수를 long 타입의 값을 인자로 전달 -> 이 과정이 백 프레이셔이며, 더 많은 데이터를 Publisher가 전송하는 것을 방지.
    - 더 이상 데이터를 수신하지 않고 구독을 취소하는 경우 cancel() 호출.   
    ```
      public inteface Subscription {
        void request(long n);
        void cancel();
      }
    ```
  - Processor(프로세서)  
    - Subscriber 역할로 데이터를 수신하고 처리 후, 역할을 바꾸어 Publisher 역할로 처리 결과를 자신의 Subscriber들에게 발행.
    ```
      public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
      
      }
    ```














-------------------------
참고 : 스프링 인 액션  
