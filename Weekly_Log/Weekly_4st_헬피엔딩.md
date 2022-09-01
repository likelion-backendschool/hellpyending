# 4주차 위클리 진행사항

## 팀 구성원, 개인 별 역할

---

- 오현직 : 잡무 담당(운동시설 상세조회, 게시물, 유저 )
- 지남은 : 카카오 지도 API 를 통한 운동시설 조회
- 홍승표 : 회원가입, 로그인
- 홍준표 : CRUD 기반 게시판

## 팀 내부 회의 진행 회차 및 일자

---

- 9회차(2022.08.25) 디스코드 타이레놀 방 (승표제외 전원 참석)
- 10회차(2022.08.29) 디스코드 타이레놀 방 (전원 참석, 김지애멘토님 참석)

## 현재까지 개발 과정 요약

---

### 기능 관련 구현

1. 지도 api - 오현직, 지남은 
    - 현재 목표하던 V1 기능 목표 달성
    - 초기 구현하던 UI에서 지도 기반 조회 뿐만 아니라 리스트 형태로 조회되게 하는것 달성
    - V1 구현 결과물
        ![기능구현_V1](https://user-images.githubusercontent.com/42793489/187217897-b797b84a-8003-4a1d-88ef-be757516b521.PNG)

        
        
    - 현재 운동시설 상세조회 기능 목표로 2차 구현 중
        - 상세페이지에 운동시설 기간별 회원권 가격, 연락처, 상세주소, 운동시설별 공지사항등을 포함하는것 목표
2. 유저(로그인 회원가입) - 홍승표(메인), 오현직(서브)
    - 마이페이지 기능을 목표로 기능 개발 하고 있음
        - 유저 정보 수정 기능
        - 유저가 작성한 게시글 조회
        - 유저가 작성가능한 나의 운동일지 기능 만들기
    - 강의시간에 배운 queryDSL 을 통하여 하나하나 리팩토링 진행하고 있음
    
3. CRUD 기능 게시판 구현 - 홍준표(메인), 오현직(서브)
    - 기본적인 CRUD 기능에 추가 기능을 목표로 V2 기능 구현하고 있음
        - 게시글 등록시 이미지 업로드 기능, 해시태그 기능(현직)
        - 게시판 대댓글 기능(준표)
    

## 개발 과정에서 나왔던 질문

---

- 게시글 조회시 등록한 이미지 list 불러올때 오류가 나는것 확인
    - 디버깅 수행 시 이미지 리스트인 ArticleImgList를 불러올때  `unable to evaluate the expression method threw 'org.hibernate.lazyinitializationexception' exception` 에러메시지 확인
    - 확인 :  application.yaml 에서 open-in-view 상태를 false를 함으로 이미지리스트 불러오기전에 sql 연결이 끊어짐으로 발생하는 현상
    - 조치 : open-in-view : true 로 수정 하여 view 단에서도 sql 연결이 끊어지지 않게 조치
- 게시글 이미지 관리
    - 초기에는 로컬로 저장하는 형태로 진행하려 했으나, 유지보수를 위해서 S3를 통해 이미지 관리하기로 정함

- 템플릿
    - 초기에 템플릿을 정하기 않고 각자 기능구현을 우선 목표로 진행했음
    - 멘토님은 UX 친화적인 템플릿을 찾는것을 우선으로 두고 정하라고 조언을 주셨음
- 프로젝트 이외 질문
    - 현재 하고 있는 방법으로 구현하는것이 맞는것인지, 다른팀은 어떤식으로 피드백을 받고 있는지 멘토님께 문의
    - 현재 기능이 돌아가는것을 목표로 진행하고, 그 이후에 리팩토링 하는 방향으로 하는것을 권장하셨음

## 개발 결과물 공유

---

> 인증샷
> ![08-25회의(승표제외)](https://user-images.githubusercontent.com/42793489/187217939-4763b6d4-456c-4db1-93d8-05589bd3634d.png)
> ![image](https://user-images.githubusercontent.com/42793489/187217997-ec667cf2-71a9-45ad-bd7c-e211dd9bfd6e.png)


Github Repository URL: [https://github.com/likelion-backendschool/hellpyending](https://github.com/likelion-backendschool/hellpyending)

ERD cloud URL : [https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc](https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc)1
