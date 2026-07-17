package com.thrivingcoders.deviceguard.extract.receipt;

import java.io.IOException;
import java.util.Base64;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ReceiptPageController {

    private static final Set<String> SUPPORTED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private final ReceiptAiService receiptAiService;

    public ReceiptPageController(final ReceiptAiService receiptAiService) {
        this.receiptAiService = receiptAiService;
    }

    @GetMapping("/")
    public String showUploadPage() {
        return "receipt-upload";
    }

    @PostMapping("/receipts/extract")
    public String extractReceipt(
            @RequestParam("file") final MultipartFile file,
            final Model model
    ) {
        if (file.isEmpty()) {
            model.addAttribute(
                    "error",
                    "Please select a receipt image."
            );
            return "receipt-upload";
        }

        final String contentType = file.getContentType();

        if (contentType == null
                || !SUPPORTED_CONTENT_TYPES.contains(contentType)) {

            model.addAttribute(
                    "error",
                    "Unsupported image format. Please upload JPEG, PNG or WebP."
            );
            return "receipt-upload";
        }

        try {
            final byte[] imageBytes = file.getBytes();

            final String encodedImage = Base64.getEncoder()
                    .encodeToString(imageBytes);

            final String imageDataUrl =
                    "data:" + contentType + ";base64," + encodedImage;

            final ReceiptExtraction extraction =
                    receiptAiService.extract(imageBytes, contentType);

            model.addAttribute("fileName", file.getOriginalFilename());
            model.addAttribute("contentType", contentType);
            model.addAttribute("fileSize", file.getSize());
            model.addAttribute("imageDataUrl", imageDataUrl);
            model.addAttribute("extraction", extraction);

            return "receipt-upload";

        } catch (IOException exception) {
            model.addAttribute(
                    "error",
                    "The receipt image could not be read."
            );

            return "receipt-upload";

        } catch (RuntimeException exception) {
            model.addAttribute(
                    "error",
                    "AI extraction failed: " + exception.getMessage()
            );

            return "receipt-upload";
        }
    }
}
