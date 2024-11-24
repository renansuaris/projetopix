package com.pix.gc.repo;

import com.pix.gc.entities.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKey, Integer> {

    List<PixKey> findByBankAccountId(Long bankAccountId);

    void deleteByBankAccountId(Long bankAccountId);
}
