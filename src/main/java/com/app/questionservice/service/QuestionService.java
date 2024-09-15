package com.app.questionservice.service;

import com.app.questionservice.dto.QuestionDTO;
import com.app.questionservice.model.Question;
import com.app.questionservice.model.QuestionResponse;
import com.app.questionservice.model.QuestionWrapper;
import enums.DifficultyLevel;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestions();

    Question findById(Integer id);

    Question createQuestion(QuestionDTO questionDTO);

    Question updateQuestion(Integer id, QuestionDTO questionDTO);

    void deleteQuestion(Integer id);

    List<Question> getQuestionsByCategory(String category);

    List<Question> getQuestionsByDifficulty(DifficultyLevel difficulty);

    List<Integer> getQuestionsForQuiz(String category, int numQuestions);

    List<QuestionWrapper> getQuestionsByIds(List<Integer> questionIds);

    int getScore(List<QuestionResponse> responses);
}
