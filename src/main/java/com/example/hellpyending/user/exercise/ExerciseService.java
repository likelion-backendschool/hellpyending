package com.example.hellpyending.user.exercise;

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
    public Page<Exercise> getList(Long kw, int page, String sortCode) {
        List<Sort.Order> sorts = new ArrayList<>();

        switch (sortCode) {
            case "OLD" -> sorts.add(Sort.Order.asc("id")); // 오래된순
            default -> sorts.add(Sort.Order.desc("id")); // 최신순
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능
        System.out.println("exerciseRepository = " + exerciseRepository.countByAuthor_Id(kw));
        return exerciseRepository.findAll(pageable);
//        return exerciseRepository.findDistinctByAuthorContains(kw,pageable);
    }
}
