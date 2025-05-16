package com.WebProject.WebService.common.signup.repository;

import com.WebProject.WebService.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignupUserRoleRepository extends JpaRepository<UserRole, Long> {
}
