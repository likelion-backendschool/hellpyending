package com.example.hellpyending.user.exercise;

import com.example.hellpyending.user.UserService;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
@RequestMapping("/exercise")
@Controller
@RequiredArgsConstructor
public class ExerciseController {
    private final UserService userService;
    private final ExerciseService exerciseService;
    @GetMapping("/list/{id}")
    @PreAuthorize("isAuthenticated()")
    String list(Model model, Principal principal, @PathVariable Long id, @RequestParam(defaultValue = "") String sortCode , @RequestParam(defaultValue = "0") int page){
        Users users = userService.getUser(principal.getName());

        if (!users.getId().equals(id)) {
            return "access_error";
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "읽을권한이 없습니다.");
        }
        Page<Exercise> paging = exerciseService.getList(id,page, sortCode);

        model.addAttribute("paging", paging);
        return "/user/exercise";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    String create_(Model model, Principal principal, @Valid ExerciseCreateForm exerciseCreateForm, BindingResult bindingResult){
        Users users = userService.getUser(principal.getName());

        exerciseService.create(
                users,
                exerciseCreateForm.getDayOfWeek(),
                exerciseCreateForm.getDates(),
                exerciseCreateForm.getType(),
                exerciseCreateForm.getIntensity(),
                exerciseCreateForm.getHour(),
                exerciseCreateForm.getCalorie()
        );
        return "redirect:/exercise/list/%d".formatted(users.getId());
    }
}
