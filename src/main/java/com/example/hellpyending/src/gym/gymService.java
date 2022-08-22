package com.example.hellpyending.src.gym;


import com.example.hellpyending.src.gym.entity.GetAddressResInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.hellpyending.user.entity.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class gymService {

    private final gymRepository gymRepository;


    public List<GetAddressResInterface> findyGymList(int address_type, String address_1st, String address_2st,String address_3st) {

        List<GetAddressResInterface> result = new ArrayList<>();
        if(address_type==1){
            result= this.gymRepository.findByGymList_1st(address_1st);
        }
        if(address_type==2){
            result= this.gymRepository.findByGymList_2st(address_1st,address_2st);
        }
        if(address_type==3){
            result= this.gymRepository.findByGymList_3st(address_1st,address_2st,address_3st);
        }
        return result;

    }
}
