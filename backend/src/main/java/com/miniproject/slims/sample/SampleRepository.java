package com.miniproject.slims.sample;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<Sample, Long> {
    Optional<Sample> findBySampleCode(String sampleCode);

    Page<Sample> findByStatus(SampleStatus status, Pageable pageable);
    Page<Sample> findByTypeContainingIgnoreCase(String type, Pageable pageable);
    // It automatically translates into SQL like:
    // SELECT *
    // FROM sample
    // WHERE LOWER(type) LIKE LOWER('%<type>%')
    // IgnoreCase = case insensitive            

    // Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
    // Page 0 (first page), 20 samples, Sorted by createdAt DESC

    Page<Sample> findByStatusAndTypeContainingIgnoreCase(SampleStatus status, String type, Pageable pageable);

    boolean existsBySampleCode(String sampleCode);
}