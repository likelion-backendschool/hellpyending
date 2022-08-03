package com.example.project_version_init.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserType userType;

    @Column(length = 400, unique = true)
    @NotNull
    private String email;

    @Column(length = 30)
    private String password;

    @Column(length = 20)
    @NotNull
    private String name;

    @Column(length = 30, unique = true)
    @NotNull
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column
    private Sex sex;

    private LocalDate birthday;

    private String address;

    @ColumnDefault("0")
    private Boolean deleteYn;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
