package com.snehasish.microservice.question.controller;

import com.snehasish.microservice.question.dto.QuestionDto;
import com.snehasish.microservice.question.dto.Response;
import com.snehasish.microservice.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/ping")
    public String ping() {
        return "pong from question";
    }

    @GetMapping("")
    public ResponseEntity<Response<List<QuestionDto>>> getAllQuestions() {
        Response<List<QuestionDto>> res = questionService.getAllQuestions();
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<QuestionDto>> getQuestionById(@PathVariable Long id) {
        Response<QuestionDto> res = questionService.getQuestionById(id);
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PostMapping("")
    public ResponseEntity<Response<QuestionDto>> addQuestion(@RequestBody QuestionDto questionDto) {
        Response<QuestionDto> res = questionService.addQuestion(questionDto);
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<QuestionDto>> updateQuiz(@PathVariable Long id, @RequestBody QuestionDto questionDto) {
        Response<QuestionDto> res = questionService.updateQuestion(id, questionDto);
        return new ResponseEntity<>(res, res.getStatus());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> deleteQuiz(@PathVariable Long id) {
        Response<String> res = questionService.deleteQuestion(id);
        return new ResponseEntity<>(res, res.getStatus());
    }

}
