package com.example.hellpyending.config;

import com.example.hellpyending.src.user.entity.Users;

import java.util.Optional;

public class Util {
    public static String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
    public static Users userContextSave(Optional<Users> users_2, String username, String email) {
        // email 연동
        if (users_2.isPresent()){
            return Users.builder()
                    .username(users_2.get().getUsername())
                    .password("")
                    .email(users_2.get().getEmail())
                    .build();
        } else {
            return Users.builder()
                    .username(username)
                    .password("")
                    .email(email)
                    .build();
        }
    }
}
