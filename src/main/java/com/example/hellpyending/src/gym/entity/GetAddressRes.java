package com.example.hellpyending.src.gym.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAddressRes {
    private List<GetAddressResInterface> positions;
}
