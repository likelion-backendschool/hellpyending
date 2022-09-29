## 팀 구성원

---

[팀장] 오현직, 지남은, 홍승표, 홍준표

## 회고 내용 요약 (최소 500자 이상)
팀원들과 함께 복습을 진행하면서 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점' 등을 요약하여 작성해 주세요 🙂


### 스프링시큐리티 -인증 개념 이해(Authentication)

### ****Authentication 인증객체란?****

- **당신이 누구인지 증명하는 것**
    - 사용자의 인증 정보를 저장하는 토큰 개념으로 사용한다.
    - **인증 시** id와 password를 담고 인증 검증을 위해 전달되어 사용된다.
    - **인증 후** 최종 인증 결과(user 객체, 권한 정보)를 담고 SecurityContext 에 저장되어 전역적으로 참조가 가능하다.

```java
Authentication authentication = 
	SecurityContexHolder.getContext().getAuthentication();
```

- **Authentication 객체의 구조**
1. **principal**: 사용자 아이디 혹은 User객체를 저장
2. **credentials**: 사용자 비밀번호
3. **authorities**: 인증된 사용자의 권한 목록
4. **details**: 인증 부가 정보
5. **Authenticated**: 인증 여부(Bool)



### ****Flow****

그럼 이 Authentication이라는 인증객체가 클라이언트의 요청시 사용이 되고, 인증이 끝나고 활용되는지에 대해 다음과 같이 그림으로 보여드리겠습니다.

![image.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2a6a398a-9051-4d1e-b14e-5bd24a33f224/image.png)

1. 사용자가 로그인을 시도(username + password 입력 및 전달)
2. **usernamePasswordAuthenticationFilter**(인증필터)가 요청정보를 받아서 정보 추출을 하여 인증객체 (Authentication)을 생성한다.
3. **AuthenticationManager**가 인증객체를 가지고 인증처리를 수행한다.
    
    a. *인증이 실패하게 되면 예외 발생.*
    
4. 인증 성공 후 Authentication 인증객체를 만들어서 내부의 Principal, Credentials, Authorities, Authenticated 들을 채워넣는다.
5. **SecurityContextHolder**객체 안의 SecurityContext에 저장한다.
→ 인증객체를 전역적으로 사용할 수 있게 된다.


---


✅ 지도 api 기반 운동시설 검색 기능 
```java
  if (radio.value == "1st_address") {
                        search_condition = "/gym/getGymList/1"
                        searchPlaces(search_condition);
                    }
                    if (radio.value == "2st_address") {
                        search_condition = "/gym/getGymList/2"
                        searchPlaces(search_condition);
                    }
                    if (radio.value == "3st_address") {
                        search_condition = "/gym/getGymList/3"
                        searchPlaces(search_condition);
                    }
                    alert(radio.value);                
                  
```


```java
  // 지도에 표시되고 있는 마커를 제거합니다
            removeMarker();
            console.log(places.length)
            for ( var i=0; i<places.length; i++ ) {
                console.log(i);
                // 마커를 생성하고 지도에 표시합니다
                var placePosition = new kakao.maps.LatLng(places[i].lat, places[i].lng),
                    marker = addMarker(placePosition, i),
                    itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

```


                 

✅ 동네 게시판 수정

```java
@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String articleCommentModify(@Valid ArticleCommentForm articleCommentForm, BindingResult bindingResult, @PathVariable("id") Long id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "article_detail";
        }

        ArticleComment articleComment = articleCommentService.getArticleComment(id);

        if (!articleComment.getUsers().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        articleCommentService.modify(articleComment, articleCommentForm.getContent());

        return "redirect:/article/detail/%d".formatted(articleComment.getArticle().getId(), articleComment.getId());
    }
}
```

## 회고 과정에서 나왔던 질문 (최소 200자 이상)

#### git 충돌을 해결과정
    
   ### 팀 협업방식
  
   - 담당 기능별로 브랜치 (#issue)를 만들어 작업을 시작한다
   - 작업이 마무리되면 pr를 하고, 팀원들은 리뷰를 본 뒤 1명 이상이 본 후에 승인을 한다
   - 이를 확인하고 머지한다 
    
    
   📢 브랜치 생성 양식
    {작업타입}/#{이슈번호} or {작업타입}/#{이슈번호}-{담당팀원}
    ex)feature/#11 or feature/#5-hyunjik
  
  
   📢 commit 생성 양식
    #{이슈번호} - {간략한 작업내역}

    {
    필요시 상세 작업 내역

        Co-authored-by: {name} <{email}>
        Co-authored-by: {name2} <{email}>
     }
  
  
   ### 문제 상황
   
   - main 브런치에 잘못 push를 한 경우 
   - 머지가 잘못 이뤄진 경우, main에 그 코드가 병합이 되었다.
   - 다른 생각없이 로컬 저장소로만 설정파일들(새로운파일은 제외하고)만 커밋그리고 fetch와 checkout을 진행하려니 에러메시지가 떴다.
    
    
   ### 원인 분석 (Merge Conflict해결 원리)
    
     conflict가 발생한 Upstream의 브랜치를 로컬에 pull하여 로컬에서 해결하는 방식
     로컬에서 conflict가 난 곳을 확인하여 accept하면서 merge (merge conflict해결)
     로컬에서 최종적으로 merge된 브랜치를 push


   ### 해결
    
    conflict가 발생한 상대방의 브랜치(= Upstream의 develop 브랜치)를,  자신의 Local Repository의 develop에 pull
    conflict가 발생한 자신의 브랜치로 check out한 다음, 자신의 develop브랜치로 rebase or merge
    merge conflict난 곳을 해결 (accept or deny) - 맥북의 경우 vscode에 conflict난 파일을 열으면, 어떤 부분을 accept할지 쉽게 선택 가능하다.


   ### Conflict 해결 중 실패한 경우 되돌아 가는 방법

      소스트리와 같은 경우, conflict해결 로그 중간이 안찍히는 경우가 존재하므로 terminal창에서 해결

      커밋번호 확인: git reflog
      리셋: git reset --hard <커밋번호>
      예제) git reset —hard f6f9fd1d


## 회고 인증샷 & 팀 자랑

---

- 회고 인증샷



## 💻14조💻의 팀 자랑멘트

팀원들과의 협력이 좋아서 서로 간의 의사소통이 원활하게 진행된다!
서로의 대한 피드백이 구체적이고 상대방을 배려할 줄 안다!
