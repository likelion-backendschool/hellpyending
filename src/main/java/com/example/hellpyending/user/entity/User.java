package com.example.hellpyending.user.entity;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(columnList = "userType")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @Column(length = 400, nullable = false, unique = true)
    private String email;

    @Column(length = 30)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column
    private Sex sex;

    private LocalDate birthday;

    @Column(name = "delete_yn")
    private char deleteYn;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String address_1st;

    @Column(nullable = false)
    private String address_2st;

    @Column(nullable = false)
    private String address_3st;

    @Column(nullable = false)
    private String address_4st;

    @Column(nullable = false)
    private String address_detail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
