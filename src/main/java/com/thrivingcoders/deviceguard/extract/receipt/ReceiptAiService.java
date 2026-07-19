package com.thrivingcoders.deviceguard.extract.receipt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;


@Service
public class ReceiptAiService {

    private final ChatClient chatClient;
    private final ReceiptExtractionValidator validator;

    public ReceiptAiService(
            final ChatClient.Builder chatClientBuilder,
            final ReceiptExtractionValidator validator
    ) {
        this.chatClient = chatClientBuilder.build();
        this.validator = validator;
    }
    public ReceiptExtraction extract(
            final byte[] imageBytes,
            final String contentType
    ) {
        final MimeType mimeType = MimeTypeUtils.parseMimeType(contentType);

        final ByteArrayResource imageResource =
                new ByteArrayResource(imageBytes);

        final Media image = new Media(mimeType, imageResource);

        final ReceiptExtraction extraction = chatClient.prompt()
                .user(user -> user
                        .text(ReceiptPrompt.EXTRACT_RECEIPT)
                        .media(image))
                .call()
                .entity(ReceiptExtraction.class);

        if (extraction == null) {
            throw new IllegalStateException(
                    "OpenAI returned no receipt extraction.");
        }

        return validator.validate(extraction);
    }
}
