package com.WebProject.WebService.common.signup.repository;

import com.WebProject.WebService.common.signup.enums.RoleEnum;
import com.WebProject.WebService.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignupRolrRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
