package com.WebProject.WebService.common.signup.repository;

import com.WebProject.WebService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignupUsersRepository extends JpaRepository<User, Long> {
    Boolean findByEmail(String email);
}
