package com.pix.gc.dto;

import com.pix.gc.entities.PixKey;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record BankAccountDto(
        Long id,
        String agency,
        String accountNumber,
        BigDecimal balance
) {}
