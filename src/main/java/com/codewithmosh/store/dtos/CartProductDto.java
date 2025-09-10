package com.codewithmosh.store.dtos;

import java.math.BigDecimal;

public record CartProductDto(
        Long id,
        String name,
        BigDecimal price
) {
}
