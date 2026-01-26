package com.miniproject.slims.sample.dto;

import com.miniproject.slims.sample.SampleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record SampleUpdateRequest(
        @NotBlank @Size(max = 64) String type,
        @NotNull SampleStatus status,
        @NotNull Instant collectedAt,
        @Size(max = 500) String comment
) {}
