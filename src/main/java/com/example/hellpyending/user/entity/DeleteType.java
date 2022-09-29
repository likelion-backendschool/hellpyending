package com.example.hellpyending.user.entity;

import lombok.Getter;
@Getter
public enum DeleteType {
    DELETE('Y'), // 삭제
    NORMAL('N'), // 정상
    DISABLE('D'); // 비활성화
    private char deleteType;
    private DeleteType(char deleteType) {
        this.deleteType = deleteType;
    }
}

