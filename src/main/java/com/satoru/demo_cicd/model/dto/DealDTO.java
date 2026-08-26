package com.satoru.demo_cicd.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "Deal unique ID cannot be blank")
    private String dealUniqueId;

    @NotBlank(message = "From currency ISO code cannot be blank")
    @Pattern(regexp = "^[A-Z]{3}$", message = "From currency must be a 3-letter ISO 4217 code (e.g. USD)")
    private String fromCurrencyIsoCode;

    @NotBlank(message = "To currency ISO code cannot be blank")
    @Pattern(regexp = "^[A-Z]{3}$", message = "To currency must be a 3-letter ISO 4217 code (e.g. EUR)")
    private String toCurrencyIsoCode;

    @NotNull(message = "Deal timestamp cannot be null")
    private LocalDateTime dealTimestamp;

    @NotNull(message = "Deal amount cannot be null")
    @DecimalMin(value = "0.01", message = "Deal amount must be greater than 0")
    @Digits(integer = 15, fraction = 2, message = "Deal amount must have at most 15 integer digits and 2 decimal places")
    private BigDecimal dealAmount;
}

