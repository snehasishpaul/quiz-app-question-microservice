package com.snehasish.microservice.question.service.impl;

import com.snehasish.microservice.question.dto.QuestionDto;
import com.snehasish.microservice.question.dto.QuizResponse;
import com.snehasish.microservice.question.dto.Response;
import com.snehasish.microservice.question.entity.Question;
import com.snehasish.microservice.question.exception.NotFoundException;
import com.snehasish.microservice.question.repository.QuestionRepository;
import com.snehasish.microservice.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response<QuestionDto> addQuestion(QuestionDto questionDto) {
        try {
            Question question = new Question();
            question.setQuestion(questionDto.getQuestion());
            question.setCategory(questionDto.getCategory());
            question.setAnswer(questionDto.getAnswer());
            question.setOption1(questionDto.getOption1());
            question.setOption2(questionDto.getOption2());
            question.setOption3(questionDto.getOption3());
            question.setOption4(questionDto.getOption4());

            Question savedQuestion = questionRepository.save(question);
            log.info("Saved Question: {}", savedQuestion);
            QuestionDto dto = modelMapper.map(savedQuestion, QuestionDto.class);
            return Response.<QuestionDto>builder().content(dto).message("Question created successfully").status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Response<List<QuestionDto>> getAllQuestions() {
        try {
            List<Question> questionList = questionRepository.findAll();
            List<QuestionDto> questionDtoList = questionList.stream().map(question -> this.modelMapper.map(question, QuestionDto.class)).toList();

            return Response.<List<QuestionDto>>builder()
                    .content(questionDtoList)
                    .message(null)
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Response<List<QuestionDto>> getQuestionsByCategory(String category) {
        List<Question> questions = this.questionRepository.findByCategoryIgnoreCase(category);
        List<QuestionDto> questionDtoList = questions.stream().map((question -> this.modelMapper.map(question, QuestionDto.class))).toList();
        return Response.<List<QuestionDto>>builder()
                .content(questionDtoList)
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public Response<QuestionDto> getQuestionById(Long id) {
        try {
            Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("question id not found"));
            QuestionDto dto = this.modelMapper.map(question, QuestionDto.class);
            return Response.<QuestionDto>builder()
                    .content(dto)
                    .message(null)
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Response<QuestionDto> updateQuestion(Long id, QuestionDto questionDto) {
        try {
            Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("question id not found"));
            question.setQuestion(questionDto.getQuestion());
            Question savedQuestion = questionRepository.save(question);
            log.info("Updated Question: {}", savedQuestion);
            QuestionDto dto = this.modelMapper.map(savedQuestion, QuestionDto.class);
            return Response.<QuestionDto>builder()
                    .content(dto)
                    .message("Question updated successfully")
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Response<String> deleteQuestion(Long id) {
        try {
            questionRepository.deleteById(id);
            log.info("Deleted Question: {}", id);
            return Response.<String>builder()
                    .content(null)
                    .message("Question deleted successfully")
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Response<List<Long>> generateQuestionsForQuiz(String category, Integer numOfQuestions) {
        List<Long> randomQuestionsForQuiz = questionRepository.getRandomQuestionIdsForQuiz(category, numOfQuestions);
        return Response.<List<Long>>builder()
                .content(randomQuestionsForQuiz)
                .message("Successfully created quiz with " + numOfQuestions + " of questions")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public Response<List<QuestionDto>> getQuestionsFromIds(List<Long> ids) {
        List<QuestionDto> questionWrapperList = new ArrayList<>();
        List<Question> questionList = new ArrayList<>();

        for (Long questionId : ids) {
            Question question = this.questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("question id not found: " + questionId));
            questionList.add(question);
        }

        for (Question question : questionList) {
            QuestionDto questionWrapper = new QuestionDto();
            questionWrapper.setId(question.getId());
            questionWrapper.setQuestion(question.getQuestion());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());
            questionWrapper.setCategory(question.getCategory());
            questionWrapperList.add(questionWrapper);
        }

        return Response.<List<QuestionDto>>builder()
                .content(questionWrapperList)
                .message("questions fetched successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public Response<Integer> getScore(List<QuizResponse> quizResponses) {
        int right = 0;
        for (QuizResponse res : quizResponses) {
            Question question = this.questionRepository.findById(res.getId()).orElseThrow(() -> new NotFoundException("question not found with id: " + res.getId()));
            if (res.getResponse().equals(question.getAnswer())) {
                right++;
            }
        }
        return Response.<Integer>builder()
                .content(right)
                .message("Your score: " + right)
                .status(HttpStatus.OK)
                .build();
    }

}
