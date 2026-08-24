package com.satoru.demo_cicd.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealDTO {

    private Long id;

    @NotNull(message = "Deal unique ID cannot be null")
    private String dealUniqueId;

    @NotNull(message = "From currency ISO code cannot be null")
    private String fromCurrencyIsoCode;

    @NotNull(message = "To currency ISO code cannot be null")
    private String toCurrencyIsoCode;

    @NotNull(message = "Deal timestamp cannot be null")
    private LocalDateTime dealTimestamp;

    @NotNull(message = "Deal amount cannot be null")
    @DecimalMin(value = "0.1", message = "Deal amount must be greater than 0")
    private BigDecimal dealAmount;
}

