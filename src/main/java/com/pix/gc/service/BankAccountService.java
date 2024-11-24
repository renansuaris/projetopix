package com.pix.gc.service;

import com.pix.gc.entities.BankAccount;
import com.pix.gc.entities.PixKey;
import com.pix.gc.entities.User;
import com.pix.gc.repo.BankAccountRepository;
import com.pix.gc.repo.PixKeyRepository;
import com.pix.gc.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final PixKeyRepository pixKeyRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository, PixKeyRepository pixKeyRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.pixKeyRepository = pixKeyRepository;
    }

    public BankAccount createBankAccountForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("Usuário não encontrado!")
        );

        if (user.getAccount() != null) {
            throw new IllegalStateException("Usuário já possui uma conta bancária!");
        }

        String agency = generateAgency();
        String accountNumber = generateAccountNumber();

        BankAccount bankAccount = BankAccount.builder()
                .agency(agency)
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .build();

        user.setAccount(bankAccount);
        userRepository.save(user);

        return bankAccount;
    }


    public List<PixKey> getPixKeysForBankAccount(Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta bancária não encontrada"));

        return pixKeyRepository.findByBankAccountId(bankAccountId);
    }

    public BankAccount updateBankAccount(Long bankAccountId, String agency, String accountNumber) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta bancária não encontrada"));

        bankAccount.setAgency(agency);
        bankAccount.setAccountNumber(accountNumber);

        return bankAccountRepository.save(bankAccount);
    }

    public void removeBankAccount(Long bankAccountId) {

        /*
        Para excluir a conta bancária, deve haver uma lógica para isso
        não pode simplesmente excluir pois a conta bancária referencia
        um id de um user, e ao tentar excluir há uma exceção por violação
        de constraint.
         */

        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Conta bancária não encontrada")); // excecao generica

        User user = userRepository.findByAccountId(bankAccountId);
        if (user != null) {
            user.setAccount(null);  // como por enquanto, nessa logica um user só possuir uma conta bancária
                                    // eu alterei o setAccount para null antes de deletar a conta bancária
                                    // essa lógica pode ser alterada mais na frente, se necessitar de mais de uma
                                    // conta bancária em user ou afins.
            userRepository.save(user);

        }

        pixKeyRepository.deleteByBankAccountId(bankAccountId);

        bankAccountRepository.delete(bankAccount);
    }

    private String generateAgency() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    private String generateAccountNumber() {
        int number = new Random().nextInt(1_000_000);
        int digit = new Random().nextInt(10);
        return String.format("%06d-%d", number, digit);
    }
}
