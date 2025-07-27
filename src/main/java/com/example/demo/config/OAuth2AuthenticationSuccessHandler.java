package com.example.demo.config;

import com.example.demo.jwt.JwtUtility;
import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.repository.RestaurantUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private ClientUserRepository clientUserRepository;

    @Autowired
    private RestaurantUserRepository restaurantUserRepository;

    private final String FRONTEND_URL = "http://localhost:3000";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // Check if the user is a client user
        Optional<ClientUser> clientUserOptional = clientUserRepository.findByEmail(email);

        // Check if the user is a restaurant user
        Optional<RestaurantUser> restaurantUserOptional = restaurantUserRepository.findByEmail(email);

        // Determine user type based on request parameters or other logic
        String userType = determineUserType(request);

        // Generate JWT token
        String token = jwtUtility.generateToken(email);

        if (userType.equals("restaurant") && restaurantUserOptional.isPresent()) {
            // Handle RestaurantUser authentication
            RestaurantUser restaurantUser = restaurantUserOptional.get();

            // Redirect to frontend with token
            String redirectUrl = UriComponentsBuilder.fromUriString(FRONTEND_URL + "/oauth2/redirect")
                    .queryParam("token", token)
                    .queryParam("email", email)
                    .queryParam("name", restaurantUser.getNombreRestaurante())
                    .queryParam("id", restaurantUser.getIdRestaurante().toString())
                    .queryParam("userType", "restaurant")
                    .build().toUriString();

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else if (clientUserOptional.isPresent()) {
            // Handle ClientUser authentication
            ClientUser clientUser = clientUserOptional.get();

            // Redirect to frontend with token
            String redirectUrl = UriComponentsBuilder.fromUriString(FRONTEND_URL + "/oauth2/redirect")
                    .queryParam("token", token)
                    .queryParam("email", email)
                    .queryParam("name", clientUser.getNombre())
                    .queryParam("lastName", clientUser.getApellido())
                    .queryParam("id", clientUser.getIdUsuario().toString())
                    .queryParam("userType", "client")
                    .build().toUriString();

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else {
            // This shouldn't happen as the user should have been created in OAuth2UserService
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User not found after OAuth2 authentication");
        }
    }

    // Helper method to determine user type from request
    private String determineUserType(HttpServletRequest request) {
        // Default to client user
        String userType = "client";

        // Try to determine user type from request parameters
        // This could be based on the redirect URI, additional parameters, or other logic
        String redirectUri = request.getParameter("redirect_uri");
        if (redirectUri != null && redirectUri.contains("restaurant")) {
            userType = "restaurant";
        }

        // Check for a specific parameter that indicates user type
        String userTypeParam = request.getParameter("user_type");
        if (userTypeParam != null) {
            if (userTypeParam.equals("restaurant")) {
                userType = "restaurant";
            } else if (userTypeParam.equals("client")) {
                userType = "client";
            }
        }

        return userType;
    }
}
