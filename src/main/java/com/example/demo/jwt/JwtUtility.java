package com.example.demo.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtility {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtility.class);

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:36000000}") // 10 horas en milisegundos por defecto
    private long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // --- ESTE ES EL MÉTODO QUE FALTABA ---
    /**
     * Genera un token JWT con claims adicionales (como ID y rol).
     * @param extraClaims Un mapa con la información extra para incluir en el token.
     * @param email El email del usuario, que será el "subject" del token.
     * @return El token JWT como un String.
     */
    public String generateToken(Map<String, Object> extraClaims, String email) {
        return Jwts.builder()
                .setClaims(extraClaims) // Añade los claims extra
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.key)
                .compact();
    }

    /**
     * Genera un token JWT simple (sin claims adicionales).
     * Este método se mantiene por si lo usas en otras partes de tu código.
     */
    public String generateToken(String email) {
        // Llama al método más completo con un mapa de claims vacío.
        return generateToken(new HashMap<>(), email);
    }

    public String extractEmail(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(this.key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            logger.error("No se pudo extraer el email del token JWT: {}", e.getMessage());
            return null;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(this.key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
            return false;
        }
    }
}