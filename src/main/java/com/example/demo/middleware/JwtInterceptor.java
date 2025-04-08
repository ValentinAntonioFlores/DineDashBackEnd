package com.example.demo.middleware;

import com.example.demo.jwt.JwtUtility;
import com.example.demo.jwt.TokenBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private TokenBlackListService tokenBlackListService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String authHeader = request.getHeader("Authorization");

        // Validar existencia del header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.getWriter().write("Missing or invalid Authorization header");
            return false;
        }

        String token = authHeader.substring(7); // Quitar "Bearer "

        // Validar token
        if (!jwtUtility.isTokenValid(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid token");
            return false;
        }

        if (tokenBlackListService.isBlacklisted(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token is blacklisted");
            return false;
        }

        // Si todo está bien, continuar
        return true;
    }
}
