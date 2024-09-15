package com.app.questionservice.validation;

import enums.DifficultyLevel;
import org.springframework.core.convert.converter.Converter;

public class DifficultyLevelValidator implements Converter<String, DifficultyLevel> {

    @Override
    public DifficultyLevel convert(String source) {
        try {
            return DifficultyLevel.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid difficulty level: " + source);
        }
    }
}