package com.miniproject.slims.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class GptService {

    private final WebClient webClient;
    private final String apiKey;
    private final ObjectMapper objectMapper;

    public GptService(@Value("${openai.api.key:}") String apiKey) {
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
        
        WebClient.Builder builder = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        
        // Only add authorization header if API key is provided
        if (apiKey != null && !apiKey.isBlank()) {
            builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
        }
        
        this.webClient = builder.build();
    }

    public SampleExtractionResult extractSampleInfo(String naturalLanguageText) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("OpenAI API key is not configured. Please set 'openai.api.key' in application.properties");
        }

        String prompt = buildPrompt(naturalLanguageText);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", new Object[]{
            Map.of("role", "system", "content", "You are a helpful assistant that extracts structured data from natural language. Always respond with valid JSON only."),
            Map.of("role", "user", "content", prompt)
        });
        requestBody.put("response_format", Map.of("type", "json_object"));
        requestBody.put("temperature", 0.3);

        try {
            String responseJson = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(body -> Mono.error(
                                            new RuntimeException("OpenAI API error: " + response.statusCode() + " - " + body))))
                    .bodyToMono(String.class)
                    .block();

            if (responseJson == null || responseJson.isBlank()) {
                throw new RuntimeException("Empty response from OpenAI API");
            }

            JsonNode response = objectMapper.readTree(responseJson);
            
            // Check for API errors in response
            if (response.has("error")) {
                JsonNode error = response.get("error");
                String errorMessage = error.has("message") ? error.get("message").asText() : "Unknown OpenAI API error";
                throw new RuntimeException("OpenAI API error: " + errorMessage);
            }
            
            if (!response.has("choices") || response.get("choices").isEmpty()) {
                throw new RuntimeException("Invalid response format from OpenAI API: no choices found");
            }
            
            String content = response.get("choices").get(0).get("message").get("content").asText();
            
            if (content == null || content.isBlank()) {
                throw new RuntimeException("Empty content in OpenAI API response");
            }
            
            JsonNode extracted = objectMapper.readTree(content);
            
            String sampleCode = extracted.has("sampleCode") ? extracted.get("sampleCode").asText() : null;
            String type = extracted.has("type") ? extracted.get("type").asText() : null;
            String collectedAtStr = extracted.has("collectedAt") ? extracted.get("collectedAt").asText() : null;
            String comment = extracted.has("comment") && !extracted.get("comment").isNull() ? extracted.get("comment").asText() : null;

            // Parse collectedAt - try ISO format first, then try to interpret relative times
            Instant collectedAt;
            if (collectedAtStr != null && !collectedAtStr.isBlank()) {
                try {
                    collectedAt = Instant.parse(collectedAtStr);
                } catch (Exception e) {
                    // If parsing fails, use current time as fallback
                    collectedAt = Instant.now();
                }
            } else {
                collectedAt = Instant.now();
            }

            // Generate sample code if not provided
            if (sampleCode == null || sampleCode.isBlank()) {
                sampleCode = "S-" + System.currentTimeMillis();
            }

            // Default type if not provided
            if (type == null || type.isBlank()) {
                type = "Blood"; // Default type
            }

            return new SampleExtractionResult(sampleCode, type, collectedAt, comment);
        } catch (RuntimeException e) {
            // Re-throw RuntimeException as-is (already has proper message)
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract sample information from natural language: " + e.getMessage() + 
                    (e.getCause() != null ? " (Cause: " + e.getCause().getMessage() + ")" : ""), e);
        }
    }

    private String buildPrompt(String naturalLanguageText) {
        String currentTime = Instant.now().toString();
        return String.format("""
            Extract sample information from the following natural language text: "%s"
            
            Current time is: %s
            
            Return a JSON object with the following fields:
            - sampleCode: The sample ID or code mentioned (if not mentioned, use null)
            - type: The sample type (e.g., "Blood", "Urine", "Tissue", etc.). If not mentioned, infer from context or use "Blood" as default.
            - collectedAt: The collection time in ISO 8601 format (e.g., "2026-01-11T10:00:00Z"). 
              * If "the time" or "now" is mentioned, use the current time: %s
              * If a relative time is mentioned (e.g., "today", "yesterday"), convert it to ISO format based on the current time
              * If not mentioned, use the current time
            - comment: Any additional notes or comments (can be null)
            
            Examples:
            - "create a blood sample ID S-0001 and the time" -> {"sampleCode": "S-0001", "type": "Blood", "collectedAt": "%s", "comment": null}
            - "create a sample with code ABC123, type is Urine, collected yesterday" -> Calculate yesterday's date and return ISO format
            - "create a blood sample" -> {"sampleCode": null, "type": "Blood", "collectedAt": "%s", "comment": null}
            
            Respond with JSON only, no additional text.
            """, naturalLanguageText, currentTime, currentTime, currentTime, currentTime);
    }

    public record SampleExtractionResult(
            String sampleCode,
            String type,
            Instant collectedAt,
            String comment
    ) {}
}
