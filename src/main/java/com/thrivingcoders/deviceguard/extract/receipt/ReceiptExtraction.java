package com.thrivingcoders.deviceguard.extract.receipt;

import java.math.BigDecimal;
import java.util.List;


public record ReceiptExtraction(
        String sellerName,
        String sellerAddress,
        String purchaseDate,
        String purchaseTime,
        String receiptNumber,
        BigDecimal totalAmount,
        String currency,
        String paymentMethod,
        List<ReceiptItem> items,
        Boolean warrantyMentioned,
        String warrantyText
) {

    public record ReceiptItem(
            String name,
            BigDecimal quantity,
            BigDecimal unitPrice,
            BigDecimal totalPrice
    ) {
    }
}
