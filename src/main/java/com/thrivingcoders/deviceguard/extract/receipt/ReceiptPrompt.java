package com.thrivingcoders.deviceguard.extract.receipt;


public final class ReceiptPrompt {

    public static final String EXTRACT_RECEIPT = """
            Analyze the attached receipt image and extract purchase information.

            General rules:
            - Read only information visible on the receipt.
            - Do not invent missing values.
            - Use null when a value cannot be determined.
            - Preserve product names as printed on the receipt.
            
            Locale and date rules:
            - Determine the receipt language and probable country from all available
              evidence, including language, seller address, currency, tax labels,
              telephone format and other regional indicators.
            - receiptLanguage must use an ISO 639-1 language code when identifiable.
            - receiptCountry must use an ISO 3166-1 alpha-2 country code when identifiable.
            - purchaseDateRaw must contain the date exactly as printed on the receipt.
            - Interpret numeric dates according to the customary date format of the
              identified country and language.
            - For German-language receipts from Germany, interpret numeric dates as
              day-month-year. For example, 10.12 means 10 December, not October 12.
            - Convert the interpreted date to ISO format YYYY-MM-DD in purchaseDate.
            - A purchase date must not be later than the current date.
            - If one interpretation produces a future date, reject that interpretation
              and evaluate other locale-compatible interpretations.
            - Do not silently choose between multiple plausible past dates.
              If the date remains ambiguous, set purchaseDate to null while preserving
              purchaseDateRaw.
            - Expand a two-digit year to the most plausible non-future year based on
              the receipt context and current date.
            - Return purchaseTime in HH:mm:ss format when possible.
            
            Financial rules:
            - Use decimal numbers without currency symbols.
            - receiptNumber means the receipt or document number, not a payment-card
              authorization number, terminal ID or transaction ID.
            
            Seller rules:
            - Document labels such as "Kundenbeleg", "Beleg", "Receipt" and
              "Customer copy" are not seller names.
            - If the seller cannot be identified, use null.
            
            Warranty rules:
            - warrantyMentioned must be true only when warranty information is
              explicitly printed on the receipt.
            - warrantyText must contain the original warranty-related text,
              or null when no warranty information is visible.
            """;

    private ReceiptPrompt() {
    }
}
