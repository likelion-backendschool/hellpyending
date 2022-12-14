package com.example.hellpyending.src.user.entity;


import com.example.hellpyending.src.chat.entity.ChatMessageEntity;
import com.example.hellpyending.src.chat.entity.ChatRoomUser;
import com.example.hellpyending.config.BaseTimeEntity;
import com.example.hellpyending.src.payment.entity.Payment;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(columnList = "userType")
})
public class Users extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @Column(length = 400, nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Column(length = 30, nullable = false, unique = true)
    private String nickname;

    @Column(length = 30)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column
    private Sex sex;

    private LocalDate birthday;

    // enum 표현하는 개선이 필요해보임.
    @Enumerated(EnumType.STRING)
    @Column(name = "delete_yn")
    private DeleteType deleteYn;


    // 광역시
    // ex) 인천, 서울, 부산 ...
    @Column
    private String address_1st;

    // 시군구
    // ex) 강화군, 서구, 중구, 미추홀구 ...
    @Column
    private String address_2st;

    // 동읍면리
    // ex) 간석동, 신현동, 관청리, 화정동 ...
    @Column
    private String address_3st;

    // 도로명
    // ex) 인천 서구 봉오재1로 36, 경기 고양시 덕양구 수원문산고속도로 51, 인천 부평구 평천로 559-3 ...
    @Column
    private String address_4st;

    // 상세 주소
    // ex) 905동 403호, 101동 1301호 ...
    @Column
    private String address_detail;

    @OneToMany(mappedBy = "users")
    private List<ChatRoomUser> chatRoomUsers = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<ChatMessageEntity> chatMessageEntities = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<Payment> payment = new ArrayList<>();

    public Users(String username, String password, List<GrantedAuthority> authorities) {
    }

    @PrePersist
    private void prePersist(){
        this.deleteYn = DeleteType.NORMAL;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users user)) return false;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Users(Long id, UserType userType, String email, String password, String username, String nickname, String phoneNumber, Sex sex, LocalDate birthday, DeleteType deleteYn, String address_1st, String address_2st, String address_3st, String address_4st, String address_detail) {
        this.id = id;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.birthday = birthday;
        this.deleteYn = deleteYn;
        this.address_1st = address_1st;
        this.address_2st = address_2st;
        this.address_3st = address_3st;
        this.address_4st = address_4st;
        this.address_detail = address_detail;
    }
}
