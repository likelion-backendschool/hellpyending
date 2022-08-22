package com.example.hellpyending.src.gym;

import com.example.hellpyending.src.gym.entity.GetAddressRes;
import com.example.hellpyending.src.gym.entity.GetAddressResInterface;
import com.example.hellpyending.src.gym.entity.Gym;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@RequestMapping("/gym")
@Controller
public class gymController {
    String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";
    String GEOCODE_USER_INFO = "KakaoAK 2419daa123eb8bb5f1117be4431f18bc";

    private final gymRepository gymRepository;
    private final gymService gymService;



    @ResponseBody
    @GetMapping("/getGymList")
    public GetAddressRes showList() {
        List<GetAddressResInterface> reslut = this.gymRepository.findyGymList();
        GetAddressRes getAddressRes = new GetAddressRes();
        getAddressRes.setPositions(reslut);
        return getAddressRes;

    }


    @ResponseBody
    @GetMapping("/getGymList/{address_type}")
    public GetAddressRes showList_new(@PathVariable("address_type") int address_type, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            System.out.println("error");
        }
        List<GetAddressResInterface> reslut = this.gymService.findyGymList(address_type);
        GetAddressRes getAddressRes = new GetAddressRes();
        getAddressRes.setPositions(reslut);
        return getAddressRes;

    }

    @RequestMapping("/search")
    public String searchGymList() {



        return "gymList_from_kakaoMap";
    }

}


