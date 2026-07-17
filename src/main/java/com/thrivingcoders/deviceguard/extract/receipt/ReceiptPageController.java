package com.thrivingcoders.deviceguard.extract.receipt;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ReceiptPageController {

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
            model.addAttribute("error", "Please select a receipt image.");
            return "receipt-upload";
        }

        model.addAttribute("fileName", file.getOriginalFilename());
        model.addAttribute("contentType", file.getContentType());
        model.addAttribute("fileSize", file.getSize());

        return "receipt-upload";
    }
}
