package com.accenture.modulosPago.repositories;

import com.accenture.modulosPago.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserIdOrderByIdDesc(Long userId);


    Account findByAccountNumber(String accountNumber);

    Account findByCbu(String cbu);
    List<Account> findByUserIdAndIsActive(Long userId, Boolean isActive);



}
