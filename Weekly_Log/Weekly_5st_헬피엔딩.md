# 5주차 위클리 진행사항

## 팀 구성원, 개인 별 역할

---

- 오현직 : 잡무 담당(운동시설 상세조회, 게시물, 유저 )
- 지남은 : 카카오 지도 API 를 통한 운동시설 조회
- 홍승표 : 회원가입, 로그인, 유저 마이페이지, 운동일지 작성
- 홍준표 : CRUD 기반 게시판, 이미지 파일 업로드

## 팀 내부 회의 진행 회차 및 일자

---

- 11회차(2022.08.29) 디스코드 타이레놀 방 (전원 참석)
- 12회차(2022.08.29) 디스코드 타이레놀 방 (전원 참석)

## 현재까지 개발 과정 요약

---

### 기능 관련 구현

1. 지도 api - 오현직, 지남은 
    - 게시글 조회 페이지 마커와 리스트 형태의 정보를 클릭시, 상세페이지로 넘어가는것 확인
        
        ![Untitled](https://user-images.githubusercontent.com/42793489/188432747-bc67e398-5c7c-4665-b7d2-ee528a728a07.png)

        
    - 문제점 : 조회 페이지에서 지도 기반 마커를 클릭하여 운동상세 페이지 확인 하는 경우, 다시 운동 조회로 넘어갈시, 마커가 계속 고정이 되는 것으로 확인
2. 유저(로그인 회원가입) - 홍승표(메인)
    - 마이페이지 기능
        - 운동일지
            ![Untitled](https://user-images.githubusercontent.com/42793489/188432747-bc67e398-5c7c-4665-b7d2-ee528a728a07.png)
            

            
            
            - 각 일자마다 운동종류에 따라 일지를 작성 할 수 있음
            - 문제점 : 운동종류를 장문으로 작성시 테이블이 양식 가독성이 안 좋음
                ![Untitled (1)](https://user-images.githubusercontent.com/42793489/188433228-7da41125-f91b-4c15-ad6d-dc38ff47a0d8.png)
                
                
        - 유저정보 수정
            - 닉네임 변경 시 중복 여부 체크
                
                ![Untitled (2)](https://user-images.githubusercontent.com/42793489/188433528-b1158b11-5457-4195-b28c-45d68b28a67f.png)
                
            - AJAX 를 통한 비밀번호 변경 시 동일성 여부 체크
                
                ![Untitled (3)](https://user-images.githubusercontent.com/42793489/188433547-d9965275-8bc2-4000-b5e4-4bf60a513521.png)
                
            - 정부 수정 완료시 로그인 세션과 상관없이 로그아웃됨
            - 사용자 인가되지 않은 페이지로 갈 시, whitelabel 페이지로 나오는 부분 수정
        - 내가 쓴 글
            - 유저가 작성한 게시글 조회 기능
                
                ![Untitled (4)](https://user-images.githubusercontent.com/42793489/188433567-835505b7-a994-4c44-90d5-8399f7f1a976.png)
                
    
3. CRUD 기능 게시판 구현 - 홍준표(메인), 오현직(서브)
    - 게시글 작성 시, 이미지, 해시태그 작성 가능
        - 게시글 등록시 이미지 업로드 기능, 해시태그 기능
            
            ![Untitled (5)](https://user-images.githubusercontent.com/42793489/188433591-6108eb4a-3537-45df-a368-6608594a8e8a.png)
            
            - 게시글 작성 시, 해시태그와 이미지를 첨부 가능, 이미지 같은경우는 AWS S3로 저장, 해시태그는 n:m 관계로 DB 에 저장
                
                ![Untitled (6)](https://user-images.githubusercontent.com/42793489/188433612-8c792f5e-8b5d-46d5-b243-1d82733ffb3b.png)
                
            
        - 게시판 대댓글 기능(준표)
    

## 개발 과정에서 나왔던 질문

---

- 게시글 조회시 등록한 이미지 list 불러올때 오류가 나는것 확인
    - 디버깅 수행 시 이미지 리스트인 ArticleImgList를 불러올때  `unable to evaluate the expression method threw 'org.hibernate.lazyinitializationexception' exception` 에러메시지 확인
    - 확인 :  application.yaml 에서 open-in-view 상태를 false를 함으로 이미지리스트 불러오기전에 sql 연결이 끊어짐으로 발생하는 현상
    - 조치 : open-in-view : true 로 수정 하여 view 단에서도 sql 연결이 끊어지지 않게 조치
- 게시글 이미지 관리
    - 로컬로 저장하는 형태로 관리를 하다, 이후 유지보수를 보다 원활하게 하기 위해 아마존 S3 를 통해 관리 하기로 협의,
- AJAX VS 리다이렉션
    - 운동일지, 유저 개인정보 수정AJAX 와 같은 비동기식 방식을 통해 처리하는 방법과, 리다이렉션 으로 처리하는 방법중에 고민을 함
    - 강사님께 조언을 구한 결과 , 운동일지 작성 방법은, 리다이렉션으로 처리를 하라 조언(충분히 리다이렉션으로 해도 괜찮은 성능이 나온다.),
    - 운동 일지를 보다 더 가독성있게 하기위해, 운동상세 정보 부분을 토글로 하여 이후 기능 구현할 때 크게 기획에서 벗어나지 않게 함을 목표를 두기로 함(현직 담당)
- git 협업 이슈
    - 이전에 정상작동 하는 코드를 PR 하고 이후에 다시 확인해본 결과, 이전 코드로 되돌아 간것 확인
    - 확인 결과, 협업을 하던 팀원중 한명이, Dev 에 있던 최신 파일을 PULL 안하고, PR 시도 하여 이전 코드로 되돌아 갔던것 확인
    - 다시 한번 팀원들한테  PR 할때, dev 파일 최신화 한 다음에 PR 하기로 약속

## 개발 결과물 공유

---

> 인증샷
> 

Github Repository URL: [https://github.com/likelion-backendschool/hellpyending](https://github.com/likelion-backendschool/hellpyending)

ERD cloud URL : [https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc](https://www.erdcloud.com/d/9MkKfoh3uEQGo2Zsc)1
