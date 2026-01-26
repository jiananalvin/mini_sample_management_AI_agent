package com.miniproject.slims.sample.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

import org.springframework.web.bind.annotation.RequestBody;

// DTO = Data Transfer Object. It’s a simple object used to move data between layers: client → controller; controller → service
// DTOs protect your system by limiting what the outside world can touch，
// public void create(@RequestBody Sample sample) {}, client can set id and status
// public void create(@RequestBody SampleCreateRequest dto) {}, client can only touch type

public record SampleCreateRequest(
        @NotBlank @Size(max = 64) String sampleCode,
        @NotBlank @Size(max = 64) String type,
        @NotNull Instant collectedAt,
        @Size(max = 500) String comment
) {}
