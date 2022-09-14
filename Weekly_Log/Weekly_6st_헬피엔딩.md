# 6주차 위클리 진행사항

## 팀 구성원, 개인 별 역할

---

- 오현직 : 잡무 담당(운동시설 상세조회, 게시물, 유저 )
- 지남은 : 카카오 지도 API 를 통한 운동시설 조회
- 홍승표 : 회원가입, 로그인, 유저 마이페이지, 운동일지 작성
- 홍준표 : CRUD 기반 게시판, 이미지 파일 업로드

## 팀 내부 회의 진행 회차 및 일자

---

- 13회차(2022.09.07) 디스코드 타이레놀 방 (전원 참석)
- 14회차(2022.09.13) 디스코드 타이레놀 방 (전원 참석)


## 현재까지 개발 과정 요약

---

### 기능 관련 구현

1. 운동시설 - 오현직, 지남은 
    - 지도 기반 운동시설 조회 후, 마커를 클릭하면 상세페이지로 넘어가는 과정 완성
        
        ![image](https://user-images.githubusercontent.com/42793489/189939839-9be43528-d4eb-4d4d-9a50-c5bda78f4e7b.png)
        
    - 상세조회후 다시 운동시설 조회 시, 클릭이 고정된 오류 해결
    - 상세페이지에서 데이터가 제대로 호출이 안되는 오류만 해결하면 되는 상황
2. 유저(OAUTH) - 홍승표(메인)
    - OAUTH
        - 회원가입시, 구글,페이스북 계정으로 가입하는 OAUTH 추가
            
            ![image](https://user-images.githubusercontent.com/42793489/189939869-f719b0ca-bf2f-4b65-9998-3734f0c240a9.png)
            
        
        - 문제점 : 현재 로그인 기능만 되는 상태이고, 그 이외 프로젝트 기능을 사용하지 못하고 있음, 즉 로그인 만 되어있는 상태
        - 해결 : 강사님께 문의 결과 OAUTH 관련 실습을 이번주에 진행할 예정이라서 그 이후 보완 할 예정
    - 추후 추가할 기능 : 비밀번호 찾기 기능
3. CRUD 기능 게시판 구현 - 홍준표(메인), 오현직(서브)
    - 게시판 기능 추가
        - 게시글 리스트 조회시 작성자의 닉네임 확인 가능하게 수정
            
            ![image](https://user-images.githubusercontent.com/42793489/189941774-25ccabf8-4b11-4eea-b4f1-39e90d7d791a.png)
        
4. 채팅 기능 구현- 오현직
    - 기존에 영속성이 없었던 채팅기능에 영속성을 부여
    - 자신이 속한 채팅방만 확인이 가능하게 수정
    - 채팅방을 다시 들어와도 이전에 작성한 메세지를 불러오기 완료
   
    ![image](https://user-images.githubusercontent.com/42793489/189942563-46240657-ed0b-4cd6-bbaf-c30904525469.png)

    ![image](https://user-images.githubusercontent.com/42793489/189942832-39d11b1d-ea6a-41f2-a317-3a052ccf9b8a.png)


## 개발 과정에서 나왔던 질문

---

- 비밀번호 찾기 기능
    - 수업 시간에 배운 Redis 를 통해 인증번호를 가입한 이메일로 전송
    - 1시간 이내로 인증번호를 입력하여, 메모리에 저장된 데이터랑 일치 여부 확인
    - 일치 할 경우 비밀번호 변경, 1시간 이내로 확인을 못받을 경우, 자연 삭제
- OAUTH
    - 아직 프로젝트에 녹이기에 어려움을 겪고 있음
    - 강사님께서 이번주 수업때 다룬다고 답변을 받았음
    - 수업시간에 배운 이후에 리팩토링 작업 하기로 예정

## 개발 결과물 공유

---

> 인증샷
> 

Github Repository URL: [https://github.com/likelion-backendschool/hellpyending](https://github.com/likelion-backendschool/hellpyending)

ERD cloud URL : [https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc](https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc)1
