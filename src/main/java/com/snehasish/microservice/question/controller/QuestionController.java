package com.snehasish.microservice.question.controller;

import com.snehasish.microservice.question.dto.QuestionDto;
import com.snehasish.microservice.question.dto.QuizResponse;
import com.snehasish.microservice.question.dto.Response;
import com.snehasish.microservice.question.entity.Question;
import com.snehasish.microservice.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/ping")
    public String ping() {
        return "pong from question";
    }

    @GetMapping("")
    public ResponseEntity<Response<List<QuestionDto>>> getAllQuestions() {
        Response<List<QuestionDto>> res = questionService.getAllQuestions();
//        log.info("all questions from" + System.);
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Response<List<QuestionDto>>> getQuestionsByCategory(@PathVariable String category) {
        Response<List<QuestionDto>> res = questionService.getQuestionsByCategory(category);
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

    //generate questions for quiz (list of ids)
    @GetMapping("/generateQuestionsForQuiz")
    public ResponseEntity<Response<List<Long>>> generateQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numOfQuestions) {
        Response<List<Long>> res = questionService.generateQuestionsForQuiz(category, numOfQuestions);
        return new ResponseEntity<>(res, res.getStatus());
    }

    //get questions by ids
    @PostMapping("/getQuestionsFromIds")
    public ResponseEntity<Response<List<QuestionDto>>> getQuestionsFromIds(@RequestBody List<Long> ids) {
        Response<List<QuestionDto>> res = questionService.getQuestionsFromIds(ids);
        return new ResponseEntity<>(res, res.getStatus());
    }

    //get score
    @PostMapping("/getScore")
    public ResponseEntity<Response<Integer>> getScore(@RequestBody List<QuizResponse> quizResponses) {
        Response<Integer> res = questionService.getScore(quizResponses);
        return new ResponseEntity<>(res, res.getStatus());
    }
}
