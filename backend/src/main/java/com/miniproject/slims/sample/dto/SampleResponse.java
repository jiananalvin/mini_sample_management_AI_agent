package com.miniproject.slims.sample.dto;

import com.miniproject.slims.sample.SampleStatus;
import java.time.Instant;

public record SampleResponse(
        Long id,
        String sampleCode,
        String type,
        SampleStatus status,
        Instant collectedAt,
        String comment
) {}
