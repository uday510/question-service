package com.app.questionservice.service.impl;


import com.app.questionservice.dto.QuestionDTO;
import com.app.questionservice.model.Question;
import com.app.questionservice.model.QuestionResponse;
import com.app.questionservice.model.QuestionWrapper;
import com.app.questionservice.repository.QuestionRepository;
import com.app.questionservice.service.QuestionService;
import enums.DifficultyLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question findById(Integer id) {
        return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @Override
    public Question createQuestion(QuestionDTO questionDTO) {

        Question question = new Question();
        question.setQuestionTitle(questionDTO.getQuestionTitle());
        question.setCategory(questionDTO.getCategory());
        question.setOption1(questionDTO.getOption1());
        question.setOption2(questionDTO.getOption2());
        question.setOption3(questionDTO.getOption3());
        question.setOption4(questionDTO.getOption4());
        question.setCorrectOption(questionDTO.getCorrectOption());
        question.setDifficultyLevel(questionDTO.getDifficultyLevel());

        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Integer id, QuestionDTO questionDTO) {

        Question question = findById(id);
        question.setQuestionTitle(questionDTO.getQuestionTitle());
        question.setCategory(questionDTO.getCategory());
        question.setOption1(questionDTO.getOption1());
        question.setOption2(questionDTO.getOption2());
        question.setOption3(questionDTO.getOption3());
        question.setOption4(questionDTO.getOption4());
        question.setCorrectOption(questionDTO.getCorrectOption());
        question.setDifficultyLevel(questionDTO.getDifficultyLevel());

        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    @Override
    public List<Question> getQuestionsByDifficulty(DifficultyLevel difficulty) {
        return questionRepository.findByDifficultyLevel(difficulty);
    }

    @Override
    public List<Integer> getQuestionsForQuiz(String category, int numQuestions) {
        Pageable pageable = PageRequest.of(0, numQuestions);
        Page<Question> questionPage = questionRepository.findRandomQuestionsByCategory(category, pageable);

        List<Question> questions = questionPage.getContent();
        List<Integer> questionIds = new ArrayList<>();

        for (Question question : questions) {
            questionIds.add(question.getId());
        }

        return questionIds;
    }

    @Override
    public List<QuestionWrapper> getQuestionsByIds(List<Integer> questionIds) {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();

        for (Integer id : questionIds) {
            Question question = findById(id);
            questionWrappers.add(new QuestionWrapper(question));
        }
        return questionWrappers;
    }

    @Override
    public int getScore(List<QuestionResponse> responses) {
        int score = 0;
        for (QuestionResponse response : responses) {
            Question question = findById(response.getId());
            if (question.getCorrectOption().equals(response.getResponse())) {
                ++score;
            }
        }
        return score;
    }

}


