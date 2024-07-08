package com.example.testing.assertj;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@Builder
public class Story {
    @NonNull
    private final String name;
    private final String description;
    private final List<String> acceptanceCriteria;
    private final Size size;

    public enum Size {
        SMALL,
        MEDIUM,
        LARGE,
        XL,
        XXL,
        COLOSSAL,
        GOVERNMENT_IT_PROJECT
    }
}
