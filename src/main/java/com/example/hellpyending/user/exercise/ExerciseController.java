package com.example.hellpyending.user.exercise;

import com.example.hellpyending.user.UserService;
import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.net.BindException;
import java.security.Principal;
@RequestMapping("/exercise")
@Controller
@RequiredArgsConstructor
public class ExerciseController {
    private final UserService userService;
    private final ExerciseService exerciseService;
    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    String list(Model model, Principal principal, @RequestParam(defaultValue = "") String sortCode , @RequestParam(defaultValue = "0") int page){
        Users users = userService.getUser(principal.getName());

        Page<Exercise> paging = exerciseService.getList(users.getId(),page, sortCode);

        model.addAttribute("paging", paging);
        return "user_exercise";
    }
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    String create_(Model model, Principal principal, @Valid ExerciseCreateForm exerciseCreateForm, BindingResult bindingResult){
        Users users = userService.getUser(principal.getName());

        exerciseService.create(
                exerciseCreateForm.getDayOfWeek(),
                exerciseCreateForm.getDates(),
                exerciseCreateForm.getType(),
                exerciseCreateForm.getIntensity(),
                exerciseCreateForm.getHour(),
                exerciseCreateForm.getCalorie()
        );
        return "redirect:/exercise/list";
    }
}
