package com.example.hellpyending.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFindService {
    private final UserRepository userRepository;
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String name) {
        return userRepository.existsByUsername(name);
    }
}
