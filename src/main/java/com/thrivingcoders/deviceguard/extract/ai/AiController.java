package com.thrivingcoders.deviceguard.extract.ai;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(final AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/ping")
    public String ping() {
        return aiService.ping();
    }
}
