package com.app.questionservice.validation;

import com.app.questionservice.dto.QuestionDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CorrectOptionValidator implements ConstraintValidator<ValidCorrectOption, QuestionDTO> {

    @Override
    public boolean isValid(QuestionDTO questionDTO, ConstraintValidatorContext context) {
        String correctOption = questionDTO.getCorrectOption();

        return correctOption != null && (correctOption.equals(questionDTO.getOption1()) || correctOption.equals(questionDTO.getOption2()) || correctOption.equals(questionDTO.getOption3()) || correctOption.equals(questionDTO.getOption4()));
    }
}
