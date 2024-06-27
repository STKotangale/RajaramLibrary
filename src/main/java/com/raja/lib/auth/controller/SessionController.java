package com.raja.lib.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.auth.service.SessionService;
import com.raja.lib.invt.resposne.SessionInfoDTO;

@RestController
@RequestMapping("/api/session")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/current-year-info")
    public ResponseEntity<SessionInfoDTO> getCurrentYearSessionInfo() {
        try {
            SessionInfoDTO sessionInfo = sessionService.getSessionInfoForCurrentYear();
            return ResponseEntity.ok(sessionInfo);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}

