package com.example.hellpyending.src.exercise.repository;

import com.example.hellpyending.src.user.entity.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Page<Exercise> findDistinctByAuthorContains(String kw, Pageable pageable);

    Page<Exercise> findByAuthor_Id(Long Author_Id ,Pageable pageable);



}
