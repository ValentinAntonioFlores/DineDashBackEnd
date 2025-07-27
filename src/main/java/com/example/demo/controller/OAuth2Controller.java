package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth2")
@CrossOrigin(origins = "http://localhost:3000") // Permite conexiones desde React
public class OAuth2Controller {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @GetMapping("/google/url")
    public ResponseEntity<Map<String, String>> getGoogleAuthUrl() {
        // The frontend will use this information to construct the Google OAuth URL
        Map<String, String> response = new HashMap<>();
        response.put("clientId", googleClientId);
        response.put("redirectUri", "http://localhost:8000/login/oauth2/code/google"); // This must match the redirect URI registered with Google
        
        return ResponseEntity.ok(response);
    }
}