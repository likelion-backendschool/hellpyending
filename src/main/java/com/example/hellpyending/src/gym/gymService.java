package com.example.hellpyending.src.gym;


import com.example.hellpyending.src.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class gymService {

    private final UserRepository userRepository;



}
