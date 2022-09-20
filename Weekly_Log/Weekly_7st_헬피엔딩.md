# 7주차 위클리 진행사항

## 팀 구성원, 개인 별 역할

---

- 오현직 : 운동시설 상세 조회, 아임포트 기반 결제 API , 소켓 통신 기반 알림 기능
- 지남은 : 프론트 UI MockUp,운동시설 상세 조회
- 홍승표 : 비밀번호 찾기(Redis) , 유저 시큐리티 보완
- 홍준표 : CRUD 기반 게시판 좋아요 기능 추가,

## 팀 내부 회의 진행 회차 및 일자

---

- 15회차(2022.09.07) 디스코드 타이레놀 방 (시간대 안맞는 인원들은 개별적으로 회의함)

## 현재까지 개발 과정 요약

---

### 기능 관련 구현

1. 운동시설 - 오현직
    - Ajax 로 GET 방식으로 받아오는 방식에 대해 데이터 가공이 어려웠던 점을 수정,
    - 기존의 URL 을 위되 경도 값으로 받아오는 방식에서 Gym Id 를 [/gym/getGymInfo/](http://localhost:8080/gym/getGymInfo/63){Gym Id} 로 변경
    - 각 헬스장 마자 불러오는 이용권 가격을 HTML Select 부분에 넣는 것으로 변경, 선택값에 따라 결제할 상품금액이 나오는것으로 수정 완료
    - 아임포트를 통해 결제 API 처리 ⇒ 정상적으로 결제 처리 되는것 확인, 아직 서버에 결제내역 저장은 안함
        
        ![image](https://user-images.githubusercontent.com/42793489/190980421-7662417a-c1e3-4f7b-b940-5d83cdc481a0.png)
        
    
2. 유저기능 - 홍승표(메인)
    - 비밀번호 찾기
        - 비밀번호 찾기 방식을 redis 를 통해 처리하는 것으로 변경
        
    - 유저 OAUTH 기능
        - 기존의 만들었던 코드에서 강사님 수업 내용을 토대로 OAUTH 를 수정
            
            ![image](https://user-images.githubusercontent.com/42793489/190980468-0e68bb51-e63e-4190-8069-0893877b1169.png)
            
3. CRUD 기능 게시판 구현 - 홍준표(메인)
    - 기존의 게시판 조회 시, 좋아요 받은 갯 수, 게시글 상세조회시 게시글 좋아요 할수 있는 기능 추가
        
        ![image](https://user-images.githubusercontent.com/42793489/190980782-63c4352d-f338-4d8c-a892-06534d208ab9.png)
            
4. 알림기능 구현 - 오현직
    - 채팅 구현 과정에서 이해하였던, 소캣 통신을 기반으로 만들었음
    - 작성한 게시글에 타 유저가 댓글을 달면 메시지가 나오는 초기 기능 구현
    - 영속성은 미 적용, 결제 API 적용 후 완성 할 예정

## 개발 과정에서 나왔던 질문

---

- 비밀번호 찾기 기능
    - 수업 시간에 배운 Redis 를 통해 인증번호를 가입한 이메일로 전송
    - 1시간 이내로 인증번호를 입력하여, 메모리에 저장된 데이터랑 일치 여부 확인
    - 일치 할 경우 비밀번호 변경, 1시간 이내로 확인을 못받을 경우, 자연 삭제
- 아임포트 를 통한 결제 API
    - 초기에 카카오 페이 결제 기능을 만들려고 했었음
    - 자료 찾아본 결과 아임포트를 통해 PG 사 결제 기능을 구현 할 수있었음
    - 영속성을 어떤 형식으로 지정을 해야하는것인가, 결제 환불 같은 결제 상황을 생각해보고 Entity 를 만들 예정임
- 프론트 UI
    - 프론트 관련해서 어떻게 진행을 할 것인지 팀 회의 결과, 남은님 께서 마무리 UI를 담당하시기로 함

## 개발 결과물 공유

---

> 인증샷
> 

![회의_4명_승표 제외](https://user-images.githubusercontent.com/42793489/190980897-cf07a1b1-da1e-4eb9-84ba-a8cb75a4b00b.PNG)

Github Repository URL: [https://github.com/likelion-backendschool/hellpyending](https://github.com/likelion-backendschool/hellpyending)

ERD cloud URL : [https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc](https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc)1
