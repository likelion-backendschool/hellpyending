package com.example.hellpyending.config;

import com.example.hellpyending.user.entity.Users;

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
    public static Users userContextSave(Optional<Users> users_1, Optional<Users> users_2, Optional<Users> users_3, String username, String nickname, String email) {
        // 1 2 3
        if (users_1.isPresent() && users_2.isPresent() && users_3.isPresent()){
            return Users.builder()
                    .username(users_1.get().getUsername())
                    .password("")
                    .email(users_2.get().getEmail())
                    .nickname(users_3.get().getNickname())
                    .build();
        }
        // 1 x x
        else if(users_1.isPresent() && !users_2.isPresent() && !users_3.isPresent()){
            return Users.builder()
                    .username(users_1.get().getUsername())
                    .password("")
                    .email(email)
                    .nickname(nickname)
                    .build();
        }
        // x 2 x
        else if(!users_1.isPresent() && users_2.isPresent() && !users_3.isPresent()){
            return Users.builder()
                    .username(username)
                    .password("")
                    .email(users_2.get().getEmail())
                    .nickname(nickname)
                    .build();
        }
        // x x 3
        else if(!users_1.isPresent() && !users_2.isPresent() && users_3.isPresent()){
            return Users.builder()
                    .username(username)
                    .password("")
                    .email(email)
                    .nickname(users_3.get().getNickname())
                    .build();
        }
        // x o o
        else if (!users_1.isPresent() && users_2.isPresent() && users_3.isPresent()) {
            return Users.builder()
                    .username(username)
                    .password("")
                    .email(users_2.get().getEmail())
                    .nickname(users_3.get().getNickname())
                    .build();
        }
        // o o x
        else if (users_1.isPresent() && users_2.isPresent() && !users_3.isPresent()) {
            return Users.builder()
                    .username(users_1.get().getUsername())
                    .password("")
                    .email(users_2.get().getEmail())
                    .nickname(nickname)
                    .build();
        }
        // o x o
        else if (users_1.isPresent() && !users_2.isPresent() && users_3.isPresent()) {
            return Users.builder()
                    .username(users_1.get().getUsername())
                    .password("")
                    .email(email)
                    .nickname(users_3.get().getNickname())
                    .build();
        } else {
            return Users.builder()
                    .username(username)
                    .password("")
                    .email(email)
                    .nickname(nickname)
                    .build();
        }
    }
}
