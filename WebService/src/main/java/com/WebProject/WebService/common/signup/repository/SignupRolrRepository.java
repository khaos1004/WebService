package com.WebProject.WebService.common.signup.repository;

import com.WebProject.WebService.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignupRolrRepository extends JpaRepository<Role, Long> {
}
