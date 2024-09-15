package com.app.questionservice.dto;

import enums.DifficultyLevel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDTO {

    @NotEmpty(message = "Question title is required")
    @Size(min = 5, max = 100, message = "Question title must be between 5 and 100 characters")
    private String questionTitle;

    @NotEmpty(message = "Category is required")
    private String category;

    @NotEmpty(message = "Option 1 is required")
    private String option1;

    @NotEmpty(message = "Option 2 is required")
    private String option2;

    @NotEmpty(message = "Option 3 is required")
    private String option3;

    @NotEmpty(message = "Option 4 is required")
    private String option4;

    @NotEmpty(message = "Correct option is required")
    private String correctOption;

    @NotNull(message = "Difficulty level is required")
    private DifficultyLevel difficultyLevel;
}
