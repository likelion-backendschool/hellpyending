# 8주차 위클리 진행사항

## 팀 구성원, 개인 별 역할

---

- 오현직 : 프로젝트 배포 작업, Docker, Jenkins  , 프론트 UI MockUp
- 지남은 : 프론트 UI MockUp
- 홍승표 : 유저 기능 버그 수정
- 홍준표 : 해커톤 발표 관련 코드 정리 및 프로젝트 정리

## 팀 내부 회의 진행 회차 및 일자

---

- 15회차(2022.09.20) 디스코드 타이레놀 방 - 전원참석
- 16회차(2022.09.21) 디스코드 타이레놀 방 - 준표
- 17회차(2022.09.22) 디스코드 타이레놀 방, 부평역 -  팀 전원 참석
- 18회차(2022.09.25) 디스코드 타이레놀 방 - 준표
- 19회차(2022.09.27) 디스코드 타이레놀 방 - 승표 제외

## 현재까지 개발 과정 요약

---

### 기능 관련 구현

1. 배포 작업
    - 네이버 클라우드 플랫폼 환경에서 서버 생성
        
        ![Untitled (7)](https://user-images.githubusercontent.com/42793489/192423593-7a5e33c2-1431-463a-ae97-a22de76566b9.png)
        
    - 가비아  를 통해 도메인 생성 후, NCP 에서 할당 받은 공인 ip 할당
    - 가상화 호스트에 java 17 설치
        
        ```bash
        1. sudo yum -y install wget curl
        2. get https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_linux-x64_bin.tar.gz
        ```
        
    - git clone 프로젝트
        1. 권한 부여 후 빌드
        2. 빌드 후 Docker file 생성
            
            ```docker
            FROM openjdk:17-jdk-alpine
            ARG JAR_FILE=build/libs/hellpyending-0.0.1-SNAPSHOT.jar
            COPY ${JAR_FILE} app.jar
            ENTRYPOINT ["java","-jar","/app.jar"]
            ```
            
        3. nginx site docker 파일 생성
            
            ```docker
            version: "3"
            services:
              app:
                image: 'jc21/nginx-proxy-manager:latest'
                restart: unless-stopped
                ports:
                  - '80:80' # Public HTTP Port
                  - '443:443' # Public HTTPS Port
                  - '81:81' # Admin Web Port
                environment:
                  TZ: "Asia/Seoul"
                  DB_MYSQL_HOST: "172.17.0.1"
                  DB_MYSQL_PORT: 3306
                  DB_MYSQL_USER: "lldjlocal"
                  DB_MYSQL_PASSWORD: "1234"
                  DB_MYSQL_NAME: "nginx"
                volumes:
                  - ./data:/data
                  - ./letsencrypt:/etc/letsencrypt
            ```
            
        4. 생성 후 NPM 으로 도메인 설정후 도커 환경에서 배포
        
2. 프론트 UI 
    - 기존 템플릿 UI 에서 새로운 템플릿 환경으로 MOCKUP 작업 진행
    
    ```
    참고 사이트 https://themeforest.net/item/porto-responsive-html5-template/4106987?_ga=2.107317189.694267747.1664191412-1033357831.1664191412
    ```
    

## 개발 과정에서 나왔던 질문

---

- 문제없이 해

## 개발 결과물 공유

---

> 인증샷
> 

![회의_0920](https://user-images.githubusercontent.com/42793489/192423611-7b7ca013-10d0-4ebc-86fb-f3a430767328.png)

0920_전원참석

![회의_0921_준표](https://user-images.githubusercontent.com/42793489/192423631-debe7cfa-bbb2-4317-9032-796ec5398a3b.PNG)

0921_준표_현직

![회의_0925_준표님](https://user-images.githubusercontent.com/42793489/192423658-aaa4aab2-ad3e-4d8f-8e01-44888992f72d.png)

0925_준표_현직

![09_27회의승표제외](https://user-images.githubusercontent.com/42793489/192423683-a29be791-b9e5-4f60-a90f-d4ef84204d3f.PNG)

09_27_승표제외

Github Repository URL: [https://github.com/likelion-backendschool/hellpyending](https://github.com/likelion-backendschool/hellpyending)

ERD cloud URL : [https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc](https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc)1
