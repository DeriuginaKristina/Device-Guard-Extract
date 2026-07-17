package com.thrivingcoders.deviceguard.extract.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(final ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String ping() {
        return chatClient.prompt()
                .system("""
                        You are a connectivity test for the Device Guard Extract application.
                        Answer with exactly: Device Guard AI connection works
                        """)
                .user("Confirm that the connection is working.")
                .call()
                .content();
    }
}
