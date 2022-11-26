package com.example.hellpyending.src.gym;

import com.example.hellpyending.src.article.exception.DataNotFoundException;
import com.example.hellpyending.src.payment.service.paymentService;
import com.example.hellpyending.src.gym.entity.GetAddressRes;
import com.example.hellpyending.src.gym.entity.GetAddressResInterface;

import com.example.hellpyending.src.gym.entity.Gym;
import com.example.hellpyending.src.user.entity.Users;
import com.example.hellpyending.src.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final paymentService paymentService;


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
    public GetAddressRes showList_new(@PathVariable("address_type") int address_type, Principal principal) {

        System.out.println("GET HERE");
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
    public String searchGymList(Principal principal) {

        if (principal == null) {
            return "access_error";
        }

        return "/gym/GymList";
    }




    @GetMapping("/getGymInfo/{gym_id}")
    public String getGymInfo(@PathVariable("gym_id") long gym_id, Model model, Principal principal) {
        if (principal == null) {
            return "access_error";
        }
        Gym gym = gymService.findGymById(gym_id);
        Users user = userService.getUser(principal.getName());
        model.addAttribute("gym", gym);
        model.addAttribute("user", user);
        return "/gym/GymInfo";
    }

    @ResponseBody
    @GetMapping("/displayGymInfo/{gym_lat}/{gym_lng}")
    public GetAddressResInterface getGymInfo(@PathVariable("gym_lat") double gym_lat, @PathVariable("gym_lng") double gym_lng,  Principal principal) {

        System.out.println("GET HERE");
        Optional<Users> user = this.userService.findByUsername(principal.getName());

        if (user.isPresent()) {
            return this.gymService.findByLatAndLng(gym_lat, gym_lng);
        } else {
            throw new DataNotFoundException("question not found");
        }
    }


}
