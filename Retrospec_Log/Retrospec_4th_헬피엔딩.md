## 팀 구성원

---

[팀장] 오현직, 지남은, 홍승표, 홍준표

## 회고 내용 요약 (최소 500자 이상)
팀원들과 함께 복습을 진행하면서 ‘기술적으로 새로 알게된 점, 어려웠던 점, 아쉬운 점' 등을 요약하여 작성해 주세요 🙂


### JPA와 Querydsl

#### JPA 리포지토리와 Querydsl

- **jpa 리포지토리**
```java
package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberJpaRepository2 {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username ", Member.class)
                .setParameter("username", username)
                .getResultList();
    }
}
```

- **JPA 테스트코드**
```java
package study.querydsl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;
import study.querydsl.repository.MemberJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired EntityManager em;
    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest() throws Exception{
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> result1 = memberJpaRepository.findAll_Querydsl();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberJpaRepository.findByUsername("member1");
        assertThat(result2).containsExactly(member);

    }
```
### Querydsl사용

- **JPA 리포지토리 - Querydsl 추가**
```java
public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class).getResultList();
}

public List<Member> findAll_Querydsl() {
    return queryFactory
            .selectFrom(member)
            .fetch();
}

public List<Member> findByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

public List<Member> findByUsername_Querydsl(String username) {
    return queryFactory
            .selectFrom(member)
            .where(member.username.eq(username))
            .fetch();
}
```

- **Querydsl 테스트코드 추가**
```java
@Test
public void basicQuerydslTest() throws Exception{
    Member member = new Member("member1", 10);
    memberJpaRepository.save(member);

    Member findMember = memberJpaRepository.findById(member.getId()).get();
    assertThat(findMember).isEqualTo(member);

    List<Member> result1 = memberJpaRepository.findAll_Querydsl();
    assertThat(result1).containsExactly(member);

    List<Member> result2 = memberJpaRepository.findByUsername_Querydsl("member1");
    assertThat(result2).containsExactly(member);
}
```
#### JPAQueryFactory 스프링 빈 등록
`JPAQueryFactory` 를 스프링 빈으로 등록해서 주입받아 사용해도 된다.

```java
@Bean
JPAQueryFactory jpaQueryFactory(EntityManager em){
		return new JPAQUeryFactory(em);
}
```

```
참고: 동시성 문제는 걱정하지 않아도 된다. 
여기서 스프링이 주입해주는 엔티티매니저(em)는 실제 동작 시점에 진짜 엔티티 매니저를 찾아주는 프록시용 가짜 엔티티 매니저이다.
이 가자 엔티티 매니저는 실제 사용 시점에 트랜잭션 단위로 실제 엔티티 매니저(영속성 컨텍스트)를 할당해준다. 
```
### QueryDSL의 장점
1. 문자가 아닌 코드로 쿼리를 작성함으로써, 컴파일 시점에 문법 오류를 쉽게 확인할 수 있다.
2. 자동 완성 등 IDE의 도움을 받을 수 있다.
3. 동적인 쿼리 작성이 편리하다.
4. 쿼리 작성 시 제약 조건 등을 메서드 추출을 통해 재사용할 수 있다.

## 회고 과정에서 나왔던 질문 (최소 200자 이상)

#### 대댓글 엔티티 설계 과정

### 문제 상황
1.cascade 옵션을 사용하지 않고 대댓글을 save 한 뒤 리턴되는 대댓글 영속성 객체를 댓글 영속성 객체의 리스트에 add 해주는 방식과 cascade all을 해주고 save 없이 넣어주는 방식을 고민 했습니다.
2.대댓글 뎁스를 원하는만큼 넣는 방식을 고민했습니다.

### 원인 분석및 해결
1. cascade 옵션을 사용해서 save 없이 해도 괜찮지만 처음 만들어보는 기능이라 정석대로 하는 것이 좋을 것 같아 cascade 옵션 없이 코딩을 해보았습니다.
2. 대댓글 뎁스를 원하는 만큼 넣기 위해 대댓글을 관리하기 위한 테이블을 만들어야할지 아니면 boardId 값을 null 이나 -1 처리를해서 댓글과 대댓글을 하나의 컴포넌트로 만들어서 기능을 구현하는 방식을 사용할지
   팀원들과 회의를 해보았습니다. 그 결과 boardId가 실제 게시판 번호라면 oneToMany에 걸려 대댓글이 게시글의 댓글처럼 나오게되는데 그것을 방지하기 위해 대댓글의 boardId를 null 처리하는 방식을 채택해서 진행했습니다.








## 회고 인증샷 & 팀 자랑

---

- 회고 인증샷



## 💻14조💻의 팀 자랑멘트

제가 부족한 부분을 팀원들과의 회의를 통해 구체적인 피드백으로 몰랐던 부분을 배우고 또 반대로 서로의 피드백을 유연하게 받아들이는 멋진 팀입니다.
