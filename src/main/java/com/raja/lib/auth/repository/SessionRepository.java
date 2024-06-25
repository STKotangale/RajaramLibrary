package com.raja.lib.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raja.lib.auth.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
	
	List<Session> findBySessionName(String sessionName);
	
    Optional<Session> findBySessionId(int sessionId);

}
