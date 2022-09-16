package com.example.hellpyending.config;

import com.example.hellpyending.user.entity.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class MemberContext extends User implements OAuth2User {
    private final Long id;
    private final String email;
    private Map<String, Object> attributes;
    private String userNameAttributeName;


    public MemberContext(Users users, List<GrantedAuthority> authorities) {
        super(users.getUsername(), users.getPassword(), authorities);
        this.id = users.getId();
        this.email = users.getEmail();
    }

    public MemberContext(Users users, List<GrantedAuthority> authorities, Map<String, Object> attributes, String userNameAttributeName) {
        this(users, authorities);
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return super.getAuthorities().stream().collect(Collectors.toSet());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.getAttribute(this.userNameAttributeName).toString();
    }
}
