package com.pix.gc.controller;

import com.pix.gc.entities.BankAccount;
import com.pix.gc.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Bank Controler")
@RestController
@RequestMapping("/bank")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Operation(summary = "Generate a Bank Account for a User")
    @PostMapping("/{userId}")
    public ResponseEntity<BankAccount> generateBankAccount(@PathVariable Long userId) {
        try {
            BankAccount bankAccount = bankAccountService.createBankAccountForUser(userId);
            return ResponseEntity.ok(bankAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @Operation(summary = "Atualizar dados da conta bancária")
    @PutMapping("/{bankAccountId}")
    public ResponseEntity<BankAccount> updateBankAccount(@PathVariable Long bankAccountId,
                                                         @RequestParam String agency,
                                                         @RequestParam String accountNumber) {
        try {
            BankAccount updatedBankAccount = bankAccountService.updateBankAccount(bankAccountId, agency, accountNumber);
            return ResponseEntity.ok(updatedBankAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Remover conta bancária")
    @DeleteMapping("/{bankAccountId}")
    public ResponseEntity<Void> removeBankAccount(@PathVariable Long bankAccountId) {
        try {
            bankAccountService.removeBankAccount(bankAccountId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
