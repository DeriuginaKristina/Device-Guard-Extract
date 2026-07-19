package com.thrivingcoders.deviceguard.extract.receipt;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


@Component
public class ReceiptExtractionValidator {

    private final Clock clock;

    public ReceiptExtractionValidator() {
        this(Clock.systemDefaultZone());
    }

    ReceiptExtractionValidator(final Clock clock) {
        this.clock = clock;
    }

    public ReceiptExtraction validate(final ReceiptExtraction extraction) {
        final String purchaseDate = extraction.purchaseDate();

        if (purchaseDate == null) {
            return extraction;
        }

        try {
            final LocalDate parsedDate = LocalDate.parse(purchaseDate);
            final LocalDate today = LocalDate.now(clock);

            if (parsedDate.isAfter(today)) {
                return withPurchaseDate(extraction, null);
            }

            return extraction;

        } catch (DateTimeParseException exception) {
            return withPurchaseDate(extraction, null);
        }
    }

    private ReceiptExtraction withPurchaseDate(
            final ReceiptExtraction source,
            final String purchaseDate
    ) {
        return new ReceiptExtraction(
                source.sellerName(),
                source.sellerAddress(),
                source.receiptLanguage(),
                source.receiptCountry(),
                source.purchaseDateRaw(),
                source.purchaseDateFormat(),
                purchaseDate,
                source.purchaseTime(),
                source.receiptNumber(),
                source.totalAmount(),
                source.currency(),
                source.paymentMethod(),
                source.items(),
                source.warrantyMentioned(),
                source.warrantyText()
        );
    }
}
