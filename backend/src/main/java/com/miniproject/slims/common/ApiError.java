package com.miniproject.slims.common;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {}


// ApiError defines a clean, consistent format for all API error responses.
// {
//   "timestamp": "2026-01-12T10:15:30Z",
//   "status": 400,
//   "error": "Bad Request",
//   "message": "Validation failed",
//   "path": "/api/samples",
//   "fieldErrors": {
//     "sampleCode": "must not be blank"
//   }
// }
