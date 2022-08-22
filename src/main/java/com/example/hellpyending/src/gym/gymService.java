package com.example.hellpyending.src.gym;


import com.example.hellpyending.src.gym.entity.GetAddressResInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class gymService {

    private final gymRepository gymRepository;


    public List<GetAddressResInterface> findyGymList(int address_type) {

        List<GetAddressResInterface> result = new ArrayList<>();
        if(address_type==1){
            result= this.gymRepository.findByGymList_1st();
        }
        if(address_type==2){
            result= this.gymRepository.findByGymList_2st();
        }
        if(address_type==3){
            result= this.gymRepository.findByGymList_3st();
        }
        return result;

    }
}
