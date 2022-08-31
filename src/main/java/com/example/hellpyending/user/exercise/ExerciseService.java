package com.example.hellpyending.user.exercise;

import com.example.hellpyending.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    public Page<Exercise> getList(Long id, int page, String sortCode) {
        List<Sort.Order> sorts = new ArrayList<>();

        switch (sortCode) {
            case "OLD" -> sorts.add(Sort.Order.asc("id")); // 오래된순
            default -> sorts.add(Sort.Order.desc("id")); // 최신순
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능-
        return exerciseRepository.findByAuthor_Id(id, pageable);
    }

    public void create(Users users, String dayOfWeek, String dates, String type, String intensity, Integer hour, Integer calorie) {
        Exercise exercise = Exercise.builder().
                author(users).
                DayOfWeek(dayOfWeek).
                dates(dates).
                Type(type).
                Intensity(intensity).
                hour(hour).
                calorie(calorie).
                build();
        exerciseRepository.save(exercise);
    }
}
