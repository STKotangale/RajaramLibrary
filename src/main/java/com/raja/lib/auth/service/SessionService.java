package com.raja.lib.auth.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raja.lib.auth.model.Session;
import com.raja.lib.auth.repository.SessionRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public void printCurrentYear(String currentYear) {
        if (currentYear == null || currentYear.isBlank()) {
            LocalDate currentDate = LocalDate.now();
            currentYear = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));
        }
        System.out.println("Current Year: " + currentYear);

        List<Session> sessionsByName = sessionRepository.findBySessionName(currentYear);
        if (!sessionsByName.isEmpty()) {
            for (Session session : sessionsByName) {
                System.out.println("Session Start Date: " + session.getSessionFromDt());
                System.out.println("Session End Date: " + session.getSessionToDt());
            }
        }

        try {
            int currentYearInt = Integer.parseInt(currentYear);
            Optional<Session> sessionById = sessionRepository.findBySessionId(currentYearInt);
            if (sessionById.isPresent()) {
                Session session = sessionById.get();
                System.out.println("Session Start Date: " + session.getSessionFromDt());
                System.out.println("Session End Date: " + session.getSessionToDt());
            } else {
                System.out.println("No session found for Year: " + currentYear + " by session ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Current year is not a valid session ID.");
        }
    }
}
