package com.snehasish.microservice.question.service;

import com.snehasish.microservice.question.dto.QuestionDto;
import com.snehasish.microservice.question.dto.QuizResponse;
import com.snehasish.microservice.question.dto.Response;

import java.util.List;

public interface QuestionService {

    Response<QuestionDto> addQuestion(QuestionDto questionDto);

    Response<List<QuestionDto>> getAllQuestions();

    Response<QuestionDto> getQuestionById(Long id);

    Response<QuestionDto> updateQuestion(Long id, QuestionDto questionDto);

    Response<String> deleteQuestion(Long id);

    Response<List<Long>> generateQuestionsForQuiz(String category, Integer numOfQuestions);

    Response<List<QuestionDto>> getQuestionsFromIds(List<Long> ids);

    Response<Integer> getScore(List<QuizResponse> quizResponses);

    Response<List<QuestionDto>> getQuestionsByCategory(String category);
}
