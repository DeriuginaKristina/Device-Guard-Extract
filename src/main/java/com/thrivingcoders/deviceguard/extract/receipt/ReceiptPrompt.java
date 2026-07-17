package com.thrivingcoders.deviceguard.extract.receipt;


public final class ReceiptPrompt {

    public static final String EXTRACT_RECEIPT = """
            Analyze the attached receipt image and extract purchase information.

            Rules:
            - Read only information visible on the receipt.
            - Do not invent missing values.
            - Use null when a value cannot be determined.
            - Preserve product names as printed on the receipt.
            - Return purchaseDate in ISO format YYYY-MM-DD when possible.
            - Return purchaseTime in HH:mm:ss format when possible.
            - Use decimal numbers without currency symbols.
            - warrantyMentioned must be true only if warranty information is
              explicitly printed on the receipt.
            - warrantyText must contain the original warranty-related text,
              or null when no warranty information is visible.
            - receiptNumber means the receipt/document/check number, not the
              payment card authorization number.
            """;

    private ReceiptPrompt() {
    }
}
