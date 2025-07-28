package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class OAuth2Controller {

    /**
     * Inicia el flujo de login con Google para un Cliente.
     * Guarda el tipo de usuario en la sesión y redirige al flujo estándar de Spring.
     */
    @GetMapping("/login/oauth2/client")
    public RedirectView loginClient(HttpServletRequest request) {
        // Guardamos en la sesión que el usuario quiere ser 'client'
        request.getSession().setAttribute("userType", "client");
        // Redirigimos al endpoint de autorización de Spring Security
        return new RedirectView("/oauth2/authorization/google");
    }

    /**
     * Inicia el flujo de login con Google para un Restaurante.
     */
    @GetMapping("/login/oauth2/restaurant")
    public RedirectView loginRestaurant(HttpServletRequest request) {
        request.getSession().setAttribute("userType", "restaurant");
        return new RedirectView("/oauth2/authorization/google");
    }
}