# 2주차 위클리 진행사항

## 팀 구성원, 개인 별 역할

---

- 오현직 : 카카오 지도 API 를 통한 운동시설 조회
- 정종민 : CRUD 기반 게시판
- 지남은 : 카카오 지도 API 를 통한 운동시설 조회
- 홍승표 : 회원가입, 로그인
- 홍준표 : CRUD 기반 게시판

## 팀 내부 회의 진행 회차 및 일자

---

- 5회차(2022.08.08) 디스코드 타이레놀 방 (전원 참석)
- 6회차(2022.08.09) 디스코드 타이레놀 방 (전원 참석)
- 7회차(2022.08.10) 디스코드 타이레놀 방 (전원 참석)
    - 디스코드 타이레놀 방(16:00~18:00) (전원참석)
    - 디스코드 타이레놀 방(21:00~23:00) ((승표, 남은, 준표, 현직) 22:30 이후 종민 참석 )
- 8회차(2022.08.11) 디스코드 타이레놀 방 (전원 참석)
    - 디스코드 타이레놀 방(16:00~18:00) (전원참석)
    - 디스코드 타이레놀 방(22:00~23:00) (승표, 현직, 종민)

## 현재까지 개발 과정 요약 (최소 500자 이상)

---

현재까지 개발을 진행하면서 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점' 등을 요약하여 작성해 주세요 🙂

1. DB 
    1. ERD 설계 
        - 초기 진행했던 ERD 에서 추가 보완
        - 게시물 테이블에 있던 해시태그 칼럼을 따로 테이블로 관리를 위해 해시태그 테이블 생성
        - 게시물 이미지 관리를 위해 이미지 테이블 생성
        - 운동시설 관련 테이블 수정 및 보안
2. 협업
    1. 확정 방법(08/08)
        - 각자 맡은 작업에 대해 issue 생성
            
         
            
        - 맡은 작업에 대한 브랜치 생성
            
            ```
            브랜치 생성 양식
            {작업타입}/#{이슈번호} or {작업타입}/#{이슈번호}-{담당팀원}
            ex)feature/#11 or feature/#5-hyunjik
            ```
            
        - 작업 후 생성한 원격 feature 브랜치로 commit
            
            ```
            commit 생성 양식
            #{이슈번호} - {간략한 작업내역}
            
            {
            필요시 상세 작업 내역
                
                Co-authored-by: {name} <{email}>
                Co-authored-by: {name2} <{email}>
             }
            ```
            
        - 작업 한 브랜치를 dev 브랜치로 PR
        - 팀원들 코드리뷰, approve 후 PR 완료
        - PR 후 단위 개발 브랜치 삭제
        - 이후 작업 반복
3. 프로젝트 들어가기 위한 사전 공부
    1. 강의 교안 점프 투 스프링 부트 팀원 각자 독학 완료
    2. 게시판, 로그인 관련 처리에 대해서 최소한에 역량 확보
4. 프로젝트 목표
    
    ### **기능 별 버전 목표**
    
    1. 동네 게시판
        - V1 : 기본적인 crud, 댓글 기능
            - 게시글 등록, 수정, 삭제, 추천 할 수 있는 기능을 우선 구현 하는 것이 목표입니다.
            - 추천을 V2 로 넣으셔도 문제가 없습니다. 가장 중요한건 CRUD 입니다.
        - V2 : 사진 첨부 기능, 대댓글, 조회수 기능
            - 게시글에 사진을 첨부하는 기능 입니다. 사진 첨부는 여러 사이트에서 사용하는 이미지 첨부 기능을 통해 구현을 목표로 합니다.
            - 조회수 기능은 중복 조회수를 피하고 만드는 것이 목표입니다.
        - V3 : 사용자 1:1 웹 소켓을 통한 채팅기능
            - 아마 가장 어려운 기능이 아닐까 생각합니다. 상대방과 1대1 메시지를 통해 통신하는 기능입니다.
    2. 헬스장 검색 기능
        - V1 : 지도 기반 API를 통해 db 데이터 조회,
            - 조회 할 때 1/3/6/12 개월 별로 가격을 확인, PT 가격도 확인이 가능하게 한다.
        - V2 : 등록된 헬스장의 상세 페이지를 조회
            - 특정 운동시설을 평점 리뷰, Q&A 와 같은 기능을 구현 하는게 목표입니다. 게시판의 상세페이지와 비슷한 흐름으로 구성 될 것 같습니다.
        - V3 : 카카오페이 결제
            - 헬스장 이용권 또는 PT 이용권을 카카오페이 API를 통해 결제를 구현하는 것이 목표 아마 가장 힘들고 어려운 부분이 아닐까 싶습니다.

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

---

- 테이블에 delete_yn 칼럼 형식
    - 초기에 구상한 부분은 delete_yn = ‘Y’ or ‘N’
    - 새롭게 나온 의견 :  시간 형식으로 해서  삭제된 시간을 확인
    - 강사님 의견 : 어느것을 해도 상관 무
    - 결론 : 초기 구상 부분대로 진행
    
    ```java
    @Entity
    @Where(clause = "deleted = false")
    @SQLDelete(sql = "UPDATE posts SET deleted = true WHERE id = ?")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    ```
    
- 도메인 별 패키지 관리 여부
    - Article 도메인 에서 사용하는 entity(article, comment, img, hashtag 등등) 을 하나의 article 패키지에 관리

## 개발 결과물 공유

---

> 인증샷
> 

![08-08 회의](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/46a5d7bd-949a-4879-a4c1-4b31a387d687/08_08_%ED%9A%8C%EC%9D%98.png)

08-08 회의

![08-10 협업 관련 연습위한 zoom](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a5b94fed-88b5-49fc-b998-1cfaf9b2dc3e/08_10_%EC%A4%8C%ED%9A%8C%EC%9D%98.png)

08-10 협업 관련 연습위한 zoom

![08-16 회의 및 개발 단위 작업](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/dedf91df-a433-4e6f-a7f0-db84c48d9d0f/08_16%EB%AA%A8%EC%9D%B8%EC%82%AC%EC%A7%84.png)

08-16 회의 및 개발 단위 작업

Github Repository URL: [https://github.com/likelion-backendschool/hellpyending](https://github.com/likelion-backendschool/hellpyending)

ERD cloud URL : [https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc](https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc)1
