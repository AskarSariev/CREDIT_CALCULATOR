package ru.sariev.credit_calculator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sariev.credit_calculator.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
