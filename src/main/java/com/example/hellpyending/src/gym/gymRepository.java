package com.example.hellpyending.src.gym;

import com.example.hellpyending.src.gym.entity.GetAddressRes;
import com.example.hellpyending.src.gym.entity.GetAddressResInterface;
import com.example.hellpyending.src.gym.entity.Gym;
import com.example.hellpyending.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface gymRepository extends JpaRepository<Gym, Integer> {

    List<Gym> findAll();


    @Query(nativeQuery = true,value = "select g.gym_name, g.lat,g.lng,g.gym_address,g.gym_phone_number,g.per1_month,g.per3_months,g.per6_months,g.per12_months from gym as g")
    List<GetAddressResInterface> findyGymList();
    @Query(nativeQuery = true,value = "select g.gym_name, g.lat,g.lng,g.gym_address,g.gym_phone_number,g.per1_month,g.per3_months,g.per6_months,g.per12_months from gym as g where address_1st=:address_1st")
    List<GetAddressResInterface> findByGymList_1st(@Param("address_1st")String address_1st);
    @Query(nativeQuery = true,value = "select g.gym_name, g.lat,g.lng,g.gym_address,g.gym_phone_number,g.per1_month,g.per3_months,g.per6_months,g.per12_months from gym as g where address_1st=:address_1st and address_2st=:address_2st")
    List<GetAddressResInterface> findByGymList_2st(@Param("address_1st")String address_1st, @Param("address_2st")String address_2st);
    @Query(nativeQuery = true,value = "select g.gym_name, g.lat,g.lng,g.gym_address,g.gym_phone_number,g.per1_month,g.per3_months,g.per6_months,g.per12_months from gym as g where address_1st=:address_1st and address_2st=:address_2st and address_3st=:address_3st")
    List<GetAddressResInterface> findByGymList_3st( @Param("address_1st")String address_1st, @Param("address_2st")String address_2st,@Param("address_3st")String address_3st);
    @Query(nativeQuery = true,value = "SELECT g.gym_name, g.lat,g.lng,g.gym_address,g.gym_phone_number,g.per1_month,g.per3_months,g.per6_months,g.per12_months  FROM gym as g WHERE g.lat = ?1 AND g.lng = ?2")
    GetAddressResInterface findByLatAndLng(double lat, double lng);
}