package com.WebProject.WebService.common.login.repository;

import com.WebProject.WebService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
