package com.sk.skmall.domain.user.repository;

import com.sk.skmall.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findById(String userId);

    Optional<User> findByEmail(String email);
}
