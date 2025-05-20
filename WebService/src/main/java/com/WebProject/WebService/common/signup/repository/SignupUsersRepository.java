package com.WebProject.WebService.common.signup.repository;

import com.WebProject.WebService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignupUsersRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
}
