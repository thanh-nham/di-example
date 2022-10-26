package com.finx.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductDto(Long id, @NotBlank String title, String description, String brand, @NotNull BigDecimal price) {
}
