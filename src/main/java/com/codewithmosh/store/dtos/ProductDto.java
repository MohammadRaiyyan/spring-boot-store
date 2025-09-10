package com.codewithmosh.store.dtos;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Byte categoryId
) {
}
