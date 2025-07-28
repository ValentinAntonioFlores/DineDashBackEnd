package com.example.demo.services;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.repository.RestaurantUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final ClientUserRepository clientUserRepository;
    private final RestaurantUserRepository restaurantUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Process the OAuth2User
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // Extract user details from OAuth2User
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String googleId = (String) attributes.get("sub"); // Google's user ID
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");

        String userType = determineUserTypeFromSession();

        if ("restaurant".equals(userType)) {
            // Busca o crea un usuario de tipo Restaurante
            restaurantUserRepository.findByEmail(email).orElseGet(() -> {
                // Si el email ya está usado por un cliente, no se puede crear.
                if (clientUserRepository.findByEmail(email).isPresent()) {
                    throw new OAuth2AuthenticationException("Un usuario cliente ya existe con este email.");
                }
                String restaurantName = firstName + "'s Place"; // Nombre por defecto
                RestaurantUser newUser = new RestaurantUser(restaurantName, email, googleId, true, null, null);
                return restaurantUserRepository.save(newUser);
            });
        } else { // Por defecto, o si es "client"
            // Busca o crea un usuario de tipo Cliente
            clientUserRepository.findByEmail(email).orElseGet(() -> {
                // Si el email ya está usado por un restaurante, no se puede crear.
                if (restaurantUserRepository.findByEmail(email).isPresent()) {
                    throw new OAuth2AuthenticationException("Un usuario restaurante ya existe con este email.");
                }
                ClientUser newUser = new ClientUser(firstName, lastName, email, googleId, true);
                return clientUserRepository.save(newUser);
            });
        }

        return oAuth2User;
    }

    /**
     * Obtiene el tipo de usuario ('client' o 'restaurant') desde la sesión HTTP.
     * Este método es mucho más fiable que intentar leerlo de los parámetros de la URL.
     */
    private String determineUserTypeFromSession() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attr.getRequest();
            String userType = (String) request.getSession().getAttribute("userType");
            return (userType != null) ? userType : "client"; // Default to client if not found
        } catch (IllegalStateException e) {
            // Esto puede pasar en contextos fuera de una petición web (como en tests)
            return "client"; // Default seguro
        }
    }
}
