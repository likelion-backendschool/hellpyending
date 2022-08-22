package com.example.hellpyending.src.gym;

import com.example.hellpyending.src.gym.entity.GetAddressResInterface;
import com.example.hellpyending.src.gym.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface gymRepository extends JpaRepository<Gym, Integer> {

    List<Gym> findAll();


    @Query(nativeQuery = true,value = "select g.gym_name, g.lat,g.lng from gym as g")
    List<GetAddressResInterface> findyGymList();


    @Query(nativeQuery = true,value = "select g.gym_name, g.lat,g.lng from gym as g where address_1st='서울'")
    List<GetAddressResInterface> findByGymList_1st();


    @Query(nativeQuery = true,value = "select g.gym_name, g.lat,g.lng from gym as g where address_1st='서울' and address_2st='용산구'")
    List<GetAddressResInterface> findByGymList_2st();


    @Query(nativeQuery = true,value = "select g.gym_name, g.lat,g.lng from gym as g where address_1st='서울' and address_2st='용산구' and address_3st='남영동'")
    List<GetAddressResInterface> findByGymList_3st();
}