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
