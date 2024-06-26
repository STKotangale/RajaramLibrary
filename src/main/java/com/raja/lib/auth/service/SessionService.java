package com.raja.lib.auth.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raja.lib.auth.model.Session;
import com.raja.lib.auth.repository.SessionRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public void checkCurrentYear() throws Exception {
        String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        int sessionId = Integer.parseInt(currentYear);

        Optional<Session> session = sessionRepository.findBySessionId(sessionId);
        if (session.isEmpty()) {
            throw new Exception("No sessions found for Year: " + sessionId);
        }
    }
}
