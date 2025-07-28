package com.example.demo.config;

import com.example.demo.jwt.JwtUtility;
import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.repository.RestaurantUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);
    private final JwtUtility jwtUtility;
    private final ClientUserRepository clientUserRepository;
    private final RestaurantUserRepository restaurantUserRepository;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String userType = (String) request.getSession().getAttribute("userType");
        if (userType == null) {
            logger.error("Error en el flujo de autenticación: userType no encontrado en la sesión.");
            getRedirectStrategy().sendRedirect(request, response, frontendUrl + "/signin?error=AuthenticationFlowError");
            return;
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Map<String, Object> extraClaims = new HashMap<>();

        // Rellenamos los claims según el tipo de usuario
        if ("restaurant".equals(userType)) {
            RestaurantUser user = restaurantUserRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalStateException("Usuario Restaurante no encontrado en la BD: " + email));
            extraClaims.put("role", "RESTAURANT");
        } else {
            ClientUser user = clientUserRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalStateException("Usuario Cliente no encontrado en la BD: " + email));
            extraClaims.put("role", "CLIENT");
        }

        String token = jwtUtility.generateToken(extraClaims, email);
        logger.info("Token JWT generado para {} con rol {}. Redirigiendo...", email, extraClaims.get("role"));

        // --- CAMBIO CLAVE AQUÍ ---
        // Ahora enviamos el 'userType' directamente al frontend.
        String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl + "/oauth2/redirect")
                .queryParam("token", token)
                .queryParam("userType", userType) // <-- Enviamos el tipo de usuario
                .build().toUriString();

        request.getSession().removeAttribute("userType");
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}