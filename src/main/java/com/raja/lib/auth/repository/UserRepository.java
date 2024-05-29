package com.raja.lib.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.useremail = :email")
    boolean existsByEmail(@Param("email") String email);
    
    @Query(value = "SELECT * FROM auth_users WHERE memberIdF IS NULL", nativeQuery = true)
    List<User> getAllAdminUsers();

    Optional<User> findByGeneralMember_MemberId(int memberId);
    
    Optional<User> findByUseremail(String email);

}
