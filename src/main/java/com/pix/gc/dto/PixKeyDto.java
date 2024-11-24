package com.pix.gc.dto;

import jakarta.validation.constraints.NotBlank;

public record PixKeyDto( String keyType,
                         String keyValue) {}
