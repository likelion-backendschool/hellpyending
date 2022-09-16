package com.example.hellpyending.config;

import com.example.hellpyending.user.entity.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class MemberContext extends User {


    public MemberContext(Users users, List<GrantedAuthority> authorities) {
        super(users.getUsername(), users.getPassword(), authorities);
    }
}
