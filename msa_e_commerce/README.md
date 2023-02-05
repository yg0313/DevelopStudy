# MSA e_commerce

## config-server 변경 값 반영 방법  
- 서버 재기동  
- Actuator refresh  
  - Actuator refresh 옵션을 사용하여 재부팅하지 않고 변경 값 사용 가능.
  - Spring Boot Actuator
    - Application 상태, 모니터링
    - Metric 수집을 위한 Http End Point 제공
- Spring cloud bus 사용  
  - 분산 시스템의 노드(mircro service)를 경량 메시지 브로커와 연결
    - 현재 프로젝트에서 메시지 브로커는 rabbit MQ 사용
  - 상태 및 구성에 대한 변경 사항을 연결된 노드에게 전달(Broadcast) 
  
##  
  
- AMQP(Advanced Message Queuing Protocol), 메시지 지향 미들웨어를 위한 개방형 표준 응용 계층 프로토콜
  - 메시지 지향, 큐잉, 라우팅(P2P, Publisher-Subscriber), 신뢰성, 보안
  - Erlang, RabbitMQ에서 사용
- Kfka 프로젝트
  - Apache Software Foundation이 Scalr 언어로 개발한 오픈 소스 메시지 브로커 프로젝트
  - 분산형 스트리밍 플랫폼
  - 대용량의 데이터를 처리 가능한 메시징 시스템     
