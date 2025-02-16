package com.WebProject.WebService.repository;

import com.WebProject.WebService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
