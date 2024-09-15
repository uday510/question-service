package com.app.questionservice.controller;

import com.app.questionservice.dto.QuestionDTO;
import com.app.questionservice.model.Question;
import com.app.questionservice.model.QuestionResponse;
import com.app.questionservice.model.QuestionWrapper;
import com.app.questionservice.service.QuestionService;
import com.app.questionservice.util.ApiResponse;
import enums.DifficultyLevel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Question>>> getAllQuestion() {
       List<Question> questions = questionService.getAllQuestions();
       return ResponseEntity.ok(new ApiResponse<>(questions, "Questions fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> getQuestionById(@PathVariable String id) {
        try {
            Integer integerId = Integer.valueOf(id);
            Question question = questionService.findById(integerId);
            return buildResponse(question);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, "Invalid question ID format"));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Question>> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        Question newQuestion = questionService.createQuestion(questionDTO);
        return ResponseEntity.created(URI.create("/api/questions/" + newQuestion.getId()))
                .body(new ApiResponse<>(newQuestion, "Question created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> updateQuestion(@PathVariable Integer id, @Valid @RequestBody QuestionDTO questionDTO) {
        Question updatedQuestion = questionService.updateQuestion(id, questionDTO);
        return ResponseEntity.ok(new ApiResponse<>(updatedQuestion, "Question updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok(new ApiResponse<>(null, "Question deleted successfully"));
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<ApiResponse<List<Question>>> getQuestionsByDifficulty(@PathVariable String difficulty) {
        List<Question> questions = questionService.getQuestionsByDifficulty(DifficultyLevel.valueOf(difficulty.toUpperCase()));
        return ResponseEntity.ok(new ApiResponse<>(questions, "Questions fetched successfully"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Question>>> getQuestionsByCategory(@PathVariable String category) {
        List<Question> questions = questionService.getQuestionsByCategory(category.toLowerCase());
        return ResponseEntity.ok(new ApiResponse<>(questions, "Questions fetched successfully"));
    }

    private <T> ResponseEntity<ApiResponse<T>> buildResponse(T data) {
        return ResponseEntity.ok(new ApiResponse<>(data, "Question fetched successfully"));
    }

    @GetMapping("/generate")
    public ResponseEntity<ApiResponse<List<Integer>>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numQuestions) {
        System.out.println("category: " + category);
        System.out.println("numQuestions: " + numQuestions);
        System.out.println("category.toLowerCase(): " + category.toLowerCase());
        List<Integer> questionIds = questionService.getQuestionsForQuiz(category.toLowerCase(), numQuestions);
        return ResponseEntity.ok(new ApiResponse<>(questionIds, "Questions fetched successfully"));
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<ApiResponse<List<QuestionWrapper>>> getQuestionsByIds(@RequestBody List<Integer> questionIds) {
        List<QuestionWrapper> questions = questionService.getQuestionsByIds(questionIds);
        return ResponseEntity.ok(new ApiResponse<>(questions, "Questions fetched successfully"));
    }

    @PostMapping("/getScore")
    public ResponseEntity<ApiResponse<Object>> submitQuiz(@RequestBody List<QuestionResponse> responses) {
        try {
            System.out.println("responses: " + responses);
            int score = questionService.getScore(responses);
            String message = "Quiz submitted successfully. Your score is " + score + "/" + responses.size();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, message));
        } catch (Exception e) {
            Logger.getLogger("Error: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(null, "Error submitting quiz"));
    }

//        @GetMapping("/load")
    public ResponseEntity<ApiResponse<Void>> loadQuestions() {
        QuestionDTO q1 = new QuestionDTO();
        q1.setQuestionTitle("What is the default value of byte variable?");
        q1.setCategory("java");
        q1.setDifficultyLevel(DifficultyLevel.EASY);
        q1.setOption1("0");
        q1.setOption2("null");
        q1.setOption3("0.0");
        q1.setOption4("undefined");
        q1.setCorrectOption("0");

        QuestionDTO q2 = new QuestionDTO();
        q2.setQuestionTitle("Which of the following is true about String?");
        q2.setCategory("java");
        q2.setDifficultyLevel(DifficultyLevel.EASY);
        q2.setOption1("String is a primary data type");
        q2.setOption2("String is a wrapper class");
        q2.setOption3("String is a mutable class");
        q2.setOption4("String is a sequence of characters");
        q2.setCorrectOption("String is a sequence of characters");

        QuestionDTO q3 = new QuestionDTO();
        q3.setQuestionTitle("Which of the following is not a keyword in Java?");
        q3.setCategory("java");
        q3.setDifficultyLevel(DifficultyLevel.EASY);
        q3.setOption1("class");
        q3.setOption2("interface");
        q3.setOption3("extends");
        q3.setOption4("abstraction");
        q3.setCorrectOption("abstraction");

        QuestionDTO q4 = new QuestionDTO();
        q4.setQuestionTitle("What is the default value of char variable?");
        q4.setCategory("java");
        q4.setDifficultyLevel(DifficultyLevel.EASY);
        q4.setOption1("0");
        q4.setOption2("null");
        q4.setOption3("0.0");
        q4.setOption4("undefined");

        QuestionDTO q5 = new QuestionDTO();
        q5.setQuestionTitle("What is the default value of float variable?");
        q5.setCategory("java");
        q5.setDifficultyLevel(DifficultyLevel.MEDIUM);
        q5.setOption1("0");
        q5.setOption2("null");
        q5.setOption3("0.0");
        q5.setOption4("undefined");
        q5.setCorrectOption("0.0");

        QuestionDTO q6 = new QuestionDTO();
        q6.setQuestionTitle("What is the default value of double variable?");
        q6.setCategory("java");
        q6.setDifficultyLevel(DifficultyLevel.HARD);
        q6.setOption1("0");
        q6.setOption2("null");
        q6.setOption3("0.0");
        q6.setOption4("undefined");
        q6.setCorrectOption("0.0");

        QuestionDTO q7 = new QuestionDTO();
        q7.setQuestionTitle("What is the default value of int variable?");
        q7.setCategory("python");
        q7.setDifficultyLevel(DifficultyLevel.EASY);
        q7.setOption1("0");
        q7.setOption2("null");
        q7.setOption3("0.0");
        q7.setOption4("undefined");
        q7.setCorrectOption("0");

        QuestionDTO q8 = new QuestionDTO();
        q8.setQuestionTitle("Which of the following is true about list?");
        q8.setCategory("python");
        q8.setDifficultyLevel(DifficultyLevel.EASY);
        q8.setOption1("List is a primary data type");
        q8.setOption2("List is a wrapper class");
        q8.setOption3("List is a mutable class");
        q8.setOption4("List is a sequence of characters");
        q8.setCorrectOption("List is a mutable class");

        QuestionDTO q9 = new QuestionDTO();
        q9.setQuestionTitle("Which of the following is not a keyword in Python?");
        q9.setCategory("python");
        q9.setDifficultyLevel(DifficultyLevel.EASY);
        q9.setOption1("class");
        q9.setOption2("interface");
        q9.setOption3("extends");
        q9.setOption4("abstraction");
        q9.setCorrectOption("abstraction");

        QuestionDTO q10 = new QuestionDTO();
        q10.setQuestionTitle("What is the default value of str variable?");
        q10.setCategory("python");
        q10.setDifficultyLevel(DifficultyLevel.MEDIUM);
        q10.setOption1("0");
        q10.setOption2("null");
        q10.setOption3("0.0");
        q10.setOption4("undefined");
        q10.setCorrectOption("0.0");

        QuestionDTO q11 = new QuestionDTO();
        q11.setQuestionTitle("What is the default value of bool variable?");
        q11.setCategory("python");
        q11.setDifficultyLevel(DifficultyLevel.HARD);
        q11.setOption1("0");
        q11.setOption2("null");
        q11.setOption3("0.0");
        q11.setOption4("undefined");
        q11.setCorrectOption("0.0");

        questionService.createQuestion(q1);
        questionService.createQuestion(q2);
        questionService.createQuestion(q3);
        questionService.createQuestion(q4);
        questionService.createQuestion(q5);
        questionService.createQuestion(q6);
        questionService.createQuestion(q7);
        questionService.createQuestion(q8);
        questionService.createQuestion(q9);
        questionService.createQuestion(q10);
        questionService.createQuestion(q11);

        return ResponseEntity.ok(new ApiResponse<>(null, "Questions loaded successfully"));
    }
}