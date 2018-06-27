package yevtukh.anton.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yevtukh.anton.entities.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
