package com.pix.gc.dto;



public record UserDto(
        Long id,
        String name,
        String email,
        String phoneNumber,
        BankAccountDto account
) {}
