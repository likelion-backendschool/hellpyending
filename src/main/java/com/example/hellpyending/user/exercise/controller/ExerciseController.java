package com.example.hellpyending.user.exercise.controller;

import com.example.hellpyending.user.service.UserService;
import com.example.hellpyending.user.entity.Users;
import com.example.hellpyending.user.entity.Exercise;
import com.example.hellpyending.user.exercise.dto.ExerciseCreateForm;
import com.example.hellpyending.user.exercise.service.ExerciseService;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequestMapping("/exercise")
@Controller
@RequiredArgsConstructor
public class ExerciseController {
    private final UserService userService;
    private final ExerciseService exerciseService;
    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    String list(Model model, Principal principal, @RequestParam(defaultValue = "") String sortCode , @RequestParam(defaultValue = "0") int page){
        if (principal==null) {
            return "access_error";
        }
        Users users = userService.getUser(principal.getName());

        Page<Exercise> paging = exerciseService.getList(users.getId(),page, sortCode);

        model.addAttribute("paging", paging);
        return "/user/exercise";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    String create_(Model model, Principal principal, @Valid ExerciseCreateForm exerciseCreateForm, BindingResult bindingResult){
        Users users = userService.getUser(principal.getName());
        String Dates_ = "%s-%s-%s".formatted(exerciseCreateForm.getYear(),exerciseCreateForm.getMonth(),exerciseCreateForm.getDay());
        LocalDate Dates = LocalDate.parse(Dates_, DateTimeFormatter.ISO_DATE);
        exerciseService.create(
                users,
                exerciseCreateForm.getDayOfWeek(),
                Dates,
                exerciseCreateForm.getType(),
                exerciseCreateForm.getIntensity(),
                exerciseCreateForm.getHour(),
                exerciseCreateForm.getCalorie()
        );
        return "redirect:/exercise/list/%d".formatted(users.getId());
    }
}
