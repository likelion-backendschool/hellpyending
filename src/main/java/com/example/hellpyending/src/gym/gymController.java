package com.example.hellpyending.src.gym;

import com.example.hellpyending.article.exception.DataNotFoundException;
import com.example.hellpyending.src.gym.entity.GetAddressRes;
import com.example.hellpyending.src.gym.entity.GetAddressResInterface;
import com.example.hellpyending.user.UserService;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/gym")
@Controller
public class gymController {
    String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";
    String GEOCODE_USER_INFO = "KakaoAK 2419daa123eb8bb5f1117be4431f18bc";

    private final gymRepository gymRepository;
    private final gymService gymService;
    private final UserService userService;


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
    public GetAddressRes showList_new(@PathVariable("address_type") int address_type,  Principal principal) {


        Optional<Users> user = this.userService.findByUsername(principal.getName());

        if (user.isPresent()) {

            List<GetAddressResInterface> reslut = this.gymService.findyGymList(address_type,user.get().getAddress_1st(),user.get().getAddress_2st(),user.get().getAddress_3st());
            GetAddressRes getAddressRes = new GetAddressRes();
            getAddressRes.setPositions(reslut);
            return getAddressRes;

        } else {
            throw new DataNotFoundException("question not found");
        }


    }

    @RequestMapping("/search")
    public String searchGymList() {

        return "GymList";
    }

    @RequestMapping("/beforeGymList")
    public String beforeGymList() {

        return "beforeGymList";
    }





}


