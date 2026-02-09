package com.miniproject.slims.sample.dto;

import jakarta.validation.constraints.NotBlank;

public record NaturalLanguageRequest(
        @NotBlank String text
) {}
