package com.raja.lib.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.raja.lib.auth.model.User;

import lombok.val;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  
  Optional<User> findByMemberIdf(String memberIdf);
  
  @Query(value = "SELECT * FROM users u WHERE u.member_idf  IS NULL", nativeQuery = true)
  List<User> getAllAdminUsers();

}
