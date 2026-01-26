package com.miniproject.slims.sample;

import com.miniproject.slims.sample.dto.SampleCreateRequest;
import com.miniproject.slims.sample.dto.SampleResponse;
import com.miniproject.slims.sample.dto.SampleUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/samples")
public class SampleController {

    private final SampleService service;

    public SampleController(SampleService service) {
        this.service = service;
    }

    @PostMapping
    public SampleResponse create(@Valid @RequestBody SampleCreateRequest req) {  // Valid triggers validation annotations inside the DTO
        return service.create(req);
    }

    @GetMapping("/{id}")
    public SampleResponse get(@PathVariable Long id) {  // PathVariable takes the value from {id} in the URL and convert it to a Long
        return service.get(id); 
    }

    // GET /api/samples?type=Blood&status=REGISTERED
    @GetMapping
    public Page<SampleResponse> list(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) SampleStatus status,
            Pageable pageable
    ) {
        return service.list(type, status, pageable);
    }

    @PutMapping("/{id}")  // must update all fields
    public SampleResponse update(@PathVariable Long id, @Valid @RequestBody SampleUpdateRequest req) {
        return service.update(id, req);
    }

    // Right now this is a PUT endpoint, so it expects a full replacement. 
    // For partial updates, I’d add a PATCH endpoint with nullable fields and only apply the non-null ones in the service layer.”
}
