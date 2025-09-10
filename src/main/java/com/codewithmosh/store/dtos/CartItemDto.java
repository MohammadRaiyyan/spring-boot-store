package com.codewithmosh.store.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CartItemDto(
        CartProductDto product,
        Integer quantity,
        BigDecimal totalPrice
) {
}
