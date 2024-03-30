package com.raja.lib.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.raja.lib.auth.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByUsername(String username);
}