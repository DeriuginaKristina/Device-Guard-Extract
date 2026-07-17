package com.thrivingcoders.deviceguard.extract.ai;

import com.openai.errors.RateLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AiExceptionHandler {

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<Map<String, String>> handleRateLimitException(
            final RateLimitException exception
    ) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(Map.of(
                        "status", "TOO_MANY_REQUESTS",
                        "message",
                        "OpenAI API quota is unavailable. Check API billing and usage limits."
                ));
    }
}
