package com.satoru.demo_cicd.model.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealRespDTO {

    private Long id;

    private String dealUniqueId;

    private String fromCurrencyIsoCode;

    private String toCurrencyIsoCode;

    private LocalDateTime dealTimestamp;

    private BigDecimal dealAmount;
}

