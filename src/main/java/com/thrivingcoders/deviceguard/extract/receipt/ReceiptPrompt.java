package com.thrivingcoders.deviceguard.extract.receipt;


public final class ReceiptPrompt {

    public static final String EXTRACT_RECEIPT = """
            Analyze the attached receipt image and extract purchase information.

            General rules:
            - Read only information visible on the receipt.
            - Do not invent missing values.
            - Use null when a value cannot be determined.
            - Preserve product names as printed on the receipt.
            
            DATE EXTRACTION RULES:
            - Search the entire receipt for a calendar date.
            - A date may appear together with a time.
            - If a line contains both date and time, split them.
            - The date is always before the time.
            - Never discard a detected date because it shares the line with the time.
            - Return purchaseDate in ISO-8601 format (yyyy-MM-dd).
            - purchaseDateRaw must contain the date exactly as printed on the receipt.
            - purchaseDateFormat must describe the printed date pattern using:
              DD for day, MM for month, YY for a two-digit year and YYYY for a four-digit year.
            - Preserve the original separators in purchaseDateFormat.
            - Examples:
              04.07.26 -> DD.MM.YY
              04.07.2026 -> DD.MM.YYYY
              07/04/2026 -> MM/DD/YYYY when the receipt locale is the United States
              2026-07-04 -> YYYY-MM-DD
            - purchaseDate must contain the normalized ISO-8601 date YYYY-MM-DD.
            
            ITEM EXTRACTION RULES
            
            Return only products or services that were actually purchased.
            
            Never return:
            
            - payment methods
            - payment transactions
            - gift card operations
            - bank card operations
            - loyalty cards
            - card numbers
            - masked card numbers
            - authorization codes
            - VAT summaries
            - discounts
            - corrections
            - totals
            - balances
            
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
