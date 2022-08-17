![image](https://user-images.githubusercontent.com/11959111/158748603-05a06049-e44b-4bad-ab46-90efb867fee6.png)

# 도커란?

### *컨테이너 기반의 오픈소스 가상화 플랫폼

*컨테이너 : 여러 애플리케이션이 다양한 운영체제에 올라가 동작하는 기술

### 장점

- 하나의 서버에 여러개의 컨테이너를 실행하여 서로 영향을 미치지 않고 독립적으로 실행되어 가볍고 빠르게 동작
- 개발언어에 종속되지 않음
  

## 이미지

컨테이너 실행에 필요한 파일과 설정값등을 포함하고 있는것으로 상태값을 가지지않고 변하지 않음(Immutable)

컨테이너는 이미지를 실행한 상태라고 볼 수 있고 추가되거나 변하는 값은 컨테이너에 저장, 같은 이미지에서 여러개의 컨테이너를 생성할 수 있고 컨테이너의 상태가 바뀌거나 컨테이너가 삭제되더라도 이미지는 변하지 않고
그대로 남아 있음.

---------------------------------------------------    

## 환경 세팅 - Window10 pro, java1.8, springboot 2.6.4, gradle
### Docker 도커 설치 - https://www.docker.com/products/docker-desktop 

![image](https://user-images.githubusercontent.com/11959111/158770161-71a0bf78-ca06-4358-875e-851b100111b7.png)

docker version 명령으로 Docker 서버와 클라이언트 정보 확인

### Springboot 설정
1. [확인을 위한 컨트롤러 설정](https://github.com/asdf120/docker/blob/main/src/main/java/com/docker/controller/MainController.java)  

``` JAVA
@Controller
public class MainController {
    @GetMapping("/main")
    @ResponseBody
    public String main(){
        return "docker test";
    }
}
```

2. [이미지 생성을 위한 Dockerfile 생성](https://github.com/asdf120/docker/blob/main/Dockerfile)  
    
``` JAVA
FROM openjdk:8-jdk-alpine 
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```  
프로젝트 root에 Dockerfile 파일 생성
- FROM - 도커 생성시 기반 이미지, 프로젝트의 jdk버전으로 설정  
- ARG - 빌드시 사용할 환경 변수 선언, Spring jar파일이 생성되는 위치를 변수로 선언  
- COPY - jar 파일명을 app.jar로 복사  
- ENTRYPOINT - 컨테이너 시작시 실행할 스크립트  

3. 도커 이미지 생성
```
docker build -t kyg/docker-spring-boot .
```
- docker build -t 생성할이미지명 .  
 ** 맨뒤 의 .은 Dockerfile의 경로를 나타냄  

4. 실행
```
docker run -p 8080:8080 kyg/docker-spring-boot
```
- docker run -p container [내부포트번호: 호스트pc포트번호] [이미지명]  

![image](https://user-images.githubusercontent.com/11959111/158775966-ddb04695-6eb8-43ca-8c5f-00b4208a1949.png)  

5. 실행 화면  
  
![image](https://user-images.githubusercontent.com/11959111/158776207-855e9802-35b6-4fc8-bf36-abab4abc38be.png)


##### 참고 
https://subicura.com/2017/01/19/docker-guide-for-beginners-1.html  
https://daddyprogrammer.org/post/12542/springboot-docker-integration/  
https://ksr930.tistory.com/139  

