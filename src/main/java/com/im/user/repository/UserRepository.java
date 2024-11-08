package com.im.user.repository;

import com.im.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNumber(String userNumber);
    boolean existsByUserNumber(String userNumber);
}
