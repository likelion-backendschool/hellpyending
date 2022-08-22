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
}