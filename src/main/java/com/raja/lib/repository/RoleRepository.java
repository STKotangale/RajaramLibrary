package com.raja.lib.repository;

import java.util.Optional;

import com.raja.lib.models.ERole;
import com.raja.lib.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
