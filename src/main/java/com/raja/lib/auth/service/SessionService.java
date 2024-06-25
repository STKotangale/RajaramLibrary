package com.raja.lib.auth.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
                System.out.println("Today's Date: " + LocalDate.now());
            }
        } else {
            System.out.println("No sessions found for Year: " + currentYear + " by session name.");
        }
    }
}
