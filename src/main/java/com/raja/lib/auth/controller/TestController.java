package com.raja.lib.auth.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.auth.security.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/session")
    public ResponseEntity<?> getSessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false); 
        if (session != null) {
            String username = (String) session.getAttribute("username");
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) session.getAttribute("roles");
            String sessionId = session.getId();
            return ResponseEntity.ok("Session info - Username: " + username + ", Roles: " + roles + ", Session ID: " + sessionId);
        } else {
            return ResponseEntity.ok("No active session");
        }
    }

    @GetMapping("/login-time")
    public ResponseEntity<?> getLoginTime(@RequestParam String token, HttpServletRequest request) {
        Date issuedAt = jwtUtils.getIssuedAtFromJwtToken(token);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(issuedAt);

        HttpSession session = request.getSession(false); // false means do not create if not exists
        String sessionId = session != null ? session.getId() : "No session";

        return ResponseEntity.ok("Login time: " + formattedDate + ", Session ID: " + sessionId);
    }
}
