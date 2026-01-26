package com.miniproject.slims.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.slims.sample.dto.SampleCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc  // Creates a MockMvc object for testing HTTP requests without starting a real server
class SampleControllerTest {
    // You want Spring to inject a bean
    @Autowired MockMvc mvc;  // Fake HTTP client
    @Autowired ObjectMapper om;  // Jackson’s JSON serializer

    @Test
    void createSample_returnsRegisteredStatus() throws Exception {
        var req = new SampleCreateRequest("S-0001", "Blood", Instant.parse("2026-01-11T10:00:00Z"), "first");
        mvc.perform(post("/api/samples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))  // Convert req → JSON string
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampleCode").value("S-0001"))  // Response JSON must contain: "sampleCode": "S-0001"
                .andExpect(jsonPath("$.status").value("REGISTERED"));
    }
}
