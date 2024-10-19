package com.snehasish.microservice.question.repository;

import com.snehasish.microservice.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
