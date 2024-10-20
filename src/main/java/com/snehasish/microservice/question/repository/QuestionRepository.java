package com.snehasish.microservice.question.repository;

import com.snehasish.microservice.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCategoryIgnoreCase(String category);

    @Query(value = "SELECT q.id FROM Question q WHERE LOWER(q.category) = LOWER(:category) ORDER BY RANDOM() LIMIT :numOfQuestions")
    List<Long> getRandomQuestionIdsForQuiz(@Param("category") String category, @Param("numOfQuestions") Integer numOfQuestions);

}
