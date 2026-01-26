package com.miniproject.slims.sample;

import com.miniproject.slims.common.ConflictException;
import com.miniproject.slims.common.NotFoundException;
import com.miniproject.slims.sample.dto.SampleCreateRequest;
import com.miniproject.slims.sample.dto.SampleResponse;
import com.miniproject.slims.sample.dto.SampleUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

// A class is a Spring bean if Spring creates and manages its instance.
// If a class has any of these annotations, it is a Spring bean: @Component @Service @Repository @Controller @RestController
// Spring beans are singletons by default
// Spring beans are long-lived logic objects;

@Service
public class SampleService {

    private final SampleRepository repo;  // Assigned once. Cannot be changed later

    public SampleService(SampleRepository repo) {
        this.repo = repo;  
    }

    public SampleResponse create(SampleCreateRequest req) {
        if (repo.existsBySampleCode(req.sampleCode())) {
            throw new ConflictException("Sample code already exists: " + req.sampleCode());
        }

        Sample s = new Sample(
                req.sampleCode().trim(),
                req.type().trim(),
                SampleStatus.REGISTERED,
                req.collectedAt(),
                req.comment()
        );

        return toResponse(repo.save(s));
    }

    public SampleResponse get(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Sample not found: " + id)));
    }

    public Page<SampleResponse> list(String type, SampleStatus status, Pageable pageable) {
        boolean hasType = type != null && !type.isBlank();
        boolean hasStatus = status != null;

        Page<Sample> page;
        if (hasType && hasStatus) {
            page = repo.findByStatusAndTypeContainingIgnoreCase(status, type.trim(), pageable);
        } else if (hasStatus) {
            page = repo.findByStatus(status, pageable);
        } else if (hasType) {
            page = repo.findByTypeContainingIgnoreCase(type.trim(), pageable);
        } else {
            page = repo.findAll(pageable);
        }

        return page.map(this::toResponse);  // For every Sample in the page, call toResponse(sample). page.map(sample -> toResponse(sample))
    }

    public SampleResponse update(Long id, SampleUpdateRequest req) {
        Sample s = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Sample not found: " + id));

        s.setType(req.type().trim());
        s.setStatus(req.status());
        s.setCollectedAt(req.collectedAt());
        s.setComment(req.comment());

        return toResponse(repo.save(s));
    }

    private SampleResponse toResponse(Sample s) {
        return new SampleResponse(
                s.getId(),
                s.getSampleCode(),
                s.getType(),
                s.getStatus(),
                s.getCollectedAt(),
                s.getComment()
        );
    }
}
